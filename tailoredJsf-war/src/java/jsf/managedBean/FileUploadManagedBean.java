/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author decimatum
 */
@ManagedBean
@SessionScoped
public class FileUploadManagedBean implements Serializable {
    private UploadedFile file;
    private String destination = System.getProperty("user.dir") + "/../docroot/";
    // Lecture 6 demo 3
    public UploadedFile getFile(){
        return file;
    }
    
    public void setFile(UploadedFile file){
        this.file = file;
    }
    
    public void fileUploadListener(FileUploadEvent event){
        System.out.println("Destination: " + destination + event.getFile().getFileName());
        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void copyFile(String fileName, InputStream in){
        try{
            OutputStream out = new FileOutputStream(new File(destination + fileName));
            
            int read = 0;
            byte[] bytes = new byte[1024];
            
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            
            in.close();
            out.flush();
            out.close();
            System.out.println("New file created!");
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
