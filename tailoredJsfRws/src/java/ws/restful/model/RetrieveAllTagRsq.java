/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Tag;
import java.util.List;

/**
 *
 * @author yiningxing
 */
public class RetrieveAllTagRsq {
    
    private List<Tag> tags;

    public RetrieveAllTagRsq() {
    }

    public RetrieveAllTagRsq(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    
}
