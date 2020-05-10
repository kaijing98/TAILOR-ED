/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Seller;
import java.util.List;

/**
 *
 * @author yiningxing
 */
public class ViewAllFollowedSellerRsp {
    private List<Seller> followedSellers;

    public ViewAllFollowedSellerRsp() {
    }

    public ViewAllFollowedSellerRsp(List<Seller> followedSellers) {
        this.followedSellers = followedSellers;
    }

    public List<Seller> getFollowedSellers() {
        return followedSellers;
    }

    public void setFollowedSellers(List<Seller> followedSellers) {
        this.followedSellers = followedSellers;
    }
    
}
