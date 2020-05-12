/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;

/**
 *
 * @author Kaijing
 */
public class DIYBoxOrder_ItemsId implements Serializable {
    
    private long DIYBoxOrder;
    private long marketPlaceItem;
    
    @Override
    public int hashCode() {
        return (int)(DIYBoxOrder + marketPlaceItem);
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (object instanceof DIYBoxOrder_Items) {
            DIYBoxOrder_ItemsId other = (DIYBoxOrder_ItemsId) object;
            return (other.DIYBoxOrder == this.DIYBoxOrder) 
              && (other.marketPlaceItem == this.marketPlaceItem);
        }
        return false;
    }

    /**
     * @return the DIYBoxOrder
     */
    public long getDIYBoxOrder() {
        return DIYBoxOrder;
    }

    /**
     * @param DIYBoxOrder the DIYBoxOrder to set
     */
    public void setDIYBoxOrder(long DIYBoxOrder) {
        this.DIYBoxOrder = DIYBoxOrder;
    }

    /**
     * @return the marketPlaceItem
     */
    public long getMarketPlaceItem() {
        return marketPlaceItem;
    }

    /**
     * @param marketPlaceItem the marketPlaceItem to set
     */
    public void setMarketPlaceItem(long marketPlaceItem) {
        this.marketPlaceItem = marketPlaceItem;
    }
    
}
