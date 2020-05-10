/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.SelfCareBoxOrder;
import java.util.List;

/**
 *
 * @author yiningxing
 */
public class RetrieveSelfCareBoxOrderRsp {
    private List<SelfCareBoxOrder> selfCareBoxOrders;

    public RetrieveSelfCareBoxOrderRsp() {
    }

    public RetrieveSelfCareBoxOrderRsp(List<SelfCareBoxOrder> selfCareBoxOrders) {
        this.selfCareBoxOrders = selfCareBoxOrders;
    }

    public List<SelfCareBoxOrder> getSelfCareBoxOrders() {
        return selfCareBoxOrders;
    }

    public void setSelfCareBoxOrders(List<SelfCareBoxOrder> selfCareBoxOrders) {
        this.selfCareBoxOrders = selfCareBoxOrders;
    }
}
