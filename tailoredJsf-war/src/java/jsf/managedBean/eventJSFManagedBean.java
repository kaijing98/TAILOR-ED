/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.AdminSessionBeanLocal;
import entity.Admin;
import entity.Event;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.primefaces.model.UploadedFile;
import util.enumeration.EventTypeEnum;
import util.exception.EventNotFoundException;

/**
 *
 * @author Kaijing
 */
@Named(value = "eventJSFManagedBean")
@ViewScoped
public class eventJSFManagedBean implements Serializable {
    
    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;
    
    private List<Event> events;
    private Event newEvent;
    
    private UploadedFile file;
    private List<UploadedFile> files;
    
    @Enumerated(EnumType.STRING)
    private EventTypeEnum eventType;
    
    private List<String> selectedEventTypes;
    
    private Event selectedEventToUpdate;
    
    private String destination = System.getProperty("user.dir") + "/../docroot/";
    /**
     * Creates a new instance of eventJSFManagedBean
     */
    public eventJSFManagedBean() {
        newEvent = new Event();
        events = new ArrayList<Event>();
        selectedEventTypes = new ArrayList<>();
        for (EventTypeEnum type : EventTypeEnum.values()) {
            selectedEventTypes.add(type.toString());
        }
    }
    
    @PostConstruct
    public void postConstruct(){
        Admin admin = (Admin)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser");        
        if(admin != null){
            setEvents(adminSessionBeanLocal.retrieveAllEvents());
            System.out.println("events: " + events);
        }
    }
    
    public void deleteEvent(ActionEvent event) {
        Event eventToDelete = (Event)event.getComponent().getAttributes().get("eventToDelete");
        try {
            adminSessionBeanLocal.deleteEvent(eventToDelete.getEventId());
            events.remove(eventToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Event deleted successfully!", null));
        } catch (EventNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the event: " + ex.getMessage(), null));
        }
            
        
    }
    
    public void doUpdateEvent(ActionEvent event)
    {
        selectedEventToUpdate = (Event)event.getComponent().getAttributes().get("eventToUpdate");
    }
    
    public void updateEvent(ActionEvent event)
    {        
        try
        {
            //upload image if it exists
            //if it does not exist, we don't do anything aka current image stays 
            if (file != null) {
                upload();
            //note this is the image destination where it is saved
            System.out.println(destination + file.getFileName());
            selectedEventToUpdate.setImage(file.getFileName());
            } 

            adminSessionBeanLocal.updateEvent(selectedEventToUpdate);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Event updated successfully", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect("/tailoredJsf-war/eventManagement/eventManagement.xhtml");
        }
        catch(EventNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating event: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void createNewEvent(ActionEvent event) {
        Admin admin = (Admin)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser");        
        if(admin != null){
            try{
                if(getFile()!=null) {
                    upload();
                    newEvent.setImage(getFile().getFileName());
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Image not set when creating event", null));
                }
                  
                Long eventId = adminSessionBeanLocal.createNewEvent(newEvent, admin.getUserId());
                
                //retrieve newly created event
                Event createdEvent = adminSessionBeanLocal.retrieveEventById(eventId);
                events.add(createdEvent);
                
                newEvent= new Event();
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Event created successfully", null));
                FacesContext.getCurrentInstance().getExternalContext().redirect("/tailoredJsf-war/eventManagement/eventManagement.xhtml");
                
            }
            catch(Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error in creating event: " + ex.getMessage(), null));
            }

            
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error in creating artwork", null));
        }
    }
    
    // Upload method section
    public void upload() {
        if (getFile() != null) {
            System.out.println("Destination: " + destination + getFile().getFileName());
            try {
                copyFile(getFile().getFileName(), getFile().getInputstream());
                FacesMessage msg = new FacesMessage("Success! ", getFile().getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void copyFile(String fileName, InputStream in){
        try{
            OutputStream out = new FileOutputStream(new File(destination + fileName));
            
            int read = 0;
            byte[] bytes = new byte[1024];
            
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            
            in.close();
            out.flush();
            out.close();
            System.out.println("New file created!");
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }        
    }

    /**
     * @return the events
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * @return the newEvent
     */
    public Event getNewEvent() {
        return newEvent;
    }

    /**
     * @param newEvent the newEvent to set
     */
    public void setNewEvent(Event newEvent) {
        this.newEvent = newEvent;
    }

    /**
     * @return the file
     */
    public UploadedFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    /**
     * @return the files
     */
    public List<UploadedFile> getFiles() {
        return files;
    }

    /**
     * @param files the files to set
     */
    public void setFiles(List<UploadedFile> files) {
        this.files = files;
    }

    /**
     * @return the eventType
     */
    public EventTypeEnum getEventType() {
        return eventType;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(EventTypeEnum eventType) {
        this.eventType = eventType;
    }

    /**
     * @return the selectedEventTypes
     */
    public List<String> getSelectedEventTypes() {
        return selectedEventTypes;
    }

    /**
     * @param selectedEventTypes the selectedEventTypes to set
     */
    public void setSelectedEventTypes(List<String> selectedEventTypes) {
        this.selectedEventTypes = selectedEventTypes;
    }

    /**
     * @return the selectedEventToUpdate
     */
    public Event getSelectedEventToUpdate() {
        return selectedEventToUpdate;
    }

    /**
     * @param selectedEventToUpdate the selectedEventToUpdate to set
     */
    public void setSelectedEventToUpdate(Event selectedEventToUpdate) {
        this.selectedEventToUpdate = selectedEventToUpdate;
    }
    
}
