/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.TagSessionBeanLocal;
import entity.Tag;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.TagNotFoundException;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllTagRsq;
import ws.restful.model.RetrieveSingleTagRsp;

/**
 * REST Web Service
 *
 * @author yiningxing
 */
@Path("Browsering")
public class BrowseringResource {

    private final TagSessionBeanLocal tagSessionBeanLocal;
    private final SessionBeanLookup sessionBeanLookup;
    
    @Context
    private UriInfo context;
    

    public BrowseringResource() {
        sessionBeanLookup = new SessionBeanLookup();
        tagSessionBeanLocal = sessionBeanLookup.lookupTagSessionBeanLocal();
    }
    
    
    @Path("retrieveAllTags")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTags() {
//    private List<Customer> customers; 
//    private List<SelfCareBox> selfCareBoxes;
//    private List<Post> posts;
//    private List<Artwork> artworks;
        try {
            //false beacause deleted ones are not retrieved
            List<Tag> tags = tagSessionBeanLocal.retrieveAllTags();
            for (Tag tag:tags) {
                tag.getArtworks().clear();
                tag.getCustomers().clear();
                tag.getPosts().clear();
                tag.getSelfCareBoxes().clear();
            }
            
            return Response.status(Status.OK).entity(new RetrieveAllTagRsq (tags)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build(); 
        }
    }
    
    @Path("retrieveSingleTagById/{tagId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSingleTagById(@PathParam("tagId") Long tagId) {
//    private List<Customer> customers; 
//    private List<SelfCareBox> selfCareBoxes;
//    private List<Post> posts;
//    private List<Artwork> artworks;
        try {
            Tag tag = tagSessionBeanLocal.retrieveTagByTagId(tagId);
                tag.getArtworks().clear();
                tag.getCustomers().clear();
                tag.getPosts().clear();
                tag.getSelfCareBoxes().clear();
                                                         
            return Response.status(Status.OK).entity(new RetrieveSingleTagRsp (tag)).build();
        } catch (TagNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build(); 
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build(); 
        }
    }
}
