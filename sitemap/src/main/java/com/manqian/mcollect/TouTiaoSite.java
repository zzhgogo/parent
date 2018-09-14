package com.manqian.mcollect;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
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


public class TouTiaoSite {

    public static String host;

    public static int port;

    public static String username;

    public static String password;

    public static String pc_build_dir;

    public static String h5_build_dir;

    public static String client = "pc";


    static {
        Properties prop = new Properties();
        try {
            prop.load(TouTiaoSite.class.getResourceAsStream("/config.properties"));
            host = prop.getProperty("mongo.host");
            port = Integer.valueOf(prop.getProperty("mongo.port"));
            username = prop.getProperty("mongo.username");
            password = prop.getProperty("mongo.password");
            pc_build_dir = prop.getProperty("pc_build_dir");
            h5_build_dir = prop.getProperty("h5_build_dir");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main( String args[] ) throws Exception{
         createPc();
         createH5();
    }

    public static void createPc() throws Exception {
        File baseDir = new File(pc_build_dir);
        if (!baseDir.exists()) {
            baseDir.mkdir();
        }
        List<Path> site_file_list = Files.list(Paths.get(pc_build_dir)).filter(m -> m.getFileName().toString().
                startsWith("sitemap_20")).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        Path last_site_file = Paths.get(pc_build_dir + "/sitemap_"+DateUtils.getCurrentDateStr1()+".xml");
        if (site_file_list.size() > 0) {
            last_site_file = site_file_list.get(0);
        }
        List<Map<String, Object>> mapList = MongoUtils.getNewSiteData();
        XmlUtils.createSiteMapFile(last_site_file, mapList);
        int size = mapList.size();
        while (size > 0){
            last_site_file = Paths.get(pc_build_dir + "/sitemap_"+DateUtils.getCurrentDateStr1()+".xml");
            XmlUtils.createSiteMapFile(last_site_file, mapList);
            size = mapList.size();
        }
    }

    public static void createH5() throws Exception {
        client = "h5";
        File baseDir = new File(h5_build_dir);
        if (!baseDir.exists()) {
            baseDir.mkdir();
        }
        List<Path> site_file_list = Files.list(Paths.get(h5_build_dir)).filter(m -> m.getFileName().toString().
                startsWith("msitemap_20")).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        Path last_site_file = Paths.get(h5_build_dir + "/msitemap_"+DateUtils.getCurrentDateStr1()+".xml");
        if (site_file_list.size() > 0) {
            last_site_file = site_file_list.get(0);
        }
        List<Map<String, Object>> mapList = MongoUtils.getNewSiteData();
        XmlUtils.createSiteMapFile(last_site_file, mapList);
        int size = mapList.size();
        while (size > 0){
            last_site_file = Paths.get(pc_build_dir + "/msitemap_"+DateUtils.getCurrentDateStr1()+".xml");
            XmlUtils.createSiteMapFile(last_site_file, mapList);
            size = mapList.size();
        }
    }
}
