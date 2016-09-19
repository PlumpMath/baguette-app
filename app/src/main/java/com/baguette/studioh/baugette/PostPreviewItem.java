package com.baguette.studioh.baugette;


import android.graphics.Bitmap;

/**
 * Created by studioh on 8/21/16.
 */
public class PostPreviewItem {
    private String postImage1;
    private String postImage2;
    private String profileImage;
    private String authorStr;
    private String titleStr;
    private String lightsStr;
    private String commentsStr;
    private String additionalTextStr;

    public void setProfileImage(String Image)
    {
        profileImage = Image;
    }
    public void setPostImage1(String Image)
    {
        postImage1 = Image;
    }
    public void setPostImage2(String Image)
    {
        postImage2 = Image;
    }
    public void setAuthor(String Author)
    {
        authorStr = Author;
    }
    public void setTitle(String Title)
    {
        titleStr = Title;
    }
    public void setLights(String Lights)
    {
        lightsStr = Lights;
    }
    public void setComments(String Comments)
    {
        commentsStr = Comments;
    }
    public void setAddtionalText(String Text) { additionalTextStr = Text; }

    public String getProfileImage()
    {
        return profileImage;
    }
    public String getPostImage1()
    {
        return postImage1;
    }
    public String getPostImage2()
    {
        return postImage2;
    }
    public String getTitle()
    {
        return titleStr;
    }
    public String getAuthor()
    {
        return authorStr;
    }
    public String getLights() {return lightsStr;}
    public String getComments() {return commentsStr;}
    public String getAdditionalText() {return additionalTextStr;}
}
