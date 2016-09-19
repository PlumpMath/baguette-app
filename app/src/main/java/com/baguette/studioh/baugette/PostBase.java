package com.baguette.studioh.baugette;

import java.util.ArrayList;

/**
 * Created by studioh on 8/21/16.
 */

public class PostBase {
    private int result;

    public int getResult() { return this.result; }

    public void setResult(int result) { this.result = result; }

    private String postid;

    public String getpostid() { return this.postid; }

    public void setpostid(String postid) { this.postid = postid; }

    private ArrayList<Post>posts;

    public ArrayList<Post> getPosts() { return this.posts; }

    public void setPosts(ArrayList<Post> posts) { this.posts = posts; }

    private Post post;

    public Post getPost() { return this.post; }

    public void setPost(Post post) { this.post = post; }
}