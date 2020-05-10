/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.ForumSessionBeanLocal;
import entity.Admin;
import entity.Artwork;
import entity.ArtworkPrice;
import entity.Customer;
import entity.Post;
import entity.Seller;
import entity.Tag;
import entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PostExistException;
import util.exception.PostNotFoundException;
import util.exception.TagNotFoundException;
import util.exception.UserNotFoundException;
import ws.restful.model.CreatePostReq;
import ws.restful.model.CreatePostRsp;
import ws.restful.model.ErrorRsp;
import ws.restful.model.FilterArtworkByTagsReq;
import ws.restful.model.RetrieveAllArtworkRsq;
import ws.restful.model.RetrieveAllPostRsp;
import ws.restful.model.RetrievePostByIdRsp;
import ws.restful.model.UpdatePostReq;

/**
 * REST Web Service
 *
 * @author yiningxing
 */
@Path("Post")
public class PostResource {

    private final CustomerSessionBeanLocal customerSessioonBeanLocal;
    private final SessionBeanLookup sessionBeanLookup;
    private final ForumSessionBeanLocal forumSessionBeanLocal;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PostResource
     */
    public PostResource() {
        sessionBeanLookup = new SessionBeanLookup();
        forumSessionBeanLocal = sessionBeanLookup.lookupForumSessionBeanLocal();
        customerSessioonBeanLocal = sessionBeanLookup.lookupCustomerSessionBeanLocal();
    }

    @Path("retrieveAllPosts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllPosts() {
        //artorder,seller, artworkprice and tag
        try {
            List<Post> posts = forumSessionBeanLocal.retrieveAllPosts();
            for (Post post : posts) {
                post.getComments().clear();
                post.getTags().clear();

                User user = post.getUser();
                if (user instanceof Customer) {
                    Customer customer = (Customer) user;
                    customer.setPassword(null);
                    customer.setSalt(null);
                    customer.getComments().clear();
                    customer.getOrders().clear();
                    customer.getComments().clear();
                    customer.getOffences().clear();
                    customer.getReviews().clear();
                    customer.getSellers().clear();
                    customer.getTags().clear();
                    customer.getTransactions().clear();
                    customer.getPosts().clear();
                } else {
                    if (user instanceof Admin) {
                        Admin admin = (Admin) user;
                        admin.setPassword(null);
                        admin.setSalt(null);
                        admin.getComments().clear();
                        admin.getOffences().clear();
                        admin.getPosts().clear();
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
                    }

                }
                post.getUser().getComments().clear();
                post.getUser().getOffences().clear();
                post.getUser().getPosts().clear();
                post.getUser().setSalt(null);
                post.getUser().setPassword(null);
            }
            return Response.status(Status.OK).entity(new RetrieveAllPostRsp(posts)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("filterPostByTagId/{tagId}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterPostByTagId(@PathParam("tagId") Long tagId) {
        try {
            List<Post> posts = forumSessionBeanLocal.filterPostsByTags(tagId);
            for (Post post : posts) {
                post.getComments().clear();
                post.getTags().clear();

                User user = post.getUser();
                if (user instanceof Customer) {
                    Customer customer = (Customer) user;
                    customer.setPassword(null);
                    customer.setSalt(null);
                    customer.getComments().clear();
                    customer.getOrders().clear();
                    customer.getComments().clear();
                    customer.getOffences().clear();
                    customer.getReviews().clear();
                    customer.getSellers().clear();
                    customer.getTags().clear();
                    customer.getTransactions().clear();
                    customer.getPosts().clear();
                } else {
                    if (user instanceof Admin) {
                        Admin admin = (Admin) user;
                        admin.setPassword(null);
                        admin.setSalt(null);
                        admin.getComments().clear();
                        admin.getOffences().clear();
                        admin.getPosts().clear();
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
                    }

                }
                post.getUser().getComments().clear();
                post.getUser().getOffences().clear();
                post.getUser().getPosts().clear();
                post.getUser().setSalt(null);
                post.getUser().setPassword(null);
            }
            return Response.status(Status.OK).entity(new RetrieveAllPostRsp(posts)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveAllMyPosts/{customerId}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllMyPosts(@PathParam("customerId") Long customerId) {
        try {
            //Customer customer = customerSessioonBeanLocal.retrieveCustomerById(customerId);
            //Customer customerLoggedin = customerSessioonBeanLocal.customerLogin(customer.getUsername(), customer.getPassword());
            //System.out.println("********** PostResource.retrieveMyPosts: Customer " + customerLoggedin.getUserId() + " login remotely via web service");

            List<Post> posts = forumSessionBeanLocal.retrieveAllMyPosts(customerId);

            for (Post post : posts) {
                post.getComments().clear();
                post.getTags().clear();
                post.setUser(null);
            }
            return Response.status(Status.OK).entity(new RetrieveAllPostRsp(posts)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("deletePostById/{postId}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePost(@QueryParam("username") String username, @QueryParam("password") String password, @PathParam("postId") Long postId) {
        try {
            forumSessionBeanLocal.deletePostById(postId);
            return Response.status(Status.OK).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("updatePost")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerProfile(UpdatePostReq updatePostReq) {
        if (updatePostReq != null) {
            try {
                forumSessionBeanLocal.updateMyPost(updatePostReq.getPost());
                return Response.status(Response.Status.OK).build();
            } catch (PostNotFoundException | InputDataValidationException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update post request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPost(CreatePostReq createPostReq) {
        if (createPostReq != null) {
            try {
                List<Long> tagIds = new ArrayList<>();
                Long postId = forumSessionBeanLocal.createNewPost(createPostReq.getPost(), createPostReq.getCustomerId(), createPostReq.getTags());
                CreatePostRsp createPostRsp = new CreatePostRsp(postId);
                return Response.status(Response.Status.OK).entity(createPostRsp).build();
            } catch (PostExistException | UserNotFoundException | TagNotFoundException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create new post request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @Path("getPostById/{postId}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrievePostById(@PathParam("postId") Long postId) {
        try {
            //Customer customer = customerSessioonBeanLocal.retrieveCustomerById(customerId);
            //Customer customerLoggedin = customerSessioonBeanLocal.customerLogin(customer.getUsername(), customer.getPassword());
            //System.out.println("********** PostResource.retrieveMyPosts: Customer " + customerLoggedin.getUserId() + " login remotely via web service");

            Post post = forumSessionBeanLocal.retrieveSinglePost(postId);
                post.getComments().clear();
                post.getTags().clear();
                post.setUser(null);
            return Response.status(Status.OK).entity(new RetrievePostByIdRsp(post)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
}
