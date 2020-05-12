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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Volunteer extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(columnDefinition = "boolean default false")
    @NotNull
    private boolean isVerified;
    
    @Column(nullable = true)
    private String credentialsFile;
    
    @Column(nullable = true)
    private String story;
    
    @Column(nullable = true)
    private String aboutYourself;
    
    @Column(columnDefinition = "boolean default false")
    @NotNull
    private boolean isMedicalProfessional;
    
    @OneToMany(mappedBy = "volunteer")
    private List<Resource> resources;
    
    public Volunteer(){
        
    }
    
    public Volunteer(String username, String password, String firstName, String lastName, String email, String credentialsFile, String story, String aboutYourself) {
        super(username, password, firstName, lastName, email);
        this.isVerified = false;
        this.credentialsFile = credentialsFile;
        this.story = story;
        this.aboutYourself = aboutYourself;
        this.isMedicalProfessional = false;
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
        if (!(object instanceof Volunteer)) {
            return false;
        }
        Volunteer other = (Volunteer) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Volunteer[ id=" + userId + " ]";
    }

    public boolean isIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getCredentialsFile() {
        return credentialsFile;
    }

    public void setCredentialsFile(String credentialsFile) {
        this.credentialsFile = credentialsFile;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getAboutYourself() {
        return aboutYourself;
    }

    public void setAboutYourself(String aboutYourself) {
        this.aboutYourself = aboutYourself;
    }

    public boolean isIsMedicalProfessional() {
        return isMedicalProfessional;
    }

    public void setIsMedicalProfessional(boolean isMedicalProfessional) {
        this.isMedicalProfessional = isMedicalProfessional;
    }

    public List<Resource> getResources() {
        return resources;
    }

/**
     * @param resources the resources to set
     */
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
    
}