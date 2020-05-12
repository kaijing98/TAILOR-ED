/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Kaijing
 */
@Entity
public class Customer extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(columnDefinition = "boolean default false")
    @NotNull
    private boolean isBanned;
    
    @Column(nullable = true)
    private String shippingAddress;
    
    @Column(nullable = true)
    private String shippingUnitNum;
    
    @Column(nullable = true)
    private String shippingPostalCode;
    
    @OneToMany(mappedBy = "customer")
    private List<Review> reviews;
    @ManyToMany(mappedBy = "customers")
    private List<Tag> tags;
    @OneToMany(mappedBy = "customer")
    private List<OrderHistory> orders;
    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;
    @ManyToMany(mappedBy = "customers", fetch = FetchType.LAZY)
    private List<Seller> sellers;
    @ManyToMany(mappedBy = "customerlike", fetch = FetchType.LAZY)
    private List<Post> likedPosts;
    @ManyToMany(mappedBy = "customersave", fetch = FetchType.LAZY)
    private List<Post> savedPosts;
    
    @ManyToMany(mappedBy = "customerlike", fetch = FetchType.LAZY)
    private List<Artwork> likedArtworks;
    @ManyToMany(mappedBy = "customersave", fetch = FetchType.LAZY)
    private List<Artwork> savedArtworks;
    
    @ManyToMany(mappedBy = "customerlike", fetch = FetchType.LAZY)
    private List<SelfCareBox> likedSelfCareBoxes;
    @ManyToMany(mappedBy = "customersave", fetch = FetchType.LAZY)
    private List<SelfCareBox> savedSelfCareBoxes;
    
    @ManyToMany(mappedBy = "customerlike", fetch = FetchType.LAZY)
    private List<MarketPlaceItem> likedMarketPlaceItem;
    @ManyToMany(mappedBy = "customersave", fetch = FetchType.LAZY)
    private List<MarketPlaceItem> savedMarketPlaceItem;

    public Customer() {
    }

    public Customer(String username, String password, String firstName, String lastName, String email, String shippingAddress, String shippingUnitNum, String shippingPostalCode) {
        super(username, password, firstName, lastName, email);
        this.isBanned = false;
        this.shippingAddress = shippingAddress;
        this.shippingUnitNum = shippingUnitNum;
        this.shippingPostalCode = shippingPostalCode;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ id=" + userId + " ]";
    }

    /**
     * @return the isBanned
     */
    public boolean isIsBanned() {
        return isBanned;
    }

    /**
     * @param isBanned the isBanned to set
     */
    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    /**
     * @return the shippingAddress
     */
    public String getShippingAddress() {
        return shippingAddress;
    }

    /**
     * @param shippingAddress the shippingAddress to set
     */
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    /**
     * @return the shippingUnitNum
     */
    public String getShippingUnitNum() {
        return shippingUnitNum;
    }

    /**
     * @param shippingUnitNum the shippingUnitNum to set
     */
    public void setShippingUnitNum(String shippingUnitNum) {
        this.shippingUnitNum = shippingUnitNum;
    }

    /**
     * @return the shippingPostalCode
     */
    public String getShippingPostalCode() {
        return shippingPostalCode;
    }

    /**
     * @param shippingPostalCode the shippingPostalCode to set
     */
    public void setShippingPostalCode(String shippingPostalCode) {
        this.shippingPostalCode = shippingPostalCode;
    }
    
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    
    public List<OrderHistory> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderHistory> orders) {
        this.orders = orders;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * @return the sellers
     */
    public List<Seller> getSellers() {
        return sellers;
    }

    /**
     * @param sellers the sellers to set
     */
    public void setSellers(List<Seller> sellers) {
        this.sellers = sellers;
    }

    /**
     * @return the likedPosts
     */
    public List<Post> getLikedPosts() {
        return likedPosts;
    }

    /**
     * @param likedPosts the likedPosts to set
     */
    public void setLikedPosts(List<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    /**
     * @return the savedPosts
     */
    public List<Post> getSavedPosts() {
        return savedPosts;
    }

    /**
     * @param savedPosts the savedPosts to set
     */
    public void setSavedPosts(List<Post> savedPosts) {
        this.savedPosts = savedPosts;
    }

    /**
     * @return the likedArtworks
     */
    public List<Artwork> getLikedArtworks() {
        return likedArtworks;
    }

    /**
     * @param likedArtworks the likedArtworks to set
     */
    public void setLikedArtworks(List<Artwork> likedArtworks) {
        this.likedArtworks = likedArtworks;
    }

    /**
     * @return the savedArtworks
     */
    public List<Artwork> getSavedArtworks() {
        return savedArtworks;
    }

    /**
     * @param savedArtworks the savedArtworks to set
     */
    public void setSavedArtworks(List<Artwork> savedArtworks) {
        this.savedArtworks = savedArtworks;
    }

    /**
     * @return the likedSelfCareBoxes
     */
    public List<SelfCareBox> getLikedSelfCareBoxes() {
        return likedSelfCareBoxes;
    }

    /**
     * @param likedSelfCareBoxes the likedSelfCareBoxes to set
     */
    public void setLikedSelfCareBoxes(List<SelfCareBox> likedSelfCareBoxes) {
        this.likedSelfCareBoxes = likedSelfCareBoxes;
    }

    /**
     * @return the savedSelfCareBoxes
     */
    public List<SelfCareBox> getSavedSelfCareBoxes() {
        return savedSelfCareBoxes;
    }

    /**
     * @param savedSelfCareBoxes the savedSelfCareBoxes to set
     */
    public void setSavedSelfCareBoxes(List<SelfCareBox> savedSelfCareBoxes) {
        this.savedSelfCareBoxes = savedSelfCareBoxes;
    }

    /**
     * @return the likedMarketPlaceItem
     */
    public List<MarketPlaceItem> getLikedMarketPlaceItem() {
        return likedMarketPlaceItem;
    }

    /**
     * @param likedMarketPlaceItem the likedMarketPlaceItem to set
     */
    public void setLikedMarketPlaceItem(List<MarketPlaceItem> likedMarketPlaceItem) {
        this.likedMarketPlaceItem = likedMarketPlaceItem;
    }

    /**
     * @return the savedMarketPlaceItem
     */
    public List<MarketPlaceItem> getSavedMarketPlaceItem() {
        return savedMarketPlaceItem;
    }

    /**
     * @param savedMarketPlaceItem the savedMarketPlaceItem to set
     */
    public void setSavedMarketPlaceItem(List<MarketPlaceItem> savedMarketPlaceItem) {
        this.savedMarketPlaceItem = savedMarketPlaceItem;
    }
}
