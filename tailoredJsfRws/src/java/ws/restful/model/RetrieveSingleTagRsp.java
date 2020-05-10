/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Tag;

/**
 *
 * @author yiningxing
 */
public class RetrieveSingleTagRsp {
    private Tag tag;

    public RetrieveSingleTagRsp() {
    }

    public RetrieveSingleTagRsp(Tag tag) {
        this.tag = tag;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
