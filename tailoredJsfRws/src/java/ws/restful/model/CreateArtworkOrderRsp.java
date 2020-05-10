/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.ArtworkOrder;

/**
 *
 * @author yiningxing
 */
public class CreateArtworkOrderRsp {
    
    private Long artworkOrderID;

    public CreateArtworkOrderRsp() {
    }

    public CreateArtworkOrderRsp(Long artworkOrderID) {
        this.artworkOrderID = artworkOrderID;
    }

    public Long getArtworkOrderID() {
        return artworkOrderID;
    }

    public void setArtworkOrderID(Long artworkOrderID) {
        this.artworkOrderID = artworkOrderID;
    }
}
