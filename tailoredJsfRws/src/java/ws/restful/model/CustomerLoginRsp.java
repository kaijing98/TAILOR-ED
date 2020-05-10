/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Customer;

/**
 *
 * @author yiningxing
 */
public class CustomerLoginRsp {
    private Customer customer;

    public CustomerLoginRsp(Customer customer) {
        this.customer = customer;
    }

    public CustomerLoginRsp() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
