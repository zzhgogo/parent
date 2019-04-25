package com.zhuhao.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author:zhuhao
 * @Description:
 * @Date:
 **/
@Repository
public interface EsEntyRepository extends ElasticsearchRepository<EsEnty, String> {

}
