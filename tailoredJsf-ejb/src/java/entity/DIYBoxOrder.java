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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import util.enumeration.DurationEnum;
import util.enumeration.OrderStatusEnum;

/**
 *
 * @author Kaijing
 */
@Entity
public class DIYBoxOrder extends OrderHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(nullable = false,precision = 5, scale = 2)
    @NotNull
    @Digits(integer=3,fraction=2)
    private float priceAtTimeOfPurchase;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Packaging packaging;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Card card;
    @OneToMany(mappedBy = "DIYBoxOrder")
    private List<DIYBoxOrder_Items> itemsAssoc;
    
    public DIYBoxOrder() {
    }

    public DIYBoxOrder(float priceAtTimeOfPurchase, int quantity, OrderStatusEnum orderStatusEnum) {
        super(quantity, orderStatusEnum);
        this.priceAtTimeOfPurchase = priceAtTimeOfPurchase;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DIYBoxOrder)) {
            return false;
        }
        DIYBoxOrder other = (DIYBoxOrder) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DIYBoxOrder[ id=" + orderId + " ]";
    }

    /**
     * @return the packaging
     */
    public Packaging getPackaging() {
        return packaging;
    }

    /**
     * @param packaging the packaging to set
     */
    public void setPackaging(Packaging packaging) {
        this.packaging = packaging;
    }

    /**
     * @return the card
     */
    public Card getCard() {
        return card;
    }

    /**
     * @param card the card to set
     */
    public void setCard(Card card) {
        this.card = card;
    }

    /**
     * @return the priceAtTimeOfPurchase
     */
    public float getPriceAtTimeOfPurchase() {
        return priceAtTimeOfPurchase;
    }

    /**
     * @param priceAtTimeOfPurchase the priceAtTimeOfPurchase to set
     */
    public void setPriceAtTimeOfPurchase(float priceAtTimeOfPurchase) {
        this.priceAtTimeOfPurchase = priceAtTimeOfPurchase;
    }

    /**
     * @return the itemsAssoc
     */
    public List<DIYBoxOrder_Items> getItemsAssoc() {
        return itemsAssoc;
    }

    /**
     * @param itemsAssoc the itemsAssoc to set
     */
    public void setItemsAssoc(List<DIYBoxOrder_Items> itemsAssoc) {
        this.itemsAssoc = itemsAssoc;
    }
    
}
