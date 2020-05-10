/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.SelfCareBox;
import java.util.List;

/**
 *
 * @author yiningxing
 */
public class RetrieveAllSelfCareBoxesRsp {
    private List<SelfCareBox> selfCareBoxes;
    
    public RetrieveAllSelfCareBoxesRsp() {
    }

    public RetrieveAllSelfCareBoxesRsp(List<SelfCareBox> selfCareBoxes) {
        this.selfCareBoxes = selfCareBoxes;
    }

    public List<SelfCareBox> getSelfCareBoxes() {
        return selfCareBoxes;
    }

    public void setSelfCareBoxes(List<SelfCareBox> selfCareBoxes) {
        this.selfCareBoxes = selfCareBoxes;
    }
}
