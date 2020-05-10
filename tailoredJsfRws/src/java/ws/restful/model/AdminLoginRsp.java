/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Admin;

/**
 *
 * @author yiningxing
 */
public class AdminLoginRsp {
    private Admin admin;

    public AdminLoginRsp() {
    }

    public AdminLoginRsp(Admin admin) {
        this.admin = admin;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }   
}
