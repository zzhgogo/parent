package com.zhu.base.elasticsearch;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.apache.commons.lang.time.DateUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Elasticsearch的基本测试
 *
 * @author sunt
 * @version V1.0
 * @ClassName: ElasticsearchDemo
 * @date 2017年11月22日
 */
public class ElasticsearchDemo {

    public final static String HOST = "127.0.0.1";

    public final static int PORT = 9300;//http请求的端口是9200，客户端是9300

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    TransportClient client = null;

    /**
     * 测试Elasticsearch客户端连接
     *
     * @return void
     * @throws UnknownHostException
     * @Title: test1
     * @author sunt
     * @date 2017年11月22日
     */
    @Before
    public void t1() throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "manqian-search").build();
        //创建客户端
        client = new PreBuiltTransportClient(Settings.builder().build())
                .addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName(HOST), PORT));
        System.out.println("连接信息:" + client.toString());
    }


    /**
     * 创建索引库
     * @Title: addIndex1
     * @author sunt
     * @date 2017年11月23日
     * @return void
     * 需求:创建一个索引库为：msg消息队列,类型为：tweet,id为1
     * 索引库的名称必须为小写
     * @throws IOException
     */
    @Test
    public void addIndex1() throws IOException {
        IndexResponse response = client.prepareIndex("msg", "tweet").setSource(XContentFactory.jsonBuilder()
                .startObject().field("userName", "张三")
                .field("sendDate", new Date())
                .field("msg", "你好李四")
                .endObject()).get();

        System.out.println("索引名称:" + response.getIndex() + "\n类型:" + response.getType()
                + "\n文档ID:" + response.getId() + "\n当前实例状态:" + response.status());
    }

    /**
     * 添加索引:传入json字符串
     * @Title: addIndex2
     * @author sunt
     * @date 2017年11月23日
     * @return void
     */
    @Test
    public void addIndex2() {
        String jsonStr = "{" +
                "\"userName\":\"张三\"," +
                "\"sendDate\":\"2017-11-30\"," +
                "\"msg\":\"你好李四\"" +
                "}";
        IndexResponse response = client.prepareIndex("weixin", "tweet").setSource(jsonStr, XContentType.JSON).get();
        System.out.println("json索引名称:" + response.getIndex() + "\njson类型:" + response.getType()
                + "\njson文档ID:" + response.getId() + "\n当前实例json状态:" + response.status());

    }

    /**
     * 创建索引-传入Map对象
     * @Title: addIndex3
     * @author sunt
     * @date 2017年11月23日
     * @return void
     */
    @Test
    public void addIndex3() {
        for (int i = 0 ; i < 2000; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userName", "张三");
            map.put("sendDate", new Date());
            map.put("msg", "你好李四");
            IndexResponse response = client.prepareIndex("momo", "tweet").setSource(map).get();

            System.out.println("map索引名称:" + response.getIndex() + "\nmap类型:" + response.getType()
                    + "\nmap文档ID:" + response.getId() + "\n当前实例map状态:" + response.status());
        }
    }

    /**
     * 传递json对象
     * @Title: addIndex4
     * @author sunt
     * @date 2017年11月23日
     * @return void
     */
    @Test
    public void addIndex4() {
        client.settings();
        for (int i = 0 ; i < 2000; i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName", "user_"+i);
            jsonObject.put("sendDate", dateFormat.format(new Date()));
            jsonObject.put("msg", "你好李四_"+i);
            IndexResponse response = client.prepareIndex("ss", "tweet").setSource(jsonObject, XContentType.JSON).get();
        }
//        System.out.println("jsonObject索引名称:" + response.getIndex() + "\njsonObject类型:" + response.getType()
//                + "\njsonObject文档ID:" + response.getId() + "\n当前实例jsonObject状态:" + response.status());
    }

    /**
     * 从索引库获取数据
     * @Title: getData1
     * @author sunt
     * @date 2017年11月23日
     * @return void
     */
    @Test
    public void getData1() {
        GetResponse getResponse = client.prepareGet("msg", "tweet", "1").get();
        System.out.println("索引库的数据:" + getResponse.getSourceAsString());
    }

    /**
     * 更新索引库数据
     * @Title: updateData
     * @author sunt
     * @date 2017年11月23日
     * @return void
     */
    @Test
    public void updateData() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName", "王五");
        jsonObject.addProperty("sendDate", "2008-08-08");
        jsonObject.addProperty("msg","你好,张三，好久不见");
        UpdateResponse updateResponse = client.prepareUpdate("msg", "tweet", "1").setDoc(jsonObject.toString(),XContentType.JSON).get();
        System.out.println("updateResponse索引名称:" + updateResponse.getIndex() + "\nupdateResponse类型:" + updateResponse.getType()
                + "\nupdateResponse文档ID:" + updateResponse.getId() + "\n当前实例updateResponse状态:" + updateResponse.status());
    }

    /**
     * 根据索引名称，类别，文档ID 删除索引库的数据
     * @Title: deleteData
     * @author sunt
     * @date 2017年11月23日
     * @return void
     */
    @Test
    public void deleteData() {
        DeleteResponse deleteResponse = client.prepareDelete("msg", "tweet", "1").get();
        System.out.println("deleteResponse索引名称:" + deleteResponse.getIndex() + "\ndeleteResponse类型:" + deleteResponse.getType()
                + "\ndeleteResponse文档ID:" + deleteResponse.getId() + "\n当前实例deleteResponse状态:" + deleteResponse.status());
    }


    @Test
    public void search1(){
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("ss").setTypes("tweet")
                .addSort("_score", SortOrder.DESC);
        QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery("你好李四");
        searchRequestBuilder.setQuery(queryBuilder);
        SearchResponse response = searchRequestBuilder.execute().actionGet();

        SearchHits searchHits = response.getHits(); //获取返回
        for (SearchHit hit: searchHits.getHits()){
            System.out.println(hit.getSourceAsString());
        }
    }



    @After
    public void t100(){
        if(client!=null){
            client.close();
            System.out.println("关闭:" + client.toString());
        }


    }
}