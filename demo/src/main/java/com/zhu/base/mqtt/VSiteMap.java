package com.zhu.base.mqtt;

import com.zhu.base.http.HttpTool;
import com.zhu.base.io.IOUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.FileReader;


/**
 * @author zhuhao
 * @title: VSiteMap
 * @projectName parent
 * @description: TODO
 * @date 2019/4/284:38 PM
 */
public class VSiteMap {

    @Test
    public void t1() throws Exception {
        String url = "http://data.zz.baidu.com/ping?appid=1601784359111516&token=ZqgJ2toTRLmxKsi5&type=video&format=xml";
        String xml = IOUtil.readText("/data/c_url_video.xml");
        String res = HttpTool.getConent(url, xml);

        FileReader fileReader = new FileReader("/data/c_url_video.xml");
        xml = IOUtils.toString(fileReader);
        System.out.println(res);
    }
}
