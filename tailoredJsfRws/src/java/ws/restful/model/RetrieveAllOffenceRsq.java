/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Offences;
import java.util.List;

/**
 *
 * @author yiningxing
 */
public class RetrieveAllOffenceRsq {
    private List<Offences> offences;

    public RetrieveAllOffenceRsq() {
    }

    public RetrieveAllOffenceRsq(List<Offences> offences) {
        this.offences = offences;
    }

    public List<Offences> getOffences() {
        return offences;
    }

    public void setOffences(List<Offences> offences) {
        this.offences = offences;
    }
    
    
}
