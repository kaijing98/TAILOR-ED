/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import java.util.List;

/**
 *
 * @author yiningxing
 */
public class FilterArtworkByTagsReq {

    private List<Long> tagIds;

    public FilterArtworkByTagsReq() {
    }

    public FilterArtworkByTagsReq(List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }
}
