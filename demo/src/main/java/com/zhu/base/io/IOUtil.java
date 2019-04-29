package com.zhu.base.io;

import org.junit.Test;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuhao
 * @title: IOUtil
 * @projectName parent
 * @description: TODO
 * @date 2019/4/284:40 PM
 */
public class IOUtil {

    private static final Charset defaultCharset = Charset.forName("utf-8");

    /**
     * 读取文件中的字符串
     *
     * @param path
     * @return
     */
    public static String readText(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            char[] buffer = new char[512];
            int len = 0;
            StringBuffer stringBuffer = new StringBuffer();
            while ((len = fileReader.read(buffer)) != -1) {
                String str = new String(buffer, 0, len);
                stringBuffer.append(str);
            }
            fileReader.close();
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void write(byte[] data, OutputStream output)
            throws IOException {
        if (data != null) {
            output.write(data);
        }
    }

    public static void write(String text, OutputStream output)
            throws IOException {
        byte[] bytes = text.getBytes(defaultCharset);
        write(bytes, output);
    }

    public static void write(String text, Writer output) throws IOException {
        output.write(text);
    }

    public static void write(byte[] bytes, Writer output) throws IOException {
        output.write(new String(bytes, defaultCharset));
    }


    public static List<String> readLines(Reader input) throws IOException {
        BufferedReader reader = toBufferedReader(input);
        List<String> list = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            list.add(line);
            line = reader.readLine();
        }
        return list;
    }

    public static List<String> readLines(InputStream input) throws IOException {
        InputStreamReader reader = new InputStreamReader(input, defaultCharset);
        return readLines(reader);
    }

    public static BufferedReader toBufferedReader(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }


    @Test
    public void t1() throws Exception {
        FileInputStream reader = new FileInputStream("/data/c_url_video.xml");
        BufferedInputStream bfi = new BufferedInputStream(reader);
        byte[] buffer = new byte[256];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = bfi.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        byte[] data = bos.toByteArray();
        reader.close();
        bfi.close();
        bos.close();
        String res = new String(data, Charset.forName("utf-8"));
        System.out.println(res);
    }

    @Test
    public void t2() throws Exception {
        FileInputStream fis = new FileInputStream("/data/c_url_video.xml");
        InputStreamReader isr = new InputStreamReader(fis);
        StringBuffer sb = new StringBuffer();
        char[] buf = new char[256];
        int len = 0;
        while ((len = isr.read(buf)) != -1) {
            sb.append(buf, 0, len);
        }
        isr.close();
        fis.close();
        System.out.println(sb);
    }
}
