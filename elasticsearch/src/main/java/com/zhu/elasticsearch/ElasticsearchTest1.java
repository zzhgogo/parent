package com.zhu.elasticsearch;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Elasticsearch的基本测试
 *
 * @author sunt
 * @version V1.0
 * @ClassName: ElasticsearchTest1
 * @date 2017年11月22日
 */
public class ElasticsearchTest1 {

    public final static String HOST = "59.110.138.29";
    public final static int PORT = 9200;//http请求的端口是9200，客户端是9300
    private Logger logger = LoggerFactory.getLogger(ElasticsearchTest1.class);

    /**
     * 测试Elasticsearch客户端连接
     *
     * @return void
     * @throws UnknownHostException
     * @Title: test1
     * @author sunt
     * @date 2017年11月22日
     */
    @Test
    public void test1() throws UnknownHostException {
        //创建客户端
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName(HOST), PORT));

        logger.info("Elasticsearch connect info:" + client.toString());
        System.out.println(client.toString());
        //关闭客户端
        client.close();
    }
}