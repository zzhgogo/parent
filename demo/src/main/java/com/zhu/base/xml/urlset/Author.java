package com.zhu.base.xml.urlset;

/**
 * @author zhuhao
 * @title: WebPage
 * @projectName parent
 * @description: TODO
 * @date 2019/4/2811:05 AM
 */
public class Author {

    private String name = "";

    private String jobTitle = "";

    private String headPortrait = "";

    private String pcUrl = "";

    private String wapUrl = "";

    private String fansCount = "0";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getPcUrl() {
        return pcUrl;
    }

    public void setPcUrl(String pcUrl) {
        this.pcUrl = pcUrl;
    }

    public String getWapUrl() {
        return wapUrl;
    }

    public void setWapUrl(String wapUrl) {
        this.wapUrl = wapUrl;
    }

    public String getFansCount() {
        return fansCount;
    }

    public void setFansCount(String fansCount) {
        this.fansCount = fansCount;
    }
}
