/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.SelfCareBox;

/**
 *
 * @author mac
 */

public class RetrieveSingleSelfCareBoxReq {
    private SelfCareBox selfCareBox;

    public RetrieveSingleSelfCareBoxReq(){
    }

    public RetrieveSingleSelfCareBoxReq(SelfCareBox selfCareBox){
        this.selfCareBox = selfCareBox;
    }

    public SelfCareBox getSelfCareBox(){
        return selfCareBox;
    }

    public void setSelfCareBox(SelfCareBox selfCareBox){
        this.selfCareBox = selfCareBox;
    }
}
