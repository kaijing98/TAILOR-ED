/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.OrderSessionBeanLocalLocal;
import entity.ArtworkOrder;
import entity.OrderHistory;
import entity.SelfCareBoxOrder;
import entity.Transaction;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.enumeration.PaymentTypeEnum;
import util.exception.OrderNotFoundException;
import util.exception.UserNotFoundException;
import ws.restful.model.CreateArtworkOrderReq;
import ws.restful.model.CreateArtworkOrderRsp;
import ws.restful.model.CreateSelfCareBoxOrderReq;
import ws.restful.model.CreateSelfCareBoxOrderRsp;
import ws.restful.model.CreateTransactionReq;
import ws.restful.model.CreateTransactionRsp;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveArtworkOrderRsp;
import ws.restful.model.RetrieveSelfCareBoxOrderRsp;
import ws.restful.model.RetrieveTransactionByCustIdRsp;

/**
 * REST Web Service
 *
 * @author yiningxing
 */
@Path("Checkout")
public class CheckoutResource {

    OrderSessionBeanLocalLocal orderSessionBeanLocal;

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;

    /**
     * Creates a new instance of CheckoutResource
     */
    public CheckoutResource() {
        sessionBeanLookup = new SessionBeanLookup();
        orderSessionBeanLocal = sessionBeanLookup.lookupOrderSessionBeanLocalLocal();
    }

    @Path("createArtworkOrder")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewArtworkOrder(CreateArtworkOrderReq createArtworkReq) {
        if (createArtworkReq != null) {
            try {
                ArtworkOrder artworkOrder = orderSessionBeanLocal.createArtworkOrder(createArtworkReq.getArtworkID(), createArtworkReq.getArtworkPriceID(), createArtworkReq.getCustomerID(), createArtworkReq.getQuantity());
                CreateArtworkOrderRsp createArtworkOrderRsp = new CreateArtworkOrderRsp(artworkOrder.getOrderId());
                System.out.println("Artwork Order ID :" + createArtworkOrderRsp.getArtworkOrderID() + ", Artwork Order really success");
                return Response.status(Response.Status.OK).entity(createArtworkOrderRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create artwork order request");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @Path("createSelfCareBoxOrder")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewSelfCareBoxOrder(CreateSelfCareBoxOrderReq createSelfCareBoxReq) {
        if (createSelfCareBoxReq != null) {
            try {
                SelfCareBoxOrder selfCareBoxOrder = orderSessionBeanLocal.createSelfCareBoxOrder(createSelfCareBoxReq.getSelfCareBoxID(), createSelfCareBoxReq.getBoxDiscountID(), createSelfCareBoxReq.getCustomerID(), createSelfCareBoxReq.getQuantity());
                CreateSelfCareBoxOrderRsp createSelfCareBoxOrderRsp = new CreateSelfCareBoxOrderRsp(selfCareBoxOrder.getOrderId());
                System.out.println("SelfCareBox Order ID :" + createSelfCareBoxOrderRsp.getSelfCareBoxOrderID() + ", SelfCareBox Order really success");
                return Response.status(Response.Status.OK).entity(createSelfCareBoxOrderRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create SelfCareBox order request");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

//    @Path("retrieveOrderById/{orderId}")
//    @GET
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response retrieveOrderById(@PathParam("orderId") Long orderId) {
//        try {
//            OrderHistory orderHistory = orderSessionBeanLocal.retrieveOrderById(orderId);
//
//            if (orderHistory instanceof ArtworkOrder) {
//                ArtworkOrder artworkOrder = (ArtworkOrder) orderHistory;
//                artworkOrder.getArtwork().getArtworkOrders().clear();
//                artworkOrder.getArtwork().getTags().clear();
//                artworkOrder.getArtwork().getSeller().getArtworks().clear();
//                artworkOrder.getArtwork().getSeller().getComments().clear();
//                artworkOrder.getArtwork().getSeller().getCustomers().clear();
//                artworkOrder.getArtwork().getSeller().getOffences().clear();
//                artworkOrder.getArtwork().getSeller().getPosts().clear();
//                artworkOrder.getArtwork().getSeller().getSelfCareBoxes().clear();
//                artworkOrder.getArtwork().getSeller().setPassword(null);
//                artworkOrder.getArtwork().getSeller().setSalt(null);
//                artworkOrder.getArtwork().getArtworkPrices().clear();
//                artworkOrder.setCustomer(null);
//                artworkOrder.setTransaction(null);
//                return Response.status(Status.OK).entity(new RetrieveArtworkOrderRsp(artworkOrder)).build();
//            } else {
//                SelfCareBoxOrder selfCareBoxOrder = (SelfCareBoxOrder) orderHistory;
//                selfCareBoxOrder.getSelfCareBox().getRatings().clear();
//                selfCareBoxOrder.getSelfCareBox().getReviews().clear();
//                selfCareBoxOrder.getSelfCareBox().getSelfCareBoxOrders().clear();
//                selfCareBoxOrder.getSelfCareBox().getSelfCareSubscriptionDiscounts().clear();
//                selfCareBoxOrder.getSelfCareBox().setSeller(null);
//                selfCareBoxOrder.getSelfCareBox().getTags().clear();
//                selfCareBoxOrder.setCustomer(null);
//                selfCareBoxOrder.setTransaction(null);
//                return Response.status(Status.OK).entity(new RetrieveSelfCareBoxOrderRsp(selfCareBoxOrder)).build();
//            }
//        } catch (OrderNotFoundException ex) {
//            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
//            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
//        } catch (Exception ex) {
//            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
//            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
//        }
//    }

    @Path("createTransaction")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTransaction(CreateTransactionReq createTransactionReq) {
        if (createTransactionReq != null) {
            try {
                System.out.println("****************************************");
                System.out.println("Artwork Order List: " + createTransactionReq.getArtworkOrderList().toString());
                System.out.println("SelfCare Order List: " + createTransactionReq.getSelfCareList().toString());
                System.out.println("Payment Type: " + createTransactionReq.getPaymentType());
                System.out.println("Cust ID: " + createTransactionReq.getCustID());

                Transaction transaction = orderSessionBeanLocal.createTransaction(createTransactionReq.getArtworkOrderList(), createTransactionReq.getSelfCareList(), PaymentTypeEnum.MASTERCARD, createTransactionReq.getCustID());
                CreateTransactionRsp createTransactionRsp = new CreateTransactionRsp(transaction.getTransactionId());
                return Response.status(Response.Status.OK).entity(createTransactionRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create transaction request");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

//    @Path("retrieveTransactionByCustId/{custId}")
//    @GET
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response retrieveTransactionByCustId(@PathParam("custId") Long custId) {
//        try {
//            List<Transaction> transactions = orderSessionBeanLocal.retrieveTransactionsByCustID(custId);
//            ArtworkOrder artworkOrder;
//            SelfCareBoxOrder selfCareBoxOrder;
//            for (Transaction transaction : transactions) {
//                transaction.setCustomer(null);
//                for (OrderHistory oh : transaction.getOrders()) {
//                    oh.setCustomer(null);
//                    oh.setTransaction(null);
//                    if (oh instanceof ArtworkOrder) {
//                        artworkOrder = (ArtworkOrder) oh;
//                        artworkOrder.getArtwork().getArtworkOrders().clear();
//                        artworkOrder.getArtwork().getTags().clear();
//                        artworkOrder.getArtwork().setSeller(null);
//                        artworkOrder.getArtwork().getArtworkPrices().clear();
//                        artworkOrder.setCustomer(null);
//                        artworkOrder.setTransaction(null);
//                    } else {
//                        selfCareBoxOrder = (SelfCareBoxOrder) oh;
//                        selfCareBoxOrder.getSelfCareBox().getRatings().clear();
//                        selfCareBoxOrder.getSelfCareBox().getReviews().clear();
//                        selfCareBoxOrder.getSelfCareBox().getSelfCareBoxOrders().clear();
//                        selfCareBoxOrder.getSelfCareBox().getSelfCareSubscriptionDiscounts().clear();
//                        selfCareBoxOrder.getSelfCareBox().setSeller(null);
//                        selfCareBoxOrder.getSelfCareBox().getTags().clear();
//                        selfCareBoxOrder.setCustomer(null);
//                        selfCareBoxOrder.setTransaction(null);
//                    }
//                }
//            }
//            RetrieveTransactionByCustIdRsp retrieveTransactionByCustIdRsp = new RetrieveTransactionByCustIdRsp(transactions);
//            return Response.status(Response.Status.OK).entity(retrieveTransactionByCustIdRsp).build();
//        } catch (UserNotFoundException ex) {
//            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
//            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
//        } catch (Exception ex) {
//            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
//            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
//        }
//    }

    @Path("checkProductName")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkProductName(@PathParam("custId") Long custId
    ) {
        try {
            List<Transaction> transactions = orderSessionBeanLocal.retrieveTransactionsByCustID(custId);
            for (Transaction transaction : transactions) {
                transaction.setCustomer(null);
                for (OrderHistory oh : transaction.getOrders()) {
                    oh.setCustomer(null);
                    oh.setTransaction(null);
                }
            }
            RetrieveTransactionByCustIdRsp retrieveTransactionByCustIdRsp = new RetrieveTransactionByCustIdRsp(transactions);
            return Response.status(Response.Status.OK).entity(retrieveTransactionByCustIdRsp).build();
        } catch (UserNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveArtworkOrderById/{customerId}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveArtworkOrderById(@PathParam("customerId") Long customerId) {
        try {
            List<ArtworkOrder> artworkOrders = orderSessionBeanLocal.retrieveAllArtworkOrderByCustomerId(customerId);

            for (ArtworkOrder artworkOrder : artworkOrders) {
                    artworkOrder.getArtwork().getArtworkOrders().clear();
                    artworkOrder.getArtwork().getTags().clear();
                    artworkOrder.getArtwork().getSeller().getArtworks().clear();
                    artworkOrder.getArtwork().getSeller().getComments().clear();
                    artworkOrder.getArtwork().getSeller().getCustomers().clear();
                    artworkOrder.getArtwork().getSeller().getOffences().clear();
                    artworkOrder.getArtwork().getSeller().getPosts().clear();
                    artworkOrder.getArtwork().getSeller().getSelfCareBoxes().clear();
                    artworkOrder.getArtwork().getSeller().setPassword(null);
                    artworkOrder.getArtwork().getSeller().setSalt(null);
                    artworkOrder.getArtwork().getArtworkPrices().clear();
                    artworkOrder.setCustomer(null);
                    artworkOrder.setTransaction(null);
                    
            }
            return Response.status(Status.OK).entity(new RetrieveArtworkOrderRsp(artworkOrders)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    
    @Path("retrieveSelfCareBoxOrderById/{customerId}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSelfCareOrderById(@PathParam("customerId") Long customerId) {
        //pls use postman to test
        //there may be stackoverflow, i cannot test it at the moment since there is no create selfcarebox order session bean and restful methods
        try {
            List<SelfCareBoxOrder> selfCareOrders = orderSessionBeanLocal.retrieveAllSelfCareBoxOrderByCustomerId(customerId);
            
//                selfCareBoxOrder.getSelfCareBox().getRatings().clear();
//                selfCareBoxOrder.getSelfCareBox().getReviews().clear();
//                selfCareBoxOrder.getSelfCareBox().getSelfCareBoxOrders().clear();
//                selfCareBoxOrder.getSelfCareBox().getSelfCareSubscriptionDiscounts().clear();
//                selfCareBoxOrder.getSelfCareBox().setSeller(null);
//                selfCareBoxOrder.getSelfCareBox().getTags().clear();
//                selfCareBoxOrder.setCustomer(null);
//                selfCareBoxOrder.setTransaction(null);
//                return Response.status(Status.OK).entity(new RetrieveSelfCareBoxOrderRsp(selfCareBoxOrder)).build();
            for (SelfCareBoxOrder selfCareBoxOrder : selfCareOrders) {
                selfCareBoxOrder.getSelfCareBox().getRatings().clear();
                selfCareBoxOrder.getSelfCareBox().getReviews().clear();
                selfCareBoxOrder.getSelfCareBox().getSelfCareBoxOrders().clear();
                selfCareBoxOrder.getSelfCareBox().getSelfCareSubscriptionDiscounts().clear();
                selfCareBoxOrder.getSelfCareBox().getSeller().getArtworks().clear();
                selfCareBoxOrder.getSelfCareBox().getSeller().getComments().clear();
                selfCareBoxOrder.getSelfCareBox().getSeller().getCustomers().clear();
                selfCareBoxOrder.getSelfCareBox().getSeller().getOffences().clear();
                selfCareBoxOrder.getSelfCareBox().getSeller().getPosts().clear();
                selfCareBoxOrder.getSelfCareBox().getSeller().getOffences().clear();
                selfCareBoxOrder.getSelfCareBox().getSeller().getSelfCareBoxes().clear();
                selfCareBoxOrder.getSelfCareBox().getTags().clear();
                selfCareBoxOrder.getSelfCareBox().getSelfCareSubscriptionDiscounts().clear();
                selfCareBoxOrder.setCustomer(null);
                selfCareBoxOrder.setTransaction(null);
                selfCareBoxOrder.getSelfCareBox().getSeller().setPassword(null);
                selfCareBoxOrder.getSelfCareBox().getSeller().setSalt(null);             
            }
            return Response.status(Status.OK).entity(new RetrieveSelfCareBoxOrderRsp(selfCareOrders)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

}
