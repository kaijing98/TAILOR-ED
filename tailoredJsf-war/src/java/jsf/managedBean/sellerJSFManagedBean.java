/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.SellerSessionBeanLocal;
import entity.ArtworkOrder;
import entity.OrderHistory;
import entity.SelfCareBoxOrder;
import entity.Seller;
import entity.User;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.UserNotFoundException;
import util.exception.UserUsernameExistException;

/**
 *
 * @author Kaijing
 */
@Named(value = "sellerJSFManagedBean")
@ViewScoped
public class sellerJSFManagedBean implements Serializable {

    @EJB(name = "SellerSessionBeanLocal")
    private SellerSessionBeanLocal sellerSessionBeanLocal;
    
    @Inject
    private adminLoginJSFManagedBean adminLoginJSFManagedBean;
    
    private Seller newSeller;
    private Seller currentSeller;
    
    public sellerJSFManagedBean() {
        newSeller = new Seller();
        currentSeller = new Seller();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        User loginUser = (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser");
        if (loginUser instanceof Seller) {
            currentSeller = (Seller)loginUser;
        }
    }
    
    public void createNewSeller(ActionEvent event) throws IOException  {
        try
            {
                Long sellerId = sellerSessionBeanLocal.createNewSeller(getNewSeller());
                Seller createdSeller = sellerSessionBeanLocal.retrieveSellerById(sellerId);
                
                setNewSeller(new Seller());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New seller created successfully (Seller Name: " + createdSeller.getFirstName() + ")", null));
//                FacesContext.getCurrentInstance().getExternalContext().redirect("/tailoredJsf-war/index.xhtml");
            }
            catch(InputDataValidationException | UserUsernameExistException  | UserNotFoundException | UnknownPersistenceException ex)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new seller: " + ex.getMessage(), null));
            }
    }
    
    public void updateProfile(ActionEvent event) {
        try {
            currentSeller = (Seller)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser");
            sellerSessionBeanLocal.updateMySellerDetails(currentSeller);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Seller updated successfully", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect("/tailoredJsf-war/index.xhtml");
        }
        catch(UserNotFoundException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating the seller: " + ex.getMessage(), null));
              
        }catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void deleteSeller(ActionEvent event) {
        try {
            currentSeller = (Seller)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser");
            sellerSessionBeanLocal.deleteSeller(currentSeller.getUserId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Seller deleted successfully", null));
            adminLoginJSFManagedBean.logout(event);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/tailoredJsf-war/index.xhtml");
        }
        catch(UserNotFoundException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the seller: " + ex.getMessage(), null));
              
        }catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    /**
     * @return the newSeller
     */
    public Seller getNewSeller() {
        return newSeller;
    }

    /**
     * @param newSeller the newSeller to set
     */
    public void setNewSeller(Seller newSeller) {
        this.newSeller = newSeller;
    }

    /**
     * @return the currentSeller
     */
    public Seller getCurrentSeller() {
        return currentSeller;
    }

    /**
     * @param currentSeller the currentSeller to set
     */
    public void setCurrentSeller(Seller currentSeller) {
        this.currentSeller = currentSeller;
    }

    /**
     * @return the adminLoginJSFManagedBean
     */
    public adminLoginJSFManagedBean getAdminLoginJSFManagedBean() {
        return adminLoginJSFManagedBean;
    }

    /**
     * @param adminLoginJSFManagedBean the adminLoginJSFManagedBean to set
     */
    public void setAdminLoginJSFManagedBean(adminLoginJSFManagedBean adminLoginJSFManagedBean) {
        this.adminLoginJSFManagedBean = adminLoginJSFManagedBean;
    }
    
}
