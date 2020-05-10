/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Seller;

/**
 *
 * @author yiningxing
 */
public class SellerLoginRsp {
    private Seller seller;

    public SellerLoginRsp() {
    }

    public SellerLoginRsp(Seller seller) {
        this.seller = seller;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
    
    
    
}
