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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Kaijing
 */
@Entity
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;
    
    @Column(nullable = false)
    @NotNull
    private String name;
    @Column(nullable = false,precision = 5, scale = 2)
    @NotNull
    @Digits(integer=3,fraction=2)
    private float price;
    @Column(nullable = false)
    @NotNull
    private String image;
    @Column(nullable = true)
    private String message;
    @Column(nullable = false)
    @NotNull
    private String sender;
    @Column(nullable = false)
    @NotNull
    private String receipient;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)  
    private Admin admin;
    @OneToMany(mappedBy = "card")
    private List<DIYBoxOrder> DIYBoxOrders;
    
    public Card(){
        
    }
    
    public Card(String name, float price, String image, String message, String sender, String receipient){
        this.name = name;
        this.price = price;
        this.image = image;
        this.message = message;
        this.receipient = receipient;
        this.sender = sender;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cardId != null ? cardId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the cardId fields are not set
        if (!(object instanceof Card)) {
            return false;
        }
        Card other = (Card) object;
        if ((this.cardId == null && other.cardId != null) || (this.cardId != null && !this.cardId.equals(other.cardId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Card[ id=" + cardId + " ]";
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
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the receipient
     */
    public String getReceipient() {
        return receipient;
    }

    /**
     * @param receipient the receipient to set
     */
    public void setReceipient(String receipient) {
        this.receipient = receipient;
    }

    /**
     * @return the admin
     */
    public Admin getAdmin() {
        return admin;
    }

    /**
     * @param admin the admin to set
     */
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    /**
     * @return the DIYBoxOrders
     */
    public List<DIYBoxOrder> getDIYBoxOrders() {
        return DIYBoxOrders;
    }

    /**
     * @param DIYBoxOrders the DIYBoxOrders to set
     */
    public void setDIYBoxOrders(List<DIYBoxOrder> DIYBoxOrders) {
        this.DIYBoxOrders = DIYBoxOrders;
    }
    
}
