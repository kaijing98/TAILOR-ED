/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Post;
import java.util.List;

/**
 *
 * @author yiningxing
 */
public class RetrieveAllPostRsp {
    private List<Post> posts;

    public RetrieveAllPostRsp() {
    }

    public RetrieveAllPostRsp(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
