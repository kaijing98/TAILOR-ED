/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.AdminSessionBeanLocal;
import entity.Admin;
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
import util.exception.UnknownPersistenceException;
import util.exception.UserNotFoundException;
import util.exception.UserUsernameExistException;

/**
 *
 * @author Kaijing
 */
@Named(value = "adminJSFManagedBean")
@ViewScoped
public class adminJSFManagedBean implements Serializable {

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;
    
    private List<Admin> admins;
    private Admin newAdmin;
    private Admin currentAdmin;
    
    public adminJSFManagedBean() {
        newAdmin = new Admin();
        currentAdmin = new Admin();
        admins = new ArrayList<>();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        Admin admin = (Admin)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser");
        currentAdmin = (Admin)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser");
        if(admin != null){
            admins = adminSessionBeanLocal.retrieveAllAdmins();
        }
    }
    
    public void createNewAdmin(ActionEvent event)  {
        Admin admin = (Admin)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser");
        if(admin!=null){    
            try
            {
                //default new admin password is always password
                newAdmin.setPassword("password");
                Long adminId = adminSessionBeanLocal.createNewAdmin(newAdmin);
                Admin adminNew = adminSessionBeanLocal.retrieveAdminByUserId(adminId);
                admins.add(adminNew);

                newAdmin = new Admin();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New admin created successfully (Admin ID: " + adminId + ")", null));
            }
            catch(InputDataValidationException | UserUsernameExistException | UserNotFoundException | UnknownPersistenceException ex)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new admin: " + ex.getMessage(), null));
            }
        }
    }
    
    public void updateAdmin(ActionEvent event)
    {        
        try {
            currentAdmin = (Admin)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser");
            adminSessionBeanLocal.updateAdmin(currentAdmin);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin updated successfully", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect("/tailoredJsf-war/userManagement/adminManagement.xhtml");
        }
        catch(InputDataValidationException | UserNotFoundException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating the admin: " + ex.getMessage(), null));
              
        }catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
        
    }
    
    public void deleteAdmin(ActionEvent event)
    {
        Admin adminToDelete = (Admin)event.getComponent().getAttributes().get("adminToDelete");

        try
        {
            adminSessionBeanLocal.deleteAdmin(adminToDelete.getUserId());            
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin deleted successfully", null));
        }
        catch(UserNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting admin: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }    
        admins.remove(adminToDelete);
    }

    /**
     * @return the admins
     */
    public List<Admin> getAdmins() {
        return admins;
    }

    /**
     * @param admins the admins to set
     */
    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }

    /**
     * @return the newAdmin
     */
    public Admin getNewAdmin() {
        return newAdmin;
    }

    /**
     * @param newAdmin the newAdmin to set
     */
    public void setNewAdmin(Admin newAdmin) {
        this.newAdmin = newAdmin;
    }

    /**
     * @return the currentAdmin
     */
    public Admin getCurrentAdmin() {
        return currentAdmin;
    }

    /**
     * @param currentAdmin the currentAdmin to set
     */
    public void setCurrentAdmin(Admin currentAdmin) {
        this.currentAdmin = currentAdmin;
    }
    
}
