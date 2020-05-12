/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Kaijing
 */
@Entity
public class Admin extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    
     @OneToMany(mappedBy = "admin")
    private List<Event> events;
    @OneToMany(mappedBy = "admin")
    private List<Packaging> packaging;
    @OneToMany(mappedBy = "admin")
    private List<Card> card;

    public Admin() {
    }

    public Admin(String username, String password, String firstName, String lastName, String email) {
        super(username, password, firstName, lastName, email);
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
        if (!(object instanceof Admin)) {
            return false;
        }
        Admin other = (Admin) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Admin[ id=" + userId + " ]";
    }

    /**
     * @return the events
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * @return the packaging
     */
    public List<Packaging> getPackaging() {
        return packaging;
    }

    /**
     * @param packaging the packaging to set
     */
    public void setPackaging(List<Packaging> packaging) {
        this.packaging = packaging;
    }

    /**
     * @return the card
     */
    public List<Card> getCard() {
        return card;
    }

    /**
     * @param card the card to set
     */
    public void setCard(List<Card> card) {
        this.card = card;
    }
    
}
