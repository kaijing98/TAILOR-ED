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
public class Packaging implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packagingId;
    
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
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)  
    private Admin admin;
    @OneToMany(mappedBy = "packaging")
    private List<DIYBoxOrder> DIYBoxOrders;
    
    public Packaging(){
        
    }
    
    public Packaging(String name, float price, String image){
        this.name = name;
        this.price = price;
        this.image = image;
              
    }

    public Long getPackagingId() {
        return packagingId;
    }

    public void setPackagingId(Long packagingId) {
        this.packagingId = packagingId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (packagingId != null ? packagingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the packagingId fields are not set
        if (!(object instanceof Packaging)) {
            return false;
        }
        Packaging other = (Packaging) object;
        if ((this.packagingId == null && other.packagingId != null) || (this.packagingId != null && !this.packagingId.equals(other.packagingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Packaging[ id=" + packagingId + " ]";
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
