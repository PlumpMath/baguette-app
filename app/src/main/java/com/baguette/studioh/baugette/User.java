package com.baguette.studioh.baugette;

import java.util.ArrayList;
import java.lang.*;

/**
 * Created by studioh on 8/20/16.
 */
public class User
{
    private String _id;

    public String getId() { return this._id; }

    public void setId(String _id) { this._id = _id; }

    private String quote;

    public String getQuote() { return this.quote; }

    public void setQuote(String quote) { this.quote = quote; }

    private String screenName;

    public String getScreenName() { return this.screenName; }

    public void setScreenName(String screenName) { this.screenName = screenName; }

    private String userIDString;

    public String getUserIDString() { return this.userIDString; }

    public void setUserIDString(String userIDString) { this.userIDString = userIDString; }

    private ArrayList<Object> posts;

    public ArrayList<Object> getPosts() { return this.posts; }

    public void setPosts(ArrayList<Object> posts) { this.posts = posts; }

    private ArrayList<Object> followers;

    public ArrayList<Object> getFollowers() { return this.followers; }

    public void setFollowers(ArrayList<Object> followers) { this.followers = followers; }

    private ArrayList<Object> following;

    public ArrayList<Object> getFollowing() { return this.following; }

    public void setFollowing(ArrayList<Object> following) { this.following = following; }
}