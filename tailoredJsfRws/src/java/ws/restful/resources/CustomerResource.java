/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.BackEndUserSessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.ProductCatalogueSessionBeanLocal;
import entity.Admin;
import entity.Artwork;
import entity.ArtworkPrice;
import entity.Comment;
import entity.Customer;
import entity.Offences;
import entity.Post;
import entity.Review;
import entity.SelfCareBox;
import entity.SelfCareSubscriptionDiscount;
import entity.Seller;
import entity.Tag;
import entity.User;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.InvalidLoginCredentialException;
import util.exception.TagNotFoundException;
import util.exception.UnableToReportUserException;
import util.exception.UserAlreadyBannedException;
import util.exception.UserNotFoundException;
import ws.restful.model.AdminLoginRsp;
import ws.restful.model.CreateCustomerReq;
import ws.restful.model.CreateCustomerRsp;
import ws.restful.model.CustomerLoginRsp;
import ws.restful.model.ErrorRsp;
import ws.restful.model.FilterArtworkByTagsReq;
import ws.restful.model.RetrieveAllArtworkRsq;
import ws.restful.model.RetrieveAllOffenceRsq;
import ws.restful.model.RetrieveAllSelfCareBoxesRsp;
import ws.restful.model.RetrieveSingleCustomerRsp;
import ws.restful.model.SellerLoginRsp;
import ws.restful.model.UpdateCustomerProfileReq;
import ws.restful.model.UpdateCustomerReq;
import ws.restful.model.ViewAllFollowedSellerRsp;

/**
 * REST Web Service
 *
 * @author yiningxing
 */
@Path("Customer")
public class CustomerResource {

    private final BackEndUserSessionBeanLocal backEndUserSessionBeanLocal;
    private final ProductCatalogueSessionBeanLocal productCatalogueSessionBeanLocal;
    private final SessionBeanLookup sessionBeanLookup;
    private final CustomerSessionBeanLocal customerSessionBeanLocal;

    @Context
    private UriInfo context;

    public CustomerResource() {
        sessionBeanLookup = new SessionBeanLookup();
        customerSessionBeanLocal = sessionBeanLookup.lookupCustomerSessionBeanLocal();
        backEndUserSessionBeanLocal = sessionBeanLookup.lookupBackEndUserSessionBeanLocal();
        productCatalogueSessionBeanLocal = sessionBeanLookup.lookupProductCatalogueSessionBeanLocal();
    }

    @Path("customerLogin")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response customerLogin(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        try {
            User user = backEndUserSessionBeanLocal.login(username, password);
            if (user instanceof Customer) {
                Customer customer = customerSessionBeanLocal.customerLogin(username, password);
                System.out.println("********** Customer login: Customer " + customer.getUsername() + " successfully login");
                customer.setPassword(null);
                customer.setSalt(null);
                //customer login will not return any assoication, if customer choose to view profile, it needs to do it at another branch
//            for (Offences offence:customer.getOffences()){
//                offence.getUsers().clear();
//            }
//            
//            for (Post post:customer.getPosts()) {
//                post.getComments().clear();
//                post.getTags().clear();
//                post.setUser(null);
//            }
//            
//            customer.getComments().clear();
//            for (OrderHistory order:customer.getOrders())
                customer.getPosts().clear();
                customer.getComments().clear();
                customer.getOrders().clear();
                customer.getComments().clear();
                customer.getOffences().clear();
                customer.getReviews().clear();
                customer.getSellers().clear();
                customer.getTags().clear();
                customer.getTransactions().clear();

                return Response.status(Status.OK).entity(new CustomerLoginRsp(customer)).build();
            } else {
                if (user instanceof Admin) {
                    Admin admin = (Admin) user;
                    admin.setPassword(null);
                    admin.setSalt(null);
                    admin.getComments().clear();
                    admin.getOffences().clear();
                    admin.getPosts().clear();
                    return Response.status(Status.OK).entity(new AdminLoginRsp(admin)).build();
                } else {
                    Seller seller = (Seller) user;
                    seller.setPassword(null);
                    seller.setSalt(null);
                    seller.getComments().clear();
                    seller.getOffences().clear();
                    seller.getPosts().clear();
                    seller.getArtworks().clear();
                    seller.getCustomers().clear();
                    seller.getComments().clear();
                    seller.getSelfCareBoxes().clear();
                    return Response.status(Status.OK).entity(new SellerLoginRsp(seller)).build();
                }

            }
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(CreateCustomerReq createCustomerReq) {
        if (createCustomerReq != null) {
            try {
                Long customerId = customerSessionBeanLocal.createNewCustomer(createCustomerReq.getNewCustomer(), createCustomerReq.getTags());
                System.out.println("Customer tag number: " + customerSessionBeanLocal.retrieveCustomerById(customerId).getTags().size());
                System.out.println("Customer tag name: " + customerSessionBeanLocal.retrieveCustomerById(customerId).getTags().get(0).getTagName());
                for (Tag tag : customerSessionBeanLocal.retrieveCustomerById(customerId).getTags()) {
                    System.out.println("Customer tag" + tag.getTagName());
                    for (Customer customer : tag.getCustomers()) {
                        System.out.println(customer.getUsername());
                    }
                }
                CreateCustomerRsp createCustomerRsp = new CreateCustomerRsp(customerId);
                return Response.status(Response.Status.OK).entity(createCustomerRsp).build();
            } catch (TagNotFoundException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create new customer request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    @Path("updatePreference")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerPreferences(UpdateCustomerReq updateCustomerReq) {
        if (updateCustomerReq != null) {
            try {
                customerSessionBeanLocal.updateMyCustomerPreferences(updateCustomerReq.getCustomer(), updateCustomerReq.getTags());

                return Response.status(Response.Status.OK).build();
            } catch (UserNotFoundException | TagNotFoundException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update customer request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    @Path("updateProfile")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerProfile(UpdateCustomerProfileReq updateCustomerProfileReq) {
        if (updateCustomerProfileReq != null) {
            try {
                Customer currCustomer = customerSessionBeanLocal.retrieveCustomerById(updateCustomerProfileReq.getCustomer().getUserId());

                updateCustomerProfileReq.getCustomer().setSalt(currCustomer.getSalt());
                updateCustomerProfileReq.getCustomer().setPassword(currCustomer.getPassword());
                customerSessionBeanLocal.updateMyCustomerProfile(updateCustomerProfileReq.getCustomer());

                return Response.status(Response.Status.OK).build();
            } catch (UserNotFoundException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update customer request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    @Path("retrieveSingleCustomerById/{customerId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSingleCustomerById(@PathParam("customerId") Long customerId) {
        try {
            Customer customer = customerSessionBeanLocal.retrieveCustomerById(customerId);
            for (Comment comment : customer.getComments()) {
                comment.setUser(null);
                comment.setPost(null);
            }
            for (Post post : customer.getPosts()) {
                post.getComments().clear();
                post.getTags().clear();
            }

            //need to edit this part to return orders once the orderHistory and Transaction CRUD is done
            customer.getOrders().clear();
            for (Review review : customer.getReviews()) {
                review.setCustomer(null);
                review.getSelfCareBox().getRatings().clear();
                review.getSelfCareBox().getReviews().clear();
                review.getSelfCareBox().getSelfCareBoxOrders().clear();
                review.getSelfCareBox().getSelfCareSubscriptionDiscounts().clear();
                review.getSelfCareBox().setSeller(null);
                review.getSelfCareBox().getTags().clear();

            }
            for (Seller seller : customer.getSellers()) {
                seller.getArtworks().clear();
                seller.getComments().clear();
                seller.getCustomers().clear();
                seller.getOffences().clear();
                seller.getPosts().clear();
                seller.getSelfCareBoxes().clear();
                seller.setPassword(null);
                seller.setSalt(null);
            }

            customer.getTags().clear();
            //need to edit this part to return transaction once the orderHistory and Transaction CRUD is done
            customer.getTransactions().clear();

            return Response.status(Status.OK).entity(new RetrieveSingleCustomerRsp(customer)).build();
        } catch (UserNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("{customerId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("customerId") Long customerId) {
        try {
            customerSessionBeanLocal.deleteCustomer(customerId);

            return Response.status(Status.OK).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveAllOffences")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllOffence() {
        try {
            List<Offences> offences = customerSessionBeanLocal.retrieveAllOffences();
            for (Offences offence : offences) {
                offence.getUsers().clear();
            }

            return Response.status(Status.OK).entity(new RetrieveAllOffenceRsq(offences)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("reportUser")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response reportUser(@QueryParam("userId") Long userToReportId, @QueryParam("offenceId") Long offenceId) {
        try {
            customerSessionBeanLocal.reportAUser(userToReportId, offenceId);

            return Response.status(Response.Status.OK).build();
        } catch (UnableToReportUserException | UserAlreadyBannedException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("followSeller")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response followSeller(@QueryParam("customerId") Long customerId, @QueryParam("sellerId") Long sellerId) {
        try {
            customerSessionBeanLocal.followASeller(customerId, sellerId);

            return Response.status(Response.Status.OK).build();
        } catch (UserNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("viewAllFollowedSellers")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewAllFollowedSellers(@QueryParam("customerId") Long customerId) {
        try {
            List<Seller> followedSellers = customerSessionBeanLocal.viewAllFollowedSellers(customerId);

            for (Seller seller : followedSellers) {
                seller.getArtworks().clear();
                seller.getComments().clear();
                seller.getCustomers().clear();
                seller.getOffences().clear();
                seller.getPosts().clear();
                seller.getSelfCareBoxes().clear();
                seller.setPassword(null);
                seller.setSalt(null);
            }
            return Response.status(Status.OK).entity(new ViewAllFollowedSellerRsp(followedSellers)).build();
        } catch (UserNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("unfollowSeller")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response unfollowSeller(@QueryParam("customerId") Long customerId, @QueryParam("sellerId") Long sellerId) {
        try {
            customerSessionBeanLocal.unfollowASeller(customerId, sellerId);;

            return Response.status(Response.Status.OK).build();
        } catch (UserNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("viewAllArtworksByFollowedSellers")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewAllArtworksByFollowedSellers(@QueryParam("customerId") Long customerId) {
        try {
            List<Artwork> artworks = customerSessionBeanLocal.viewAllArtworksByFollowedSellers(customerId);
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
                artwork.getTags().clear();

            }
            return Response.status(Status.OK).entity(new RetrieveAllArtworkRsq(artworks)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("viewAllSelfCareBoxByFollowedSellers")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewAllSelfCareBoxByFollowedSellers(@QueryParam("customerId") Long customerId) {
        try {
            List<SelfCareBox> selfCareBoxes = customerSessionBeanLocal.viewAllSelfCareBoxByFollowedSellers(customerId);
            for (SelfCareBox selfCareBox : selfCareBoxes) {
                selfCareBox.getSelfCareBoxOrders().clear();
                selfCareBox.getRatings().clear();
                selfCareBox.getReviews().clear();
                selfCareBox.getRatings().clear();
                //keep seller, solve marshalling problem through removing selfcarebox in seller/ then need to remove associations with seller also
                selfCareBox.getSeller().getArtworks().clear();
                selfCareBox.getSeller().getCustomers().clear();
                selfCareBox.getSeller().getSelfCareBoxes().clear();
                selfCareBox.getSeller().getOffences().clear();
                selfCareBox.getSeller().getComments().clear();
                selfCareBox.getSeller().getPosts().clear();

                for (SelfCareSubscriptionDiscount discount : selfCareBox.getSelfCareSubscriptionDiscounts()) {
                    discount.setSelfCareBox(null);
                }
                //here i feel it is not a good practice to send password and salt to the frontend so i will set to null before sending,.kl,,l/
                selfCareBox.getSeller().setPassword(null);
                selfCareBox.getSeller().setSalt(null);

                selfCareBox.getTags().clear();
            }
            return Response.status(Status.OK).entity(new RetrieveAllSelfCareBoxesRsp(selfCareBoxes)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("filterArtworkByTagId/{tagId}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //lazy to create another wrappe class since filterArtworkByTagsReq class has a common attribute List<Long> tagIds
    public Response filterArtworkByTags(@PathParam("tagId") Long tagId) {
        try {
            List<Artwork> artworks = productCatalogueSessionBeanLocal.filterArtworksByTags(tagId);
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
                artwork.getTags().clear();

            }
            return Response.status(Status.OK).entity(new RetrieveAllArtworkRsq(artworks)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("filterSelfCareBoxByTagId/{tagId}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterSelfCareBoxByTags(@PathParam("tagId") Long tagId) {
        try {
            List<SelfCareBox> selfCareBoxes = productCatalogueSessionBeanLocal.filterSelfCareBoxesByTags(tagId);
            for (SelfCareBox selfCareBox : selfCareBoxes) {
                selfCareBox.getSelfCareBoxOrders().clear();
                selfCareBox.getRatings().clear();
                selfCareBox.getReviews().clear();
                selfCareBox.getRatings().clear();
                //keep seller, solve marshalling problem through removing selfcarebox in seller/ then need to remove associations with seller also
                selfCareBox.getSeller().getArtworks().clear();
                selfCareBox.getSeller().getCustomers().clear();
                selfCareBox.getSeller().getSelfCareBoxes().clear();
                selfCareBox.getSeller().getOffences().clear();
                selfCareBox.getSeller().getComments().clear();
                selfCareBox.getSeller().getPosts().clear();

                for (SelfCareSubscriptionDiscount discount : selfCareBox.getSelfCareSubscriptionDiscounts()) {
                    discount.setSelfCareBox(null);
                }
                //here i feel it is not a good practice to send password and salt to the frontend so i will set to null before sending,.kl,,l/
                selfCareBox.getSeller().setPassword(null);
                selfCareBox.getSeller().setSalt(null);

                selfCareBox.getTags().clear();
            }
            return Response.status(Status.OK).entity(new RetrieveAllSelfCareBoxesRsp(selfCareBoxes)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
}
