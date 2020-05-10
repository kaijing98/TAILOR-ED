/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.TagSessionBeanLocal;
import entity.Admin;
import entity.Tag;
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
import util.exception.InputDataValidationException;
import util.exception.TagExistException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mac
 */
@Named(value = "tagJSFManagedBean")
@ViewScoped
public class tagJSFManagedBean implements Serializable  {

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;

    private List<Tag> tags;
    private Tag newTag;
    private Tag selectedTagToUpdate;

    
    public tagJSFManagedBean() {
        newTag = new Tag();
        tags = new ArrayList<>();
        selectedTagToUpdate = new Tag();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        Admin admin = (Admin)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser");
        if(admin != null){
            tags = tagSessionBeanLocal.retrieveAllTags();
        }
    }
    
    public void createNewTag(ActionEvent event) {
        Admin admin = (Admin)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser");
        if(admin!=null){    
            try
            {
                Long tId = tagSessionBeanLocal.createNewTag(newTag);
                Tag t = tagSessionBeanLocal.retrieveTagByTagId(tId);
                tags.add(t);

                newTag = new Tag();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New tag created successfully (Tag ID: " + tId + ")", null));
            }
            catch(InputDataValidationException | TagExistException | TagNotFoundException | UnknownPersistenceException ex)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new tag: " + ex.getMessage(), null));
            }
        }
    }
    
    public void doUpdateTag(ActionEvent event)
    {
        selectedTagToUpdate = (Tag)event.getComponent().getAttributes().get("tagToUpdate");
        
    }
    
    public void updateTag(ActionEvent event)
    {        
        try {
            tagSessionBeanLocal.updateTag(selectedTagToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tag updated successfully", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect("/tailoredJsf-war/productManagement/tagManagement.xhtml");
        }
        catch(InputDataValidationException | TagNotFoundException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating the tag: " + ex.getMessage(), null));
              
        }catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
        
    }
    
    public void deleteTag(ActionEvent event)
    {
        Tag tagToDelete = (Tag)event.getComponent().getAttributes().get("tagToDelete");
        System.out.println("deleting tag "+ tagToDelete.getTagId());
        try
        {
            tagSessionBeanLocal.deleteTag(tagToDelete.getTagId());            
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tag deleted successfully", null));
        }
        catch(TagNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting tag: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }    
        tags.remove(tagToDelete);
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Tag getNewTag() {
        return newTag;
    }

    public void setNewTag(Tag tag) {
        this.newTag = tag;
    }
    
    public Tag getSelectedTagToUpdate() {
        return selectedTagToUpdate;
    }

    public void setSelectedTagToUpdate(Tag selectedTagToUpdate) {
        this.selectedTagToUpdate = selectedTagToUpdate;
    }
}
