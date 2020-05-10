/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Transaction;
import java.util.List;

/**
 *
 * @author yiningxing
 */
public class RetrieveTransactionByCustIdRsp {
    private List<Transaction> transactions;

    public RetrieveTransactionByCustIdRsp() {
    }

    public RetrieveTransactionByCustIdRsp(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
