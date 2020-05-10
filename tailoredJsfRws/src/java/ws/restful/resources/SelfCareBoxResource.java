/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.ProductCatalogueSessionBeanLocal;
import entity.Review;
import entity.SelfCareBox;
import entity.SelfCareSubscriptionDiscount;
import entity.Tag;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllSelfCareBoxesRsp;
import ws.restful.model.RetrieveSingleSelfCareBoxReq;

/**
 * REST Web Service
 *
 * @author yiningxing
 */
@Path("SelfCareBox")
public class SelfCareBoxResource {

    @Context
    private UriInfo context; 

    private final SessionBeanLookup sessionBeanLookup;
    private final ProductCatalogueSessionBeanLocal productCatalogueSessionBeanLocal;

    public SelfCareBoxResource() {
        sessionBeanLookup = new SessionBeanLookup();
        productCatalogueSessionBeanLocal = sessionBeanLookup.lookupProductCatalogueSessionBeanLocal();
    }
    
    

//    clear private Seller seller;
//    cleared private List<SelfCareSubscriptionDiscount> selfCareSubscriptionDiscounts;
//    cleared private List<SelfCareBoxOrder> selfCareBoxOrders; 
//    cleered private List<Review> reviews;
//    cleared private List<Rating> ratings;
//    cleared private List<Tag> tags;
    @Path("retrieveAllSelfCareBoxes")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //To test on postman: http://localhost:8080/tailoredJsfRws/Resources/SelfCareBox/retrieveAllSelfCareBoxes
    public Response retrieveAllSelfCareBoxes() {
        //artorder,seller, artworkprice and tag
        try {
            List<SelfCareBox> selfCareBoxes = productCatalogueSessionBeanLocal.retrieveAllSelfCareBox();
            for (SelfCareBox selfCareBox : selfCareBoxes) {
                selfCareBox.getSelfCareBoxOrders().clear();
                selfCareBox.getRatings().clear();
                selfCareBox.getReviews().clear();
                
                //keep seller, solve marshalling problem through removing selfcarebox in seller/ then need to remove associations with seller also
                selfCareBox.getSeller().getArtworks().clear();
                selfCareBox.getSeller().getCustomers().clear();
                selfCareBox.getSeller().getSelfCareBoxes().clear();
                selfCareBox.getSeller().getOffences().clear();
                selfCareBox.getSeller().getComments().clear();
                selfCareBox.getSeller().getPosts().clear();
                
                for (SelfCareSubscriptionDiscount discount:selfCareBox.getSelfCareSubscriptionDiscounts()) {
                    discount.setSelfCareBox(null);
                }
                //here i feel it is not a good practice to send password and salt to the frontend so i will set to null before sending,.kl,,l/
                selfCareBox.getSeller().setPassword(null);
                selfCareBox.getSeller().setSalt(null);
                
                for (Tag tag : selfCareBox.getTags()) {
                    tag.getArtworks().clear();
                    tag.getCustomers().clear();
                    tag.getPosts().clear();
                    tag.getSelfCareBoxes().clear();
                }
            }
            return Response.status(Status.OK).entity(new RetrieveAllSelfCareBoxesRsp(selfCareBoxes)).build();
        } catch (Exception ex) {                        
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build(); 
        }
    }
        
    @Path("retrieveSelfCareBoxById/{selfCareBoxId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSelfCareBoxById(@PathParam("selfCareBoxId") Long selfCareBoxId) {
        System.out.println("SelfCareBox ID For restAPI is : " + selfCareBoxId);
        //artorder,seller, artworkprice and tag
        try {
            SelfCareBox selfCareBox = productCatalogueSessionBeanLocal.retrieveSelfCareBoxById(selfCareBoxId);
            selfCareBox.getSelfCareBoxOrders().clear();
            selfCareBox.getSelfCareBoxOrders().clear();
            selfCareBox.getRatings().clear();
            selfCareBox.getReviews().clear();
            
            //keep seller, solve marshalling problem through removing selfCareBox in seller/ then need to remove associations with seller also
            selfCareBox.getSeller().getSelfCareBoxes().clear();
            selfCareBox.getSeller().getCustomers().clear();
            selfCareBox.getSeller().getArtworks().clear();
            selfCareBox.getSeller().getOffences().clear();
            selfCareBox.getSeller().getComments().clear();
            selfCareBox.getSeller().getPosts().clear();

            selfCareBox.getSeller().setPassword(null);
            selfCareBox.getSeller().setSalt(null);

            for (SelfCareSubscriptionDiscount boxDiscount : selfCareBox.getSelfCareSubscriptionDiscounts()) {
                boxDiscount.setSelfCareBox(null);
            }
            for (Tag tag : selfCareBox.getTags()) {
                tag.getSelfCareBoxes().clear();
                tag.getCustomers().clear();
                tag.getPosts().clear();
                tag.getArtworks().clear();
            }

            return Response.status(Status.OK).entity(new RetrieveSingleSelfCareBoxReq(selfCareBox)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

}
