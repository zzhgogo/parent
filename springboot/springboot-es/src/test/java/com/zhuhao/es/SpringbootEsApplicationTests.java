package com.zhuhao.es;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootEsApplicationTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private EsEntyRepository esEntyRepository;

    @Test
    public void create() {
        elasticsearchTemplate.createIndex(EsEnty.class);
    }

    @Test
    public void save() {
        EsEnty esEnty = new EsEnty(System.currentTimeMillis() + "", "zhu1", "zhuhao");
        esEntyRepository.save(esEnty);
    }

}
