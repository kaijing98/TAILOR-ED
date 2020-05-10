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
public class UpdateCustomerReq {
    private Customer customer;
    private List<Tag> tags;

    public UpdateCustomerReq() {
    }

    public UpdateCustomerReq(Customer customer, List<Tag> tags) {
        this.customer = customer;
        this.tags = tags;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
