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
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.RowEditEvent;
import util.enumeration.DurationEnum;

/**
 *
 * @author mac
 */
@Named(value = "addSelfCareSubscriptionDiscountRowView")
@ViewScoped
public class addSelfCareSubscriptionDiscountRowView implements Serializable{

    /**
     * Creates a new instance of addSelfCareSubscriptionDiscountRowView
     */
    public addSelfCareSubscriptionDiscountRowView() {
    }
    
    private List<SelfCareSubscriptionDiscount> selfCareSubscriptionDiscounts;
    
    private SelfCareSubscriptionDiscount selfCareSubscriptionDiscount;
    
    @PostConstruct
    public void init(){
        selfCareSubscriptionDiscounts = new ArrayList<SelfCareSubscriptionDiscount>();
        SelfCareSubscriptionDiscount sd = new SelfCareSubscriptionDiscount(0,DurationEnum.OnceOff);
        selfCareSubscriptionDiscounts.add(sd);
        sd = new SelfCareSubscriptionDiscount(0,DurationEnum.SixMonths);
        selfCareSubscriptionDiscounts.add(sd);
        sd = new SelfCareSubscriptionDiscount(0,DurationEnum.ThreeMonths);
        selfCareSubscriptionDiscounts.add(sd);
    }
     
    public void onAddNew() {
        SelfCareSubscriptionDiscount newsd = new SelfCareSubscriptionDiscount(0,DurationEnum.OnceOff);
        selfCareSubscriptionDiscounts.add(newsd);
        FacesMessage msg = new FacesMessage("New Self Care Subscription Discount added");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowEdit(RowEditEvent event){
        FacesMessage msg = new FacesMessage("Self Care Subscription Discount Edited", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<SelfCareSubscriptionDiscount> getSelfCareSubscriptionDiscounts() {
        return selfCareSubscriptionDiscounts;
    }

    public void setSelfCareSubscriptionDiscounts(List<SelfCareSubscriptionDiscount> selfCareSubscriptionDiscounts) {
        this.selfCareSubscriptionDiscounts = selfCareSubscriptionDiscounts;
    }

    public SelfCareSubscriptionDiscount getSelfCareSubscriptionDiscount() {
        return selfCareSubscriptionDiscount;
    }

    public void setSelfCareSubscriptionDiscount(SelfCareSubscriptionDiscount selfCareSubscriptionDiscount) {
        this.selfCareSubscriptionDiscount = selfCareSubscriptionDiscount;
    }

}
