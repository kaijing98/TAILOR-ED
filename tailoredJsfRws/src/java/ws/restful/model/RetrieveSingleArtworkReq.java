/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Artwork;

/**
 *
 * @author decimatum
 */
public class RetrieveSingleArtworkReq {
    private Artwork artwork;
    
    public RetrieveSingleArtworkReq(){
    }
    
    public RetrieveSingleArtworkReq(Artwork artwork){
        this.artwork = artwork;
    }
    
    public Artwork getArtwork(){
        return artwork;
    }
    
    public void setArtwork(Artwork artwork){
        this.artwork = artwork;
    }
}
