package com.zhu.base.xml.urlset;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author zhuhao
 * @title: Url
 * @projectName parent
 * @description: TODO
 * @date 2019/4/289:47 AM
 */
@XStreamAlias("url")
public class Url {

    private String loc;

    private String lastmod;

    private String changefreq;

    private String priority;

    private UrlData data = new UrlData();



    public Url() {
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getLastmod() {
        return lastmod;
    }

    public void setLastmod(String lastmod) {
        this.lastmod = lastmod;
    }

    public String getChangefreq() {
        return changefreq;
    }

    public void setChangefreq(String changefreq) {
        this.changefreq = changefreq;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public UrlData getData() {
        return data;
    }

    public void setData(UrlData data) {
        this.data = data;
    }
}
