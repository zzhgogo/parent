package com.zhu.base.xml.urlset;

/**
 * @author zhuhao
 * @title: Display
 * @projectName parent
 * @description: TODO
 * @date 2019/4/2810:23 AM
 */
public class DisplayData {

    private WebPage WebPage = new WebPage();

    private Author Author = new Author();

    private VideoObject VideoObject = new VideoObject();

    public com.zhu.base.xml.urlset.VideoObject getVideoObject() {
        return VideoObject;
    }

    public void setVideoObject(com.zhu.base.xml.urlset.VideoObject videoObject) {
        VideoObject = videoObject;
    }

    public WebPage getWebPage() {
        return WebPage;
    }

    public void setWebPage(WebPage webPage) {
        this.WebPage = webPage;
    }

    public Author getAuthor() {
        return Author;
    }

    public void setAuthor(Author author) {
        Author = author;
    }
}
