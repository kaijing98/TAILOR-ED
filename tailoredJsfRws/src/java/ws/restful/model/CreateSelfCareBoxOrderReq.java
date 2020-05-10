/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

/**
 *
 * @author mac
 */
public class CreateSelfCareBoxOrderReq {
    private Long selfCareBoxID;
    private Long boxDiscountID;
    private Long customerID;
    private int quantity;

    public CreateSelfCareBoxOrderReq() {
    }

    public CreateSelfCareBoxOrderReq(Long selfCareBoxID, Long boxDiscountID, Long customerID, int quantity) {
        this.selfCareBoxID = selfCareBoxID;
        this.boxDiscountID = boxDiscountID;
        this.customerID = customerID;
        this.quantity = quantity;
    }

    public Long getSelfCareBoxID() {
        return selfCareBoxID;
    }

    public void setSelfCareBoxID(Long selfCareBoxID) {
        this.selfCareBoxID = selfCareBoxID;
    }

    public Long getBoxDiscountID() {
        return boxDiscountID;
    }

    public void setBoxDiscountID(Long boxDiscountID) {
        this.boxDiscountID = boxDiscountID;
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
