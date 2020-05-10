/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ProductCatalogueSessionBeanLocal;
import ejb.session.stateless.SellerSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import entity.Artwork;
import entity.ArtworkOrder;
import entity.ArtworkPrice;
import entity.OrderHistory;
import entity.SelfCareBoxOrder;
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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;
import util.enumeration.FormatEnum;
import util.enumeration.OrderStatusEnum;
import util.exception.ArtworkNotCreatedException;
import util.exception.ArtworkNotFoundException;
import util.exception.DeleteArtworkException;
import util.exception.TagNotFoundException;
import util.exception.UserNotFoundException;

/**
 *
 * @author decimatum
 */
@Named(value = "artworkJSFManagedBean")
@ViewScoped
public class artworkJSFManagedBean implements Serializable {

    @EJB(name = "SellerSessionBeanLocal")
    private SellerSessionBeanLocal sellerSessionBeanLocal;

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;

    @EJB(name = "ProductCatalogueSessionBeanLocal")
    private ProductCatalogueSessionBeanLocal productCatalogueSessionBeanLocal;
    
    private List<Artwork> artworks;
    private List<Artwork> filteredArtworks;
    private List<Tag> tags;
    private List<ArtworkPrice> prices;
    
    private List<Long> selectedTags;
    
    private Artwork newArtwork;
    
    private Artwork selectedArtworkEntityToUpdate;
    private List<Long> tagIdsUpdate;
    private List<ArtworkPrice> updateArtworkPrices;
    
    private Artwork artworkEntityToView;
    
    private UploadedFile file;
    private List<UploadedFile> files;
  
    private String destination = System.getProperty("user.dir") + "/../docroot/";
    
    @Enumerated(EnumType.STRING)
    private FormatEnum formatEnum;
    
    private List<String> selectedFormats;
  

    private float newPriceCanvas;
    
    private float newPricePhoto;
    
    private float newPricePoster;
    
    private List<ArtworkOrder> artworkOrders;

    private List<String> selectedOrderStatus;
    private ArtworkOrder updatedArtworkOrder;

    /**
     * Creates a new instance of artworkJSFManagedBean
     */
    public artworkJSFManagedBean() {
        newArtwork = new Artwork();
        artworks = new ArrayList<Artwork>();
        updateArtworkPrices = new ArrayList<>();
        filteredArtworks = new ArrayList<>();
        selectedOrderStatus = new ArrayList<>();
        tags = new ArrayList<Tag>();
        for ( OrderStatusEnum status : OrderStatusEnum.values()) {
            selectedOrderStatus.add(status.toString());
        }
    }
    
    @PostConstruct
    public void postConstruct(){
        Seller seller = (Seller)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser");        
        if(seller != null){
            artworks = sellerSessionBeanLocal.retrieveArtworkBySeller(seller.getUserId());
            setArtworkOrders(sellerSessionBeanLocal.retrieveArtworkOrder(seller.getUserId()));
        }
        tags = tagSessionBeanLocal.retrieveAllTags();
    }
    
    //this cell is called on the artwork order table edit
    public void onRowOrderEdit(RowEditEvent event){
        DataTable dataTable = (DataTable) event.getSource();
        //hence each row data is an artworkOrder object
        updatedArtworkOrder = (ArtworkOrder) dataTable.getRowData();
        
        updateArtworkOrder(updatedArtworkOrder);
        FacesMessage msg = new FacesMessage("Artwork Order Status Edited", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowOrderCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void updateArtworkOrder(ArtworkOrder updatedArtworkOrder){
        try
        {
            sellerSessionBeanLocal.updateArtworkOrderStatus(updatedArtworkOrder);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Order Status updated successfully", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect("/tailoredJsf-war/productManagement/orderManagement.xhtml");
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void deleteArtwork(ActionEvent event) {
        Artwork artworkEntityToDelete = (Artwork)event.getComponent().getAttributes().get("artworkEntityToDelete");
        try {
            productCatalogueSessionBeanLocal.deleteArtwork(artworkEntityToDelete.getArtworkId());
            artworks.remove(artworkEntityToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Artwork deleted successfully!", null));
        } catch (ArtworkNotFoundException | DeleteArtworkException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the artwork: " + ex.getMessage(), null));
        }
            
        
    }

    

    public void onRowArtworkPriceEdit(RowEditEvent event){
         FacesMessage msg = new FacesMessage("Artwork Price Edited", null);
         FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    //this cell is called on the price table edit in artwork
    public void onRowEdit(RowEditEvent event){
        DataTable dataTable = (DataTable) event.getSource();
        //hence each row data is a artworkPrice object
        ArtworkPrice currPrice = (ArtworkPrice) dataTable.getRowData();
        //updateArtworkPrices is the list of edited artwork prices for the current artwork
        updateArtworkPrices.add(currPrice);

        FacesMessage msg = new FacesMessage("Artwork Price Edited", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void doUpdateArtwork(ActionEvent event)
    {
        selectedArtworkEntityToUpdate = (Artwork)event.getComponent().getAttributes().get("artworkEntityToUpdate");
    }
    
    public void updateArtwork(ActionEvent event)
    {        
        try
        {
            //upload image if it exists
            //if it does not exist, we don't do anything aka current image stays 
            if (file != null) {
                upload();
            //note this is the image destination where it is saved
            System.out.println(destination + file.getFileName());
            selectedArtworkEntityToUpdate.setImage(file.getFileName());
            } 

            productCatalogueSessionBeanLocal.updateArtwork(selectedArtworkEntityToUpdate, selectedTags, updateArtworkPrices);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product updated successfully", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect("/tailoredJsf-war/productManagement/artworkManagement.xhtml");
        }
        catch(ArtworkNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void createNewArtwork(ActionEvent event) throws TagNotFoundException{
        Seller seller = (Seller)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUser");        
        if(seller != null){
            try{
                if(file!=null) {
                    upload();
                    newArtwork.setImage(file.getFileName());
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Image not set when creating artwork", null));
                }
                  
                Artwork createdArtwork = productCatalogueSessionBeanLocal.createArtwork(newArtwork, selectedTags, seller);
                
                ArtworkPrice canvasAP = new ArtworkPrice();
                canvasAP.setFormatEnum(FormatEnum.CANVASWRAP);
                canvasAP.setPrice(newPriceCanvas);
                productCatalogueSessionBeanLocal.createArtworkPrice(canvasAP, createdArtwork);
                
                ArtworkPrice photoAP = new ArtworkPrice();
                photoAP.setFormatEnum(FormatEnum.PHOTOPRINT);
                photoAP.setPrice(newPricePhoto);
                productCatalogueSessionBeanLocal.createArtworkPrice(photoAP, createdArtwork);
                
                ArtworkPrice posterAP = new ArtworkPrice();
                posterAP.setFormatEnum(FormatEnum.ARTPOSTER);
                posterAP.setPrice(newPricePoster);
                productCatalogueSessionBeanLocal.createArtworkPrice(posterAP, createdArtwork);
                
                artworks.add(createdArtwork);
                
                newArtwork= new Artwork();
                selectedTags = null;
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product created successfully", null));
                FacesContext.getCurrentInstance().getExternalContext().redirect("/tailoredJsf-war/productManagement/artworkManagement.xhtml");
                
            }catch(ArtworkNotCreatedException  ex){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error in creating artwork: " + ex.getMessage(), null));
            }
            catch(Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }

            
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error in creating artwork", null));
        }
    }
    
    // Upload method section
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

    
    // Getter and setter methods section
    public void setUpdateArtworkPrices(List<ArtworkPrice> updateArtworkPrices) {
        this.updateArtworkPrices = updateArtworkPrices;
    }

    public SellerSessionBeanLocal getSellerSessionBeanLocal() {
        return sellerSessionBeanLocal;
    }

    public void setSellerSessionBeanLocal(SellerSessionBeanLocal sellerSessionBeanLocal) {
        this.sellerSessionBeanLocal = sellerSessionBeanLocal;
    }

    public TagSessionBeanLocal getTagSessionBeanLocal() {
        return tagSessionBeanLocal;
    }

    public void setTagSessionBeanLocal(TagSessionBeanLocal tagSessionBeanLocal) {
        this.tagSessionBeanLocal = tagSessionBeanLocal;
    }

    public ProductCatalogueSessionBeanLocal getProductCatalogueSessionBeanLocal() {
        return productCatalogueSessionBeanLocal;
    }

    public void setProductCatalogueSessionBeanLocal(ProductCatalogueSessionBeanLocal productCatalogueSessionBeanLocal) {
        this.productCatalogueSessionBeanLocal = productCatalogueSessionBeanLocal;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    public List<Artwork> getFilteredArtworks() {
        return filteredArtworks;
    }

    public void setFilteredArtworks(List<Artwork> filteredArtworks) {
        this.filteredArtworks = filteredArtworks;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<ArtworkPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<ArtworkPrice> prices) {
        this.prices = prices;
    }

    public List<Long> getSelectedTags() {
        return selectedTags;
    }

    public void setSelectedTags(List<Long> selectedTags) {
        this.selectedTags = selectedTags;
    }

    public Artwork getNewArtwork() {
        return newArtwork;
    }

    public void setNewArtwork(Artwork newArtwork) {
        this.newArtwork = newArtwork;
    }

    public Artwork getSelectedArtworkEntityToUpdate() {
        return selectedArtworkEntityToUpdate;
    }

    public void setSelectedArtworkEntityToUpdate(Artwork selectedArtworkEntityToUpdate) {
        this.selectedArtworkEntityToUpdate = selectedArtworkEntityToUpdate;
    }

    public List<Long> getTagIdsUpdate() {
        return tagIdsUpdate;
    }

    public void setTagIdsUpdate(List<Long> tagIdsUpdate) {
        this.tagIdsUpdate = tagIdsUpdate;
    }

    public Artwork getArtworkEntityToView() {
        return artworkEntityToView;
    }

    public void setArtworkEntityToView(Artwork artworkEntityToView) {
        this.artworkEntityToView = artworkEntityToView;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<UploadedFile> getFiles() {
        return files;
    }

    public void setFiles(List<UploadedFile> files) {
        this.files = files;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public FormatEnum getFormatEnum() {
        return formatEnum;
    }

    public void setFormatEnum(FormatEnum formatEnum) {
        this.formatEnum = formatEnum;
    }

    public List<String> getSelectedFormats() {
        return selectedFormats;
    }

    public void setSelectedFormats(List<String> selectedFormats) {
        this.selectedFormats = selectedFormats;
    }

    public float getNewPriceCanvas() {
        return newPriceCanvas;
    }

    public void setNewPriceCanvas(float newPriceCanvas) {
        this.newPriceCanvas = newPriceCanvas;
    }

    public float getNewPricePhoto() {
        return newPricePhoto;
    }

    public void setNewPricePhoto(float newPricePhoto) {
        this.newPricePhoto = newPricePhoto;
    }

    public float getNewPricePoster() {
        return newPricePoster;
    }

    public void setNewPricePoster(float newPricePoster) {
        this.newPricePoster = newPricePoster;
    }

    /**
     * @return the artworkOrders
     */
    public List<ArtworkOrder> getArtworkOrders() {
        return artworkOrders;
    }

    /**
     * @param artworkOrders the artworkOrders to set
     */
    public void setArtworkOrders(List<ArtworkOrder> artworkOrders) {
        this.artworkOrders = artworkOrders;
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
     * @return the updatedArtworkOrder
     */
    public ArtworkOrder getUpdatedArtworkOrder() {
        return updatedArtworkOrder;
    }

    /**
     * @param updatedArtworkOrder the updatedArtworkOrder to set
     */
    public void setUpdatedArtworkOrder(ArtworkOrder updatedArtworkOrder) {
        this.updatedArtworkOrder = updatedArtworkOrder;
    }

    
}
