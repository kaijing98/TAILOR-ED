/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Post;

/**
 *
 * @author yiningxing
 */
public class RetrievePostByIdRsp {
    private Post post;

    public RetrievePostByIdRsp() {
    }

    
    public RetrievePostByIdRsp(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }  
}
