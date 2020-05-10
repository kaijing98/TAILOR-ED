/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.ProductCatalogueSessionBeanLocal;
import entity.Artwork;
import entity.ArtworkPrice;
import entity.Tag;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllArtworkRsq;
import ws.restful.model.RetrieveSingleArtworkReq;

/**
 * REST Web Service
 *
 * @author yiningxing
 */
@Path("Artwork")
public class ArtworkResource {

    private final SessionBeanLookup sessionBeanLookup;
    private final ProductCatalogueSessionBeanLocal productCatalogueSessionBeanLocal;

    @Context
    private UriInfo context;

    public ArtworkResource() {
        sessionBeanLookup = new SessionBeanLookup();
        productCatalogueSessionBeanLocal = sessionBeanLookup.lookupProductCatalogueSessionBeanLocal();
    }

    @Path("retrieveAllArtworks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //to test the method in postman http://localhost:8080/tailoredJsfRws/Resources/Artwork/retrieveAllArtworks
    //all artworks are retrieved with their prices as well, but i think it is going to be hard to diplay mutiple prices in the front end
    //another idea would be retrieve all artworks, display its name and picture only, then user can click the artwork and be redirected to another page to with desciption and prices & format
    public Response retrieveAllArtworks() {
        //artorder,seller, artworkprice and tag
        try {
            List<Artwork> artworks = productCatalogueSessionBeanLocal.retrieveAllArtwork();
            for (Artwork artwork : artworks) {
                artwork.getArtworkOrders().clear();

                //keep seller, solve marshalling problem through removing artwork in seller/ then need to remove associations with seller also
                artwork.getSeller().getArtworks().clear();
                artwork.getSeller().getCustomers().clear();
                artwork.getSeller().getSelfCareBoxes().clear();
                artwork.getSeller().getOffences().clear();
                artwork.getSeller().getComments().clear();
                artwork.getSeller().getPosts().clear();

                //here i feel it is not a good practice to send password and salt to the frontend so i will set to null before sending
                artwork.getSeller().setPassword(null);
                artwork.getSeller().setSalt(null);
                //keep artwork, solve marshalling problem through removing artworkprice in seller      
                for (ArtworkPrice artworkPrice : artwork.getArtworkPrices()) {
                    artworkPrice.setArtwork(null);
                }
                for (Tag tag : artwork.getTags()) {
                    tag.getArtworks().clear();
                    tag.getCustomers().clear();
                    tag.getPosts().clear();
                    tag.getSelfCareBoxes().clear();
                }
            }
            return Response.status(Status.OK).entity(new RetrieveAllArtworkRsq(artworks)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveArtworkById/{artworkId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    //to test the method in postman http://localhost:8080/tailoredJsfRws/Resources/Artwork/retrieveArtworkById/{ArtworkID}
    //all artworks are retrieved with their prices as well, but i think it is going to be hard to diplay mutiple prices in the front end
    //another idea would be retrieve all artworks, display its name and picture only, then user can click the artwork and be redirected to another page to with desciption and prices & format
    public Response retrieveArtworkById(@PathParam("artworkId") Long artworkId) {
        System.out.println("Artwork ID For restAPI is : " + artworkId);
        //artorder,seller, artworkprice and tag
        try {
            Artwork artwork = productCatalogueSessionBeanLocal.retrieveArtworkById(artworkId);
            artwork.getArtworkOrders().clear();

            //keep seller, solve marshalling problem through removing artwork in seller/ then need to remove associations with seller also
            artwork.getSeller().getArtworks().clear();
            artwork.getSeller().getCustomers().clear();
            artwork.getSeller().getSelfCareBoxes().clear();
            artwork.getSeller().getOffences().clear();
            artwork.getSeller().getComments().clear();
            artwork.getSeller().getPosts().clear();

            //here i feel it is not a good practice to send password and salt to the frontend so i will set to null before sending
            artwork.getSeller().setPassword(null);
            artwork.getSeller().setSalt(null);
            //keep artwork, solve marshalling problem through removing artworkprice in seller      
            for (ArtworkPrice artworkPrice : artwork.getArtworkPrices()) {
                artworkPrice.setArtwork(null);
            }
            for (Tag tag : artwork.getTags()) {
                tag.getArtworks().clear();
                tag.getCustomers().clear();
                tag.getPosts().clear();
                tag.getSelfCareBoxes().clear();
            }
         
            return Response.status(Status.OK).entity(new RetrieveSingleArtworkReq(artwork)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
}
