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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import util.enumeration.EventTypeEnum;

/**
 *
 * @author Kaijing
 */
@Entity
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    
    @Column(unique = true, nullable = false)
    @NotNull
    private String name;
    @Column(nullable = false)
    @NotNull
    private String description;
    @Column(nullable = false)
    @NotNull
    private String venue;
    @Column(nullable = false)
    @NotNull
    private String time;
    @Column(nullable = false, precision = 5, scale = 2)
    @NotNull
    @Digits(integer=3,fraction=2)
    private float price;
    @Column(nullable = false)
    @NotNull
    private List<String> image;
    @Enumerated(EnumType.STRING)
    private EventTypeEnum eventTypeEnum;
    @Column(columnDefinition = "boolean default false")
    @NotNull
    private boolean isDeleted;
    
    @OneToMany(mappedBy = "event")
    private List<EventOrder> eventOrders;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)  
    private Admin admin;
    
    public Event(){
        
    }
    
    public Event(String name, String description, String venue, String time, float price, List<String> image, EventTypeEnum eventTypeEnum) {
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.time = time;
        this.price = price;
        this.image = image;
        this.eventTypeEnum = eventTypeEnum;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventId != null ? eventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the eventId fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.eventId == null && other.eventId != null) || (this.eventId != null && !this.eventId.equals(other.eventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Event[ id=" + eventId + " ]";
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
     * @return the venue
     */
    public String getVenue() {
        return venue;
    }

    /**
     * @param venue the venue to set
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
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
     * @return the eventTypeEnum
     */
    public EventTypeEnum getEventTypeEnum() {
        return eventTypeEnum;
    }

    /**
     * @param eventTypeEnum the eventTypeEnum to set
     */
    public void setEventTypeEnum(EventTypeEnum eventTypeEnum) {
        this.eventTypeEnum = eventTypeEnum;
    }

    /**
     * @return the eventOrders
     */
    public List<EventOrder> getEventOrders() {
        return eventOrders;
    }

    /**
     * @param eventOrders the eventOrders to set
     */
    public void setEventOrders(List<EventOrder> eventOrders) {
        this.eventOrders = eventOrders;
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
     * @return the isDeleted
     */
    public boolean isIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
}
