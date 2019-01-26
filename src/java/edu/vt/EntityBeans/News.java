
/*
 * Created by Bengi Sevil on 2018.11.28  * 
 * Copyright © 2018 Bengi Sevil. All rights reserved. * 
 */
package edu.vt.EntityBeans;

/**
 *
 * @author Sevil
 */
public class News {

    private String title;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public News() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
    
     
}
