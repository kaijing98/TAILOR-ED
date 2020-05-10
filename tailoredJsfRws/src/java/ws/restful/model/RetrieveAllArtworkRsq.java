/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Artwork;
import java.util.List;

/**
 *
 * @author yiningxing
 */
public class RetrieveAllArtworkRsq {
    private List<Artwork> artworks;

    public RetrieveAllArtworkRsq() {
    }
    
    public RetrieveAllArtworkRsq(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    } 
}
