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
 * @author mac
 */
@Named(value = "editSelfCareSubscriptionDiscountView")
@ViewScoped
public class EditSelfCareSubscriptionDiscountView implements Serializable {

    /**
     * Creates a new instance of EditSelfCareSubscriptionDiscountView
     */
    public EditSelfCareSubscriptionDiscountView() {
    }
    private List<SelfCareSubscriptionDiscount> selfCareSubscriptionDiscounts;
    private List<SelfCareSubscriptionDiscount> selfCareSubscriptionDiscountsEdit;

    public List<SelfCareSubscriptionDiscount> getSelfCareSubscriptionDiscounts() {
        return selfCareSubscriptionDiscounts;
    }

    public void setSelfCareSubscriptionDiscounts(List<SelfCareSubscriptionDiscount> selfCareSubscriptionDiscounts) {
        this.selfCareSubscriptionDiscounts = selfCareSubscriptionDiscounts;
    }

    public List<SelfCareSubscriptionDiscount> getSelfCareSubscriptionDiscountsEdit() {
        return selfCareSubscriptionDiscountsEdit;
    }

    public void setSelfCareSubscriptionDiscountsEdit(List<SelfCareSubscriptionDiscount> selfCareSubscriptionDiscountsEdit) {
        this.selfCareSubscriptionDiscountsEdit = selfCareSubscriptionDiscountsEdit;
    }
    
    
    public List<DurationEnum> getDurations(){
        List<DurationEnum> durations = new ArrayList<DurationEnum>();
        durations.add(DurationEnum.SixMonths);
        durations.add(DurationEnum.OnceOff);
        durations.add(DurationEnum.ThreeMonths);
        return durations;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Self Care Box Edited", null);
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
