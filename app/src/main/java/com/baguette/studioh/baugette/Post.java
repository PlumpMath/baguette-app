package com.baguette.studioh.baugette;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by studioh on 8/21/16.
 */
public class Post
{
    private String _id;

    public String getId() { return this._id; }

    public void setId(String _id) { this._id = _id; }

    private String title;

    public String getTitle() {return this.title; }

    public void setTitle(String title) { this.title = title; }

    private String explanation;

    public String getExplanation() {return this.explanation; }

    public void setExplanation(String explanation) { this.explanation = explanation; }

    private String uploaderName;

    public String getUploaderName() { return this.uploaderName; }

    public void setUploaderName(String uploaderName) { this.uploaderName = uploaderName; }

    private String uploader;

    public String getUploader() { return this.uploader; }

    public void setUploader(String uploader) { this.uploader = uploader; }

    private int __v;

    public int getV() { return this.__v; }

    public void setV(int __v) { this.__v = __v; }

    private ArrayList<Comment> comments;

    public ArrayList<Comment> getComments() { return this.comments; }

    public void setComments(ArrayList<Comment> comments) { this.comments = comments; }

    private ArrayList<Object> likes;

    public ArrayList<Object> getLikes() { return this.likes; }

    public void setLikes(ArrayList<Object> likes) { this.likes = likes; }

    private Date uploadTime;

    public Date getUploadTime() { return this.uploadTime; }

    public void setUploadTime(Date uploadTime) { this.uploadTime = uploadTime; }
}
