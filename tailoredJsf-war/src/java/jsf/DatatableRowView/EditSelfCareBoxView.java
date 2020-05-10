/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.DatatableRowView;

import entity.SelfCareSubscriptionDiscount;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import util.enumeration.DurationEnum;

/**
 *
 * @author decimatum
 */
@Named(value = "editSelfCareBoxView")
@ViewScoped
public class EditSelfCareBoxView implements Serializable {

    private List<SelfCareSubscriptionDiscount> discounts;
    private List<SelfCareSubscriptionDiscount> discountsEdit;
    
    /**
     * Creates a new instance of EditArtworkView
     */
    public EditSelfCareBoxView(){
    }
    
    public List<DurationEnum> getDurations(){
        List<DurationEnum> durations = new ArrayList<DurationEnum>();
        durations.add(DurationEnum.OnceOff);
        durations.add(DurationEnum.ThreeMonths);
        durations.add(DurationEnum.SixMonths);
        return durations;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Subscription Price Edited", null);
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

    /**
     * @return the discounts
     */
    public List<SelfCareSubscriptionDiscount> getDiscounts() {
        return discounts;
    }

    /**
     * @param discounts the discounts to set
     */
    public void setDiscounts(List<SelfCareSubscriptionDiscount> discounts) {
        this.discounts = discounts;
    }

    /**
     * @return the discountsEdit
     */
    public List<SelfCareSubscriptionDiscount> getDiscountsEdit() {
        return discountsEdit;
    }

    /**
     * @param discountsEdit the discountsEdit to set
     */
    public void setDiscountsEdit(List<SelfCareSubscriptionDiscount> discountsEdit) {
        this.discountsEdit = discountsEdit;
    }
    
}
