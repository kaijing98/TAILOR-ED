/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import java.util.List;
import util.enumeration.PaymentTypeEnum;

/**
 *
 * @author yiningxing
 */
public class CreateTransactionReq {
    private List<Long> artworkOrderList;
    private List<Long> selfCareList;
    private PaymentTypeEnum paymentType;
    private Long custID;

    public CreateTransactionReq() {
    }

    public CreateTransactionReq(List<Long> artworkOrderList, List<Long> selfCareList, PaymentTypeEnum paymentType, Long custID) {
        this.artworkOrderList = artworkOrderList;
        this.selfCareList = selfCareList;
        this.paymentType = paymentType;
        this.custID = custID;
    }

    public List<Long> getArtworkOrderList() {
        return artworkOrderList;
    }

    public void setArtworkOrderList(List<Long> artworkOrderList) {
        this.artworkOrderList = artworkOrderList;
    }

    public List<Long> getSelfCareList() {
        return selfCareList;
    }

    public void setSelfCareList(List<Long> selfCareList) {
        this.selfCareList = selfCareList;
    }

    public PaymentTypeEnum getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeEnum paymentType) {
        this.paymentType = paymentType;
    }

    public Long getCustID() {
        return custID;
    }

    public void setCustID(Long custID) {
        this.custID = custID;
    } 
}
