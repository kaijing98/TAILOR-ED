/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.ArtworkOrder;
import java.util.List;

/**
 *
 * @author yiningxing
 */
public class RetrieveArtworkOrderRsp {
    private List<ArtworkOrder> artworkOrders;

    public RetrieveArtworkOrderRsp() {
    }

    public RetrieveArtworkOrderRsp(List<ArtworkOrder> artworkOrders) {
        this.artworkOrders = artworkOrders;
    }

    public List<ArtworkOrder> getArtworkOrders() {
        return artworkOrders;
    }

    public void setArtworkOrders(List<ArtworkOrder> artworkOrders) {
        this.artworkOrders = artworkOrders;
    }
}
