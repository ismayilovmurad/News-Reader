
package com.martiandeveloper.newsreader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Article {

    @SerializedName("title")
    @Expose
    private final String title;
    @SerializedName("url")
    @Expose
    private final String url;
    @SerializedName("urlToImage")
    @Expose
    private final String urlToImage;
    @SerializedName("publishedAt")
    @Expose
    private final String publishedAt;
    @SerializedName("content")
    @Expose
    private final String content;

    public Article(String title, String url, String urlToImage, String publishedAt, String content) {
        this.title = title;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }

}
