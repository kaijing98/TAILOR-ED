/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.SelfCareBoxOrder;

/**
 *
 * @author mac
 */
public class CreateSelfCareBoxOrderRsp {
    
    private Long selfCareBoxOrderID;

    public CreateSelfCareBoxOrderRsp() {
    }

    public CreateSelfCareBoxOrderRsp(Long selfCareBoxOrderID) {
        this.selfCareBoxOrderID = selfCareBoxOrderID;
    }

    public Long getSelfCareBoxOrderID() {
        return selfCareBoxOrderID;
    }

    public void setSelfCareBoxOrderID(Long selfCareBoxOrderID) {
        this.selfCareBoxOrderID = selfCareBoxOrderID;
    }
}
