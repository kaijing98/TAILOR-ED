/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.BackEndUserSessionBeanLocal;
import entity.Seller;
import entity.User;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author decimatum
 */
@Named(value = "adminLoginJSFManagedBean")
@RequestScoped
public class adminLoginJSFManagedBean {

    @EJB
    private BackEndUserSessionBeanLocal backEndUserSessionBean;
    
    private String username;
    private String password;
    public adminLoginJSFManagedBean() {
        
    }

    public void login(ActionEvent event) throws IOException{
        try{
            User loginUser = backEndUserSessionBean.login(username, password);
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loginUser", loginUser);
            System.out.println("Path: " + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
        }catch(InvalidLoginCredentialException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
    }
    
    public void logout(ActionEvent event) throws IOException
    {
        ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", false);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loginUser", null);
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String checkClassType(Object object){
        return object.getClass().getSimpleName();
    }

    public String getSessionClass(){
        
        Object user = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser"); 
        if(user != null){
            return checkClassType(user);
        }
        else {
            return null;
        }
    }

}
