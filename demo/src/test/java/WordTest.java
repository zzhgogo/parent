import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class WordTest {

    @Test
    public void test1() throws IOException {
        String path1 = "/Users/zhuhao/Documents/words_cjsctock2.txt";
        String path2 = "/Users/zhuhao/Documents/words_dev.txt";
        List<String> wordsList1 = FileUtils.readLines(new File(path1));
        List<String> wordsList2 = FileUtils.readLines(new File(path2));
        wordsList1.addAll(wordsList2);
        Set<String> wordSet = new HashSet<>(wordsList1);
        List<String> wordsList = new ArrayList<>(wordSet);
        Collections.shuffle(wordsList);
        wordsList1.stream().forEach(System.out::println);
        System.out.println(wordsList.size());
        IOUtils.writeLines(wordsList.subList(0, 30000), IOUtils.LINE_SEPARATOR,
                new FileOutputStream("/data/word/words_1.txt"));
        IOUtils.writeLines(wordsList.subList(30000, 60000), IOUtils.LINE_SEPARATOR,
                new FileOutputStream("/data/word/words_2.txt"));
        IOUtils.writeLines(wordsList.subList(60000, 90000), IOUtils.LINE_SEPARATOR,
                new FileOutputStream("/data/word/words_3.txt"));
        IOUtils.writeLines(wordsList.subList(90000, wordsList.size()-1), IOUtils.LINE_SEPARATOR,
                new FileOutputStream("/data/word/words_4.txt"));
    }

    @Test
    public void test2() throws Exception{
        Path basePath = Paths.get("/data/sitemap");
        Files.list(basePath).filter(m -> m.toString().contains("msitemap_")).forEach(this::cut);
    }

    public void cut(Path path) {
        try {
            Path fileName = path.getFileName();
            String dateStr = fileName.toString().substring(9, 19);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = format.parse(dateStr);
            date1.setTime(date1.getTime()-24*60*60);
            String dateStr2 = format.format(date1);

            List<String> oldList = Files.readAllLines(path);
            oldList.remove(0);
            oldList.remove(oldList.size()-1);

            List<String> newList1 = new ArrayList<>();
            newList1.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?><urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");
            newList1.addAll(oldList.subList(0, oldList.size()/2));
            newList1.add("</urlset>");
            List<String> newList2 = new ArrayList<>();
            newList2.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?><urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");
            newList2.addAll(oldList.subList(oldList.size()/2, oldList.size()-1));
            newList2.add("</urlset>");

            IOUtils.writeLines(newList1, IOUtils.LINE_SEPARATOR, new FileOutputStream("/data/m/msitemap_"+dateStr+".xml"));
            IOUtils.writeLines(newList2, IOUtils.LINE_SEPARATOR, new FileOutputStream("/data/m/msitemap_"+dateStr2+".xml"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
