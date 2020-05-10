/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.AdminSessionBeanLocal;
import entity.Admin;
import entity.Offences;
import java.io.IOException;
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
import util.exception.OffenceExistException;
import util.exception.OffenceNotFoundException;
import util.exception.UnknownPersistenceException;
/**
 *
 * @author mac
 */
@Named(value = "offenceJSFManagedBean")
@ViewScoped
public class offenceJSFManagedBean implements Serializable {

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;

    private Offences newOffence;
    private List<Offences> offences;
    
    public offenceJSFManagedBean() {
        newOffence =  new Offences();
        offences = new ArrayList<>();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        Admin admin = (Admin)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser");
        if(admin != null){
            offences = adminSessionBeanLocal.retrieveAllOffences();
        }
    }
    
    public void createNewOffence(ActionEvent event) throws IOException  {
        try
            {
                Long offenceId = adminSessionBeanLocal.createNewOffence(newOffence);
                Offences createdOffence = adminSessionBeanLocal.retrieveOffenceById(offenceId);
                
                offences.add(createdOffence);
                newOffence = new Offences();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New offence created successfully (Offece Name: " + createdOffence.getName() + ")", null));
            }
            catch(UnknownPersistenceException | InputDataValidationException | OffenceExistException | OffenceNotFoundException ex)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new offence: " + ex.getMessage(), null));
            }
    }

    public Offences getNewOffence() {
        return newOffence;
    }

    public void setNewOffence(Offences newOffence) {
        this.newOffence = newOffence;
    }

    public List<Offences> getOffences() {
        return offences;
    }

    public void setOffences(List<Offences> offences) {
        this.offences = offences;
    }
    
}
