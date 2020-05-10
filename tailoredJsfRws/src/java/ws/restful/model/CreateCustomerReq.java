/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Customer;
import entity.Tag;
import java.util.List;

/**
 *
 * @author yiningxing
 */
public class CreateCustomerReq {
    
    private Customer newCustomer;
    private List<Tag> tags;

    public CreateCustomerReq() {
    }

    public CreateCustomerReq(Customer newCustomer, List<Tag> tags) {
        this.newCustomer = newCustomer;
        this.tags = tags;
    }

    public Customer getNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(Customer newCustomer) {
        this.newCustomer = newCustomer;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
