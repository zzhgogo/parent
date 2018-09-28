package com.manqian.mcollect;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.*;

public class MongoUtils {

    public static String host;

    public static int port;

    public static String username;

    public static String password;



    static {
        Properties prop = new Properties();
        try {
            prop.load(MongoUtils.class.getResourceAsStream("/config.properties"));
            host = prop.getProperty("mongo.host");
            port = Integer.valueOf(prop.getProperty("mongo.port"));
            username = prop.getProperty("mongo.username");
            password = prop.getProperty("mongo.password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Map<String, Object>> getNewSiteData() {

        MongoDatabase mongoDatabase = MongoUtils.getMongoDatabase();

        MongoCollection<Document> collection = mongoDatabase.getCollection("mq.mcollect.weChatArticle");

        Document queryParam = new Document("auditTime", new Document()
                .append("$gte", DateUtils.yesterdayStartTime())
                .append("$lte", DateUtils.yesterdayEndTime()));
        Document sortParam = new Document("auditTime", -1);

        FindIterable<Document> findIterable = collection.find(queryParam).sort(sortParam);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        List<Map<String, Object>> list = new ArrayList<>();
        while (mongoCursor.hasNext()) {
            Document document = mongoCursor.next();
            Map<String, Object> temp = new HashMap<>();
            temp.put("id", document.get("_id").toString());
            temp.put("date", DateUtils.formatDate(document.getDate("auditTime")));
            list.add(temp);
        }
        return list;
    }

    private static MongoDatabase mongoDatabase = null;

    public static MongoDatabase getMongoDatabase() {
        try {
            if(mongoDatabase!=null){
                return mongoDatabase;
            }
            ServerAddress serverAddress = new ServerAddress(host, port);
            MongoCredential credential = MongoCredential.createCredential(username, "mcollect", password.toCharArray());
            MongoClient client = new MongoClient(serverAddress);
            mongoDatabase = client.getDatabase("mcollect");
            return mongoDatabase;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
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


}
