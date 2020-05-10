/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ProductCatalogueSessionBeanLocal;
import ejb.session.stateless.SellerSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import entity.SelfCareBox;
import entity.SelfCareBoxOrder;
import entity.SelfCareSubscriptionDiscount;
import entity.Seller;
import entity.Tag;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.primefaces.component.datatable.DataTable;
import util.exception.InputDataValidationException;
import util.exception.SelfCareBoxExistException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;
import util.enumeration.DurationEnum;
import util.enumeration.OrderStatusEnum;
import util.exception.DeleteSelfCareBoxException;
import util.exception.DiscountNotFoundException;
import util.exception.SelfCareBoxNotFoundException;
import util.exception.SelfCareSubscriptionDiscountExistException;

/**
 *
 * @author Kaijing
 */
@Named(value = "selfCareBoxManagedBean")
@ViewScoped
public class selfCareBoxManagedBean implements Serializable {
    
    @EJB(name = "SellerSessionBeanLocal")
    private SellerSessionBeanLocal sellerSessionBeanLocal;

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;

    @EJB(name = "ProductCatalogueSessionBeanLocal")
    private ProductCatalogueSessionBeanLocal productCatalogueSessionBeanLocal;
    
    private List<SelfCareBox> selfcareboxes;
    private List<Tag> tags;
    private List<SelfCareSubscriptionDiscount> discounts;
    
    private List<Long> selectedTags;
    private SelfCareBox newSelfCareBox;
       
    private SelfCareBox selectedSelfCareBoxToUpdate;
    private List<Long> tagIdsUpdate;
    private List<SelfCareSubscriptionDiscount> updateDiscounts;
    
    private UploadedFile file;
    private List<UploadedFile> files;    
  
    private String destination = System.getProperty("user.dir") + "/../docroot/";
    
    @Enumerated(EnumType.STRING)
    private DurationEnum durationEnum;
    
    private List<String> selectedDurations;
    
    private int newDiscountOneTime;
    private int newDiscountThreeMonth;
    private int newDiscountSixMonth;
    
    private SelfCareBox boxToView;
    
    private List<SelfCareBoxOrder> selfCareBoxOrders;
    
    private List<String> selectedOrderStatus;
    private SelfCareBoxOrder updatedSelfCareBoxOrder;

    /**
     * Creates a new instance of selfCareBoxManagedBean
     */
    public selfCareBoxManagedBean() {
        selfcareboxes = new ArrayList<>();
        newSelfCareBox = new SelfCareBox();
        tags = new ArrayList<>();
        updateDiscounts = new ArrayList<>();
        selectedDurations = new ArrayList<>();
        selectedOrderStatus = new ArrayList<>();
        for ( OrderStatusEnum status : OrderStatusEnum.values()) {
            selectedOrderStatus.add(status.toString());
        }

    }
    
    @PostConstruct
    public void postConstruct(){
        Seller seller = (Seller)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser");        
        if(seller != null){
            setSelfcareboxes(sellerSessionBeanLocal.retrieveSelfCareBoxBySeller(seller.getUserId()));
            setSelfCareBoxOrders(sellerSessionBeanLocal.retrieveSelfCareBoxOrder(seller.getUserId()));
            System.out.println("SelfCareOrdder: " + sellerSessionBeanLocal.retrieveSelfCareBoxOrder(seller.getUserId()));
        }
        tags = tagSessionBeanLocal.retrieveAllTags();
    }
    
    //this cell is called on the selfcarebox order table edit
    public void onRowOrderEdit(RowEditEvent event){
        DataTable dataTable = (DataTable) event.getSource();
        //hence each row data is an selfcareboxOrder object
        updatedSelfCareBoxOrder = (SelfCareBoxOrder) dataTable.getRowData();
        
        updateSelfCareBoxOrder(updatedSelfCareBoxOrder);
        FacesMessage msg = new FacesMessage("SelfCareBox Order Status Edited", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowOrderCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void updateSelfCareBoxOrder(SelfCareBoxOrder updatedSelfCareBoxOrder){
        try
        {
            sellerSessionBeanLocal.updateSelfCareBoxOrderStatus(updatedSelfCareBoxOrder);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Order Status updated successfully", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect("/tailoredJsf-war/productManagement/orderManagement.xhtml");
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void createNewSelfCareBox(ActionEvent event) throws TagNotFoundException
    {        
        Seller seller = (Seller)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser");        
        if(seller != null){
            try{  
                if(file!=null) {
                    upload();
                    newSelfCareBox.setImage(file.getFileName());
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Image not set when creating selfcare box", null));
                }

                SelfCareBox scb = productCatalogueSessionBeanLocal.createSelfCareBox(newSelfCareBox, selectedTags, seller);
                
                SelfCareSubscriptionDiscount onetimeSD = new SelfCareSubscriptionDiscount();
                onetimeSD.setDurationEnum(durationEnum.OnceOff);
                onetimeSD.setDiscountPercentage(newDiscountOneTime);
                productCatalogueSessionBeanLocal.createSelfCareSubscriptionDiscount(onetimeSD, scb);
                
                SelfCareSubscriptionDiscount threemonSD = new SelfCareSubscriptionDiscount();
                threemonSD.setDurationEnum(durationEnum.ThreeMonths);
                threemonSD.setDiscountPercentage(newDiscountThreeMonth);
                productCatalogueSessionBeanLocal.createSelfCareSubscriptionDiscount(threemonSD, scb);
                
                SelfCareSubscriptionDiscount sixmonSD = new SelfCareSubscriptionDiscount();
                sixmonSD.setDurationEnum(durationEnum.SixMonths);
                sixmonSD.setDiscountPercentage(newDiscountSixMonth);
                productCatalogueSessionBeanLocal.createSelfCareSubscriptionDiscount(sixmonSD, scb);
                
                selfcareboxes.add(scb);

                newSelfCareBox = new SelfCareBox();
                selectedTags = null;
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product created successfully", null));
                FacesContext.getCurrentInstance().getExternalContext().redirect("/tailoredJsf-war/productManagement/selfCareBoxManagement.xhtml");
            } catch (SelfCareSubscriptionDiscountExistException|InputDataValidationException | SelfCareBoxExistException | UnknownPersistenceException ex)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new self care box: " + ex.getMessage(), null));
            } catch (IOException ex) {
                Logger.getLogger(selfCareBoxManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
           
    public void onRowEdit(RowEditEvent event){
        DataTable dataTable = (DataTable) event.getSource();
       
        SelfCareSubscriptionDiscount currDiscount = (SelfCareSubscriptionDiscount) dataTable.getRowData();
        updateDiscounts.add(currDiscount);

        FacesMessage msg = new FacesMessage("Self Care Subscription Discount Edited", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void upload() {
        if (file != null) {
            System.out.println("Destination: " + destination + file.getFileName());
            try {
                copyFile(file.getFileName(), file.getInputstream());
                FacesMessage msg = new FacesMessage("Success! ", file.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void copyFile(String fileName, InputStream in){
        try{
            OutputStream out = new FileOutputStream(new File(destination + fileName));
            
            int read = 0;
            byte[] bytes = new byte[1024];
            
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            
            in.close();
            out.flush();
            out.close();
            System.out.println("New file created!");
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }        
    }
    
    public void uploadMultiple(){
        if (files != null) {
            for (UploadedFile f : files) {
                FacesMessage message = new FacesMessage("Successful", f.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    public void deleteSelfCareBox(ActionEvent event) {
        SelfCareBox selfCareBoxToDelete = (SelfCareBox)event.getComponent().getAttributes().get("selfCareBoxToDelete");
        try {
            productCatalogueSessionBeanLocal.deleteSelfCareBox(selfCareBoxToDelete.getSelfCareBoxId());
            selfcareboxes.remove(selfCareBoxToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Self Care Box deleted successfully!", null));
        } catch (SelfCareBoxNotFoundException | DeleteSelfCareBoxException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the selfcarebox: " + ex.getMessage(), null));
        }
            
        
    }

    public void doUpdateBox(ActionEvent event)
    {
        selectedSelfCareBoxToUpdate = (SelfCareBox)event.getComponent().getAttributes().get("selfCareBoxToUpdate");
    }
    
    public void updateSelfCareBox(ActionEvent event)
    {        
        try
        {
            //upload image if it exists
            //if it does not exist, we don't do anything aka current image stays
            
            
            if (getFile() != null) {
                upload();
                //note this is the image destination where it is saved
                System.out.println(getDestination() + getFile().getFileName());
                selectedSelfCareBoxToUpdate.setImage(getFile().getFileName());
            } 
            
            productCatalogueSessionBeanLocal.updateSelfCareBox(selectedSelfCareBoxToUpdate, selectedTags, updateDiscounts);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product updated successfully", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect("/tailoredJsf-war/productManagement/selfCareBoxManagement.xhtml");
        }
        catch(DiscountNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    /**
     * @return the selfcareboxes
     */
    public List<SelfCareBox> getSelfcareboxes() {
        return selfcareboxes;
    }

    /**
     * @param selfcareboxes the selfcareboxes to set
     */
    public void setSelfcareboxes(List<SelfCareBox> selfcareboxes) {
        this.selfcareboxes = selfcareboxes;
    }

    /**
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * @return the durationEnum
     */
    public DurationEnum getDurationEnum() {
        return durationEnum;
    }

    /**
     * @param durationEnum the durationEnum to set
     */
    public void setDurationEnum(DurationEnum durationEnum) {
        this.durationEnum = durationEnum;
    }

    public SelfCareBox getNewSelfCareBox() {
        return newSelfCareBox;
    }

    public void setNewSelfCareBox(SelfCareBox newSelfCareBox) {
        this.newSelfCareBox = newSelfCareBox;
    }

    public List<SelfCareSubscriptionDiscount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<SelfCareSubscriptionDiscount> discounts) {
        this.discounts = discounts;
    }


    /**
     * @return the selectedTags
     */
    public List<Long> getSelectedTags() {
        return selectedTags;
    }

    /**
     * @param selectedTags the selectedTags to set
     */
    public void setSelectedTags(List<Long> selectedTags) {
        this.selectedTags = selectedTags;
    }

    /**
     * @return the selectedSelfCareBoxToUpdate
     */
    public SelfCareBox getSelectedSelfCareBoxToUpdate() {
        return selectedSelfCareBoxToUpdate;
    }

    /**
     * @param selectedSelfCareBoxToUpdate the selectedSelfCareBoxToUpdate to set
     */
    public void setSelectedSelfCareBoxToUpdate(SelfCareBox selectedSelfCareBoxToUpdate) {
        this.selectedSelfCareBoxToUpdate = selectedSelfCareBoxToUpdate;
    }

    /**
     * @return the tagIdsUpdate
     */
    public List<Long> getTagIdsUpdate() {
        return tagIdsUpdate;
    }

    /**
     * @param tagIdsUpdate the tagIdsUpdate to set
     */
    public void setTagIdsUpdate(List<Long> tagIdsUpdate) {
        this.tagIdsUpdate = tagIdsUpdate;
    }

    /**
     * @return the updateDiscounts
     */
    public List<SelfCareSubscriptionDiscount> getUpdateDiscounts() {
        return updateDiscounts;
    }

    /**
     * @param updateDiscounts the updateDiscounts to set
     */
    public void setUpdateDiscounts(List<SelfCareSubscriptionDiscount> updateDiscounts) {
        this.updateDiscounts = updateDiscounts;
    }

    /**
     * @return the selectedDurations
     */
    public List<String> getSelectedDurations() {
        return selectedDurations;
    }

    /**
     * @param selectedDurations the selectedDurations to set
     */
    public void setSelectedDurations(List<String> selectedDurations) {
        this.selectedDurations = selectedDurations;
    }

    /**
     * @return the file
     */
    public UploadedFile getFile() {
        return file;
    }


    /**
     * @param file the file to set
     */
    public void setFile(UploadedFile file) {
        this.file = file;
    }


    /**
     * @return the files
     */
    public List<UploadedFile> getFiles() {
        return files;
    }

    /**
     * @param files the files to set
     */
    public void setFiles(List<UploadedFile> files) {
        this.files = files;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getNewDiscountOneTime() {
        return newDiscountOneTime;
    }

    public void setNewDiscountOneTime(int newDiscountOneTime) {
        this.newDiscountOneTime = newDiscountOneTime;
    }

    public int getNewDiscountThreeMonth() {
        return newDiscountThreeMonth;
    }

    public void setNewDiscountThreeMonth(int newDiscountThreeMonth) {
        this.newDiscountThreeMonth = newDiscountThreeMonth;
    }

    public int getNewDiscountSixMonth() {
        return newDiscountSixMonth;
    }

    public void setNewDiscountSixMonth(int newDiscountSixMonth) {
        this.newDiscountSixMonth = newDiscountSixMonth;
    }

    public SelfCareBox getBoxToView() {
        return boxToView;
    }

    public void setBoxToView(SelfCareBox boxToView) {
        this.boxToView = boxToView;
    } 

    /**
     * @return the selfCareBoxOrders
     */
    public List<SelfCareBoxOrder> getSelfCareBoxOrders() {
        return selfCareBoxOrders;
    }

    /**
     * @param selfCareBoxOrders the selfCareBoxOrders to set
     */
    public void setSelfCareBoxOrders(List<SelfCareBoxOrder> selfCareBoxOrders) {
        this.selfCareBoxOrders = selfCareBoxOrders;
    }

    /**
     * @return the selectedOrderStatus
     */
    public List<String> getSelectedOrderStatus() {
        return selectedOrderStatus;
    }

    /**
     * @param selectedOrderStatus the selectedOrderStatus to set
     */
    public void setSelectedOrderStatus(List<String> selectedOrderStatus) {
        this.selectedOrderStatus = selectedOrderStatus;
    }

    /**
     * @return the updatedSelfCareBoxOrder
     */
    public SelfCareBoxOrder getUpdatedSelfCareBoxOrder() {
        return updatedSelfCareBoxOrder;
    }

    /**
     * @param updatedSelfCareBoxOrder the updatedSelfCareBoxOrder to set
     */
    public void setUpdatedSelfCareBoxOrder(SelfCareBoxOrder updatedSelfCareBoxOrder) {
        this.updatedSelfCareBoxOrder = updatedSelfCareBoxOrder;
    }
}
