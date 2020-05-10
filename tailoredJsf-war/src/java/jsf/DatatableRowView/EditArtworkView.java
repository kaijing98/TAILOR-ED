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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import util.enumeration.FormatEnum;

/**
 *
 * @author decimatum
 */
@Named(value = "editArtworkView")
@ViewScoped
public class EditArtworkView implements Serializable {

    private List<ArtworkPrice> artworkPrices;
    private List<ArtworkPrice> artworkPricesEdit;
    
    /**
     * Creates a new instance of EditArtworkView
     */
    public EditArtworkView(){
    }

    public List<ArtworkPrice> getArtworkPrices() {
        return artworkPrices;
    }

    public void setArtworkPrices(List<ArtworkPrice> artworkPrices) {
        this.artworkPrices = artworkPrices;
    }

    public List<ArtworkPrice> getArtworkPricesEdit() {
        return artworkPricesEdit;
    }

    public void setArtworkPricesEdit(List<ArtworkPrice> artworkPricesEdit) {
        this.artworkPricesEdit = artworkPricesEdit;
    }
    
    public List<FormatEnum> getFormats(){
        List<FormatEnum> formats = new ArrayList<FormatEnum>();
        formats.add(FormatEnum.ARTPOSTER);
        formats.add(FormatEnum.CANVASWRAP);
        formats.add(FormatEnum.PHOTOPRINT);
        return formats;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Artwork Edited", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
}
