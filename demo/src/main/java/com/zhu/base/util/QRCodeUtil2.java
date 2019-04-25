package com.zhu.base.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * 二维码编码&解码
 *
 * @Author: ChenGuiYong 2015年7月13日 上午11:09:56
 * @Version: $Id$
 * @Desc: <p></p>
 */
public class QRCodeUtil2 {

    private static final int BLACK = 0xFF000000;

    private static final int WHITE = 0xFFFFFFFF;

    public static void createErCode(String website, OutputStream output) throws WriterException, IOException {
        int width = 300;
        int height = 300;
        String format = "jpg";
        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bm = new MultiFormatWriter().encode(website, BarcodeFormat.QR_CODE, width, height, hints);

        BufferedImage image = toImage(bm);
        ImageIO.write(image, format, output);   //把二维码写到response的输出流



    }

    private static BufferedImage toImage(BitMatrix bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bm.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static void main(String[] args) throws IOException, WriterException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        QRCodeUtil2.createErCode("朱昊", byteArrayOutputStream);
        IOUtils.write(byteArrayOutputStream.toByteArray(), new FileOutputStream("/data/er.jpg"));
    }

}