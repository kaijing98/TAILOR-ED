/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

/**
 *
 * @author yiningxing
 */
public class CreateArtworkOrderReq {
    private Long artworkID;
    private Long artworkPriceID;
    private Long customerID;
    private int quantity;

    public CreateArtworkOrderReq() {
    }

    public CreateArtworkOrderReq(Long artworkID, Long artworkPriceID, Long customerID, int quantity) {
        this.artworkID = artworkID;
        this.artworkPriceID = artworkPriceID;
        this.customerID = customerID;
        this.quantity = quantity;
    }

    public Long getArtworkID() {
        return artworkID;
    }

    public void setArtworkID(Long artworkID) {
        this.artworkID = artworkID;
    }

    public Long getArtworkPriceID() {
        return artworkPriceID;
    }

    public void setArtworkPriceID(Long artworkPriceID) {
        this.artworkPriceID = artworkPriceID;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
