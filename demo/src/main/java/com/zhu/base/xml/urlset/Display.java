package com.zhu.base.xml.urlset;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author zhuhao
 * @title: Display
 * @projectName parent
 * @description: TODO
 * @date 2019/4/2810:23 AM
 */
@XStreamAlias("display")
public class Display {

    private String context;

    private String id;

    private String appid;

    private String title;

    private String images;

    private String pubDate;

    private String upDate;

    private String lrDate;

    private String description;

    private DisplayData data = new DisplayData();


    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getUpDate() {
        return upDate;
    }

    public void setUpDate(String upDate) {
        this.upDate = upDate;
    }

    public String getLrDate() {
        return lrDate;
    }

    public void setLrDate(String lrDate) {
        this.lrDate = lrDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DisplayData getData() {
        return data;
    }

    public void setData(DisplayData data) {
        this.data = data;
    }
}
