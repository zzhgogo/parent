package com.manqian.mcollect;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


public class GenSite {

    public static long start_timestamp = 1529164800678l;

    public static String build_dir = "c:/sitemap";

    public static String site_file_prefix = "sitemap_";

    public static void main( String args[] ) throws Exception {
        //todo
        File baseDir = new File(build_dir);
        if(!baseDir.exists()){
            baseDir.mkdir();
        }

        List<Path> site_file_list = Files.list(Paths.get(build_dir))
                .filter(m->m.getFileName().toString().contains(site_file_prefix))
                .sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        Path last_site_file = site_file_list.get(0);
        System.out.println("last created file : " + last_site_file);


        List<Map<String, Object>> mapList = GenSite.getNewSiteData();

        XmlUtils.readXMLDemo(last_site_file, mapList);

        if(mapList.size() > 0 ){
            XmlUtils.builtXmlDocument(mapList, build_dir+"/sitemap_2018-06-19.xml");
        }


        //site_file_list.stream().forEach(System.out::println);

        //Files.list(Paths.get(build_dir)).map(m->m.getFileName()).forEach(System.out::println);






    }

    public static List<Map<String,Object>> getNewSiteData(){
        MongoDatabase mongoDatabase = GenSite.getMongoDatabase("127.0.0.1", 27017, "mcollect");
        MongoCollection<Document> collection = mongoDatabase.getCollection("mq.mcollect.weChatArticle");
        Document queryParam = new Document("auditTime", new Document()
                .append("$gt", geStartCurrentDay())
                .append("$lt", geEndCurrentDay()));
        Document sortParam = new Document("auditTime", 1);
        FindIterable<Document> findIterable = collection.find().sort(sortParam).limit(900);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        List<Map<String, Object>> list = new ArrayList<>();
        while(mongoCursor.hasNext()){
            Document document =  mongoCursor.next();
            Map<String, Object> temp = new HashMap<>();
            temp.put("id", document.getString("_id"));
            temp.put("date", formatDate(document.getDate("createTime")));
            list.add(temp);
        }
        return list;
    }

    public static MongoDatabase getMongoDatabase(String host, int port, String baseName){
        try{
            MongoClient mongoClient = new MongoClient( host , port);
            MongoDatabase mongoDatabase = mongoClient.getDatabase(baseName);
            return  mongoDatabase;
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
        }
        return null;
    }

    public static Date geStartCurrentDay(){
        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }


    public static Date geEndCurrentDay(){
        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        return calendar.getTime();
    }

    public static String formatDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);

    }

    public static Date getDateByTimestamp(long timestamp){
        Date date = new Date();
        date.setTime(timestamp);
        return date;
    }

    public static void buildXml(){

    }

}
