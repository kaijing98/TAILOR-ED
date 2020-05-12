/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Kaijing
 */
@Entity
@Table(name="DIYBOXORDER_ITEMS")
@IdClass(DIYBoxOrder_ItemsId.class)
public class DIYBoxOrder_Items {

    @Id
    @ManyToOne
    @JoinColumn(name = "DIYBoxOrder_id")
     DIYBoxOrder DIYBoxOrder;
    @Id
    @ManyToOne
    @JoinColumn(name = "marketPlaceItem_id")
     MarketPlaceItem marketPlaceItem;
    
    @Column(nullable = true)
    private int quantityOfItems;

    /**
     * @return the DIYBoxOrder
     */
    public DIYBoxOrder getDIYBoxOrder() {
        return DIYBoxOrder;
    }

    /**
     * @param DIYBoxOrder the DIYBoxOrder to set
     */
    public void setDIYBoxOrder(DIYBoxOrder DIYBoxOrder) {
        this.DIYBoxOrder = DIYBoxOrder;
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

    /**
     * @return the quantityOfItems
     */
    public int getQuantityOfItems() {
        return quantityOfItems;
    }

    /**
     * @param quantityOfItems the quantityOfItems to set
     */
    public void setQuantityOfItems(int quantityOfItems) {
        this.quantityOfItems = quantityOfItems;
    }
    
}
