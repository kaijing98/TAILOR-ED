/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.DatatableRowView;

import entity.ArtworkPrice;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.RowEditEvent;
import util.enumeration.FormatEnum;

/**
 *
 * @author decimatum
 */
@Named(value = "addArtworkPriceRowView")
@ViewScoped
public class addArtworkPriceRowView implements Serializable{

    private List<ArtworkPrice> artworkPrices;
    
    private ArtworkPrice artworkPrice;
    
    @PostConstruct
    public void init(){
        artworkPrices = new ArrayList<ArtworkPrice>();
        ArtworkPrice ap = new ArtworkPrice(0.00f,FormatEnum.ARTPOSTER);
        artworkPrices.add(ap);
        ap = new ArtworkPrice(0.00f,FormatEnum.CANVASWRAP);
        artworkPrices.add(ap);
        ap = new ArtworkPrice(0.00f,FormatEnum.PHOTOPRINT);
        artworkPrices.add(ap);
    }
    
    public ArtworkPrice getArtworkPrice(){
        return this.artworkPrice;
    }
    
    public void setArtworkPrice(ArtworkPrice ap){
        this.artworkPrice = ap;
    }
    
    public addArtworkPriceRowView() {
    }
    
    public void onAddNew() {
        // Add one new car to the table:
        ArtworkPrice newAP = new ArtworkPrice(0.00f, FormatEnum.ARTPOSTER);
        artworkPrices.add(newAP);
        FacesMessage msg = new FacesMessage("New Artwork Price added");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowEdit(RowEditEvent event){
        FacesMessage msg = new FacesMessage("Artwork Price Edited", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<ArtworkPrice> getArtworkPrices() {
        return artworkPrices;
    }

    public void setArtworkPrices(List<ArtworkPrice> artworkPrices) {
        this.artworkPrices = artworkPrices;
    }

    
}
