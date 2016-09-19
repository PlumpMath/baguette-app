package com.baguette.studioh.baugette;

/**
 * Created by studioh on 8/21/16.
 */
public class Comment
{
    private Object uploadTime;

    public Object getUploadTime() { return this.uploadTime; }

    public void setUploadTime(Object uploadTime) { this.uploadTime = uploadTime; }

    private String content;

    public String getContent() { return this.content; }

    public void setContent(String content) { this.content = content; }

    private String uploader;

    public String getUploader() { return this.uploader; }

    public void setUploader(String uploader) { this.uploader = uploader; }

    private String uploaderID;

    public String getUploaderID() { return this.uploaderID; }

    public void setUploaderID(String uploaderID) { this.uploaderID = uploaderID; }

    private String uploaderUsername;

    public String getUploaderUsername() { return this.uploaderUsername; }

    public void setUploaderUsername(String uploaderUsername) { this.uploaderUsername = uploaderUsername; }
}
