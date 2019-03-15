package com.zhuhao.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author:zhuhao
 * @Description:
 * @Date:
 **/
@Document(indexName = "springboot",type = "esenty")
public class EsEnty implements Serializable {

    @Id
    private String id;

    private String content;

    private String title;

    public EsEnty(String id, String content, String title) {
        this.id = id;
        this.content = content;
        this.title = title;
    }

    private Date crateTime = new Date();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCrateTime() {
        return crateTime;
    }

    public void setCrateTime(Date crateTime) {
        this.crateTime = crateTime;
    }


    public EsEnty(String content, String title) {
        this.content = content;
        this.title = title;
    }
}
