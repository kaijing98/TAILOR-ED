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
public class CreateTransactionRsp {
    private Long transactionID;

    public CreateTransactionRsp() {
    }

    public CreateTransactionRsp(Long transactionID) {
        this.transactionID = transactionID;
    }

    public Long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Long transactionID) {
        this.transactionID = transactionID;
    }
}
