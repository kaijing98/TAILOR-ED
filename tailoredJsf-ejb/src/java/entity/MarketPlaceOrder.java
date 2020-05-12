/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import util.enumeration.OrderStatusEnum;

/**
 *
 * @author Kaijing
 */
@Entity
public class MarketPlaceOrder extends OrderHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(nullable = false,precision = 5, scale = 2)
    @NotNull
    @Digits(integer=3,fraction=2)
    private float priceAtTimeOfPurchase;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)  
    private MarketPlaceItem marketPlaceItem;
    
    public MarketPlaceOrder(){
        
    }
    
    public MarketPlaceOrder(float priceAtTimeOfPurchase, int quantity, OrderStatusEnum orderStatusEnum){
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
        if (!(object instanceof MarketPlaceOrder)) {
            return false;
        }
        MarketPlaceOrder other = (MarketPlaceOrder) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MarketPlaceOrder[ id=" + orderId + " ]";
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
     * @return the marketPlaceItem
     */
    public MarketPlaceItem getMarketPlaceItem() {
        return marketPlaceItem;
    }

    /**
     * @param marketPlaceItem the marketPlaceItem to set
     */
    public void setMarketPlaceItem(MarketPlaceItem marketPlaceItem) {
        this.marketPlaceItem = marketPlaceItem;
    }
    
}
