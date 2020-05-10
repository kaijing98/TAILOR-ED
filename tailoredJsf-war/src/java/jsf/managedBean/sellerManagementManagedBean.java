/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.AdminSessionBeanLocal;
import ejb.session.stateless.SellerSessionBeanLocal;
import entity.Admin;
import entity.Seller;
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
import util.exception.UserNotFoundException;

/**
 *
 * @author mac
 */
@Named(value = "sellerManagementManagedBean")
@ViewScoped
public class sellerManagementManagedBean implements Serializable {

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;

    @EJB(name = "SellerSessionBeanLocal")
    private SellerSessionBeanLocal sellerSessionBeanLocal;
    
    
    
    private List<Seller> sellers;
    
    public sellerManagementManagedBean() {
        sellers = new ArrayList<>();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        Admin admin = (Admin)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser");
        if(admin != null){
            sellers = sellerSessionBeanLocal.retrieveAllSellers();
        }
    }
    
    public void approveSeller(ActionEvent event)
    {   
        Seller sellerToApprove = (Seller)event.getComponent().getAttributes().get("sellerToApprove");
        try {
            adminSessionBeanLocal.approveSeller(sellerToApprove.getUserId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Seller approved successfully", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect("/tailoredJsf-war/userManagement/sellerManagement.xhtml");
        }
        catch(UserNotFoundException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while approving the seller: " + ex.getMessage(), null));
              
        }catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
        
    }

    public List<Seller> getSellers() {
        return sellers;
    }

    public void setSellers(List<Seller> sellers) {
        this.sellers = sellers;
    }
}
