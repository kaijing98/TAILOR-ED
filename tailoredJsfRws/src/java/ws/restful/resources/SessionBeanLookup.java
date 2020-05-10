/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.BackEndUserSessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.ForumSessionBeanLocal;
import ejb.session.stateless.OrderSessionBeanLocalLocal;
import ejb.session.stateless.ProductCatalogueSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author yiningxing
 */
public class SessionBeanLookup {

    public ProductCatalogueSessionBeanLocal lookupProductCatalogueSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ProductCatalogueSessionBeanLocal) c.lookup("java:global/tailoredJsf/tailoredJsf-ejb/ProductCatalogueSessionBean!ejb.session.stateless.ProductCatalogueSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public CustomerSessionBeanLocal lookupCustomerSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CustomerSessionBeanLocal) c.lookup("java:global/tailoredJsf/tailoredJsf-ejb/CustomerSessionBean!ejb.session.stateless.CustomerSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public BackEndUserSessionBeanLocal lookupBackEndUserSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (BackEndUserSessionBeanLocal) c.lookup("java:global/tailoredJsf/tailoredJsf-ejb/BackEndUserSessionBean!ejb.session.stateless.BackEndUserSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public TagSessionBeanLocal lookupTagSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TagSessionBeanLocal) c.lookup("java:global/tailoredJsf/tailoredJsf-ejb/TagSessionBean!ejb.session.stateless.TagSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public ForumSessionBeanLocal lookupForumSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ForumSessionBeanLocal) c.lookup("java:global/tailoredJsf/tailoredJsf-ejb/ForumSessionBean!ejb.session.stateless.ForumSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public OrderSessionBeanLocalLocal lookupOrderSessionBeanLocalLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (OrderSessionBeanLocalLocal) c.lookup("java:global/tailoredJsf/tailoredJsf-ejb/OrderSessionBeanLocal!ejb.session.stateless.OrderSessionBeanLocalLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
