/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Kaijing
 */
@Entity
public class MarketPlaceItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long marketPlaceItemId;
    
    @Column(nullable = false, unique = true)
    @NotNull
    @Size(min=5,max=40)
    private String name;
    @Column(nullable = false, unique = true)
    @NotNull
    @Size(min=5,max=40)
    private String description;
    @Column(nullable = false,precision = 5, scale = 2)
    @NotNull
    @Digits(integer=3,fraction=2)
    private float price;
    @Column(nullable = false)
    @NotNull
    private List<String> image;
    @Column(nullable = true)
    private int totalLikes;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Seller seller;
    @ManyToMany(mappedBy = "marketPlaceItems")
    private List<Tag> tags;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "MARKETPLACEITEM_LIKES", joinColumns = @JoinColumn(name = "customerlike_id"),
               inverseJoinColumns = @JoinColumn(name = "marketplaceitem_id"))
    private List<Customer> customerlike;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "MARKETPLACEITEM_SAVES", joinColumns = @JoinColumn(name = "customersave_id"),
               inverseJoinColumns = @JoinColumn(name = "marketplaceitem_id"))
    private List<Customer> customersave;
    @OneToMany(mappedBy = "marketPlaceItem")
    private List<MarketPlaceOrder> marketPlaceOrders;
    @OneToMany(mappedBy = "marketPlaceItem")
    private List<DIYBoxOrder_Items> DIYBoxOrdersAssoc;
    
    public MarketPlaceItem(){
        
    }
    
    public MarketPlaceItem(String name, String description, float price, List<String> image, int totalLikes){
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.totalLikes = totalLikes;
    }

    public Long getMarketPlaceItemId() {
        return marketPlaceItemId;
    }

    public void setMarketPlaceItemId(Long marketPlaceItemId) {
        this.marketPlaceItemId = marketPlaceItemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (marketPlaceItemId != null ? marketPlaceItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the marketPlaceItemId fields are not set
        if (!(object instanceof MarketPlaceItem)) {
            return false;
        }
        MarketPlaceItem other = (MarketPlaceItem) object;
        if ((this.marketPlaceItemId == null && other.marketPlaceItemId != null) || (this.marketPlaceItemId != null && !this.marketPlaceItemId.equals(other.marketPlaceItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MarketPlaceItem[ id=" + marketPlaceItemId + " ]";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return the image
     */
    public List<String> getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(List<String> image) {
        this.image = image;
    }

    /**
     * @return the totalLikes
     */
    public int getTotalLikes() {
        return totalLikes;
    }

    /**
     * @param totalLikes the totalLikes to set
     */
    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    /**
     * @return the seller
     */
    public Seller getSeller() {
        return seller;
    }

    /**
     * @param seller the seller to set
     */
    public void setSeller(Seller seller) {
        this.seller = seller;
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
     * @return the customerlike
     */
    public List<Customer> getCustomerlike() {
        return customerlike;
    }

    /**
     * @param customerlike the customerlike to set
     */
    public void setCustomerlike(List<Customer> customerlike) {
        this.customerlike = customerlike;
    }

    /**
     * @return the customersave
     */
    public List<Customer> getCustomersave() {
        return customersave;
    }

    /**
     * @param customersave the customersave to set
     */
    public void setCustomersave(List<Customer> customersave) {
        this.customersave = customersave;
    }

    /**
     * @return the marketPlaceOrders
     */
    public List<MarketPlaceOrder> getMarketPlaceOrders() {
        return marketPlaceOrders;
    }

    /**
     * @param marketPlaceOrders the marketPlaceOrders to set
     */
    public void setMarketPlaceOrders(List<MarketPlaceOrder> marketPlaceOrders) {
        this.marketPlaceOrders = marketPlaceOrders;
    }

    /**
     * @return the DIYBoxOrdersAssoc
     */
    public List<DIYBoxOrder_Items> getDIYBoxOrdersAssoc() {
        return DIYBoxOrdersAssoc;
    }

    /**
     * @param DIYBoxOrdersAssoc the DIYBoxOrdersAssoc to set
     */
    public void setDIYBoxOrdersAssoc(List<DIYBoxOrder_Items> DIYBoxOrdersAssoc) {
        this.DIYBoxOrdersAssoc = DIYBoxOrdersAssoc;
    }
    
}
