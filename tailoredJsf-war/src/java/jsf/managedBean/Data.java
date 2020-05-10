/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import util.enumeration.FormatEnum;

/**
 *
 * @author decimatum
 */
@Named(value = "data")
@ManagedBean
@ApplicationScoped
public class Data {
    
    public List<String> getFormats(){
        List<String> friendlyFormatEnums = new ArrayList();
        for (FormatEnum currFormat : FormatEnum.values()) {
            if(currFormat.equals(FormatEnum.ARTPOSTER)){
                friendlyFormatEnums.add("Art Poster");
            } else if(currFormat.equals(FormatEnum.CANVASWRAP)){
                friendlyFormatEnums.add("Canvas Wrap");
            } else {
                friendlyFormatEnums.add("Photo Print");
            }
        }
        return friendlyFormatEnums;
    }
}
