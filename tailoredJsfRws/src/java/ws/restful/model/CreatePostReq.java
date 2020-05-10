/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Customer;
import entity.Post;
import entity.Tag;
import java.util.List;

/**
 *
 * @author yiningxing
 */
public class CreatePostReq {
   private Long customerId;
   private Post post;
   private List<Long> tags;

    public CreatePostReq() {
    }

    public CreatePostReq(Long customerId, Post post, List<Long> tags) {
        this.customerId = customerId;
        this.post = post;
        this.tags = tags;
    }



    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getTags() {
        return tags;
    }

    public void setTags(List<Long> tags) {
        this.tags = tags;
    }


}
