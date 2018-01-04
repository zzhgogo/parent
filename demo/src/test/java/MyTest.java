import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Author: as
 * Time:  2017/9/26 10:26
 * Description:
 */
public class MyTest {

    //初始化List
    List<String> list = new ArrayList<String>() {{
        add("远洋星集团");
        add("集团本部");
        add("远洋星电子商务宝安分公司");
        add("远洋星电子商务龙岗分公司");
        add("珠海销售部");
        add("宝安销售部");
        add("龙岗销售部");
        add("珠海远洋星公司");
    }};

    @Test
    public void test1() {
        String path = "C:/Users/as/Desktop/bh-线上机构.txt";
        String jsonArrStr = readFile(path);
        JSONArray jsonArray = JSON.parseArray(jsonArrStr);
        List<Map<String, Object>> resultsMaps = jsonArray.stream().map(m -> (JSONObject) m).filter(m -> list.contains(m.getString("name"))).map(m -> {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", m.getString("id"));
            map.put("name", m.getString("name"));
            return map;
        }).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(resultsMaps));

        String resultStr = resultsMaps.stream().map(m -> {
            String id = (String) m.get("id");
            String name = (String) m.get("name");
            return String.format("id=%s" + System.lineSeparator() + "name=%s" + System.lineSeparator() + System.lineSeparator(), id, name);
        }).collect(Collectors.joining(System.lineSeparator()));
        String path2 = "C:/Users/as/Desktop/bh.txt";
        writeFile(resultStr, path2);
    }

    public String readFile(String path) {
        String str = "";
        try {
            FileReader fileReader = new FileReader(path);
            List<String> stringList = IOUtils.readLines(fileReader);
            str = stringList.stream().collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception e) {

        }
        return str;
    }

    public void writeFile(String data, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            OutputStream os = new FileOutputStream(path);
            IOUtils.write(data, os);
        } catch (Exception e) {

        }
    }

    @Test
    public void t() {
        System.out.println(String.format("%02d时%02d分%02d秒", 1000, 100, 100));

        System.out.println("二".compareTo("三"));
    }

    @Test
    public void ct() {
        List<String> ll = list.subList(1, 3);
        System.out.println(JSONObject.toJSON(ll).toString());
    }

    @Test
    public void t3() {
        Integer a = 3141560;
        Integer b = 671272;
        Float c = Float.valueOf(b) / Float.valueOf(a) * 100;
        System.out.println(c);
    }

    @Test
    public void t4(){
        String str = "50|";
        Integer jobNum = Integer.valueOf(str.substring(0, str.indexOf("|")));
        System.out.println(jobNum);
    }
}