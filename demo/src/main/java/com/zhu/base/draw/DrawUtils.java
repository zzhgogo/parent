package com.zhu.base.draw;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DrawUtils {

    /**
     * 字体文件路径
     */
    private static final String DEFAULT_FONT = "E:\\shuiyin//simhei.ttf";

    public static BufferedImage draw0(String templateImg, String headImg, String erCodeImg, String nickName) throws Exception {
        BufferedImage templateImage = ImageIO.read(Files.newInputStream(Paths.get(templateImg)));
        BufferedImage headImage = ImageIO.read(Files.newInputStream(Paths.get(headImg)));
        BufferedImage erCodeImage = ImageIO.read(Files.newInputStream(Paths.get(erCodeImg)));


        //绘制圆形图像
        Graphics2D graphics2D = templateImage.createGraphics();
        Ellipse2D.Double shape = new Ellipse2D.Double(84, 50, 100, 100);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setClip(shape);
        graphics2D.drawImage(resize(100, 100, headImage), 84, 50, 100, 100, null);
        graphics2D.dispose();

        //绘制昵称
        graphics2D = templateImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font =loadFont(DEFAULT_FONT, 42);
        graphics2D.setFont(font);
        graphics2D.setColor(Color.white);
        graphics2D.drawString(nickName, 200, 95);
        graphics2D.dispose();


        //绘制二维码
        graphics2D = templateImage.createGraphics();
        graphics2D.drawImage(resize(240, 240, erCodeImage), 730, 1590, null);
        graphics2D.dispose();

        return templateImage;

    }


    /**
     * 图片剪裁
     *
     * @param targetWidth
     * @param targetHeight
     * @param src
     * @return
     */
    public static BufferedImage resize(int targetWidth, int targetHeight, BufferedImage src) {
        double scaleW = (double) targetWidth / (double) src.getWidth();
        double scaleH = (double) targetHeight / (double) src.getHeight();
        double scale = scaleW < scaleH ? scaleW : scaleH;
        BufferedImage result = new BufferedImage((int) (src.getWidth() * scale), (int) (src.getHeight() * scale), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = result.createGraphics();
        g2d.drawImage(src, 0, 0, result.getWidth(), result.getHeight(), null);
        g2d.dispose();
        return result;
    }

    /**
     * 加载字体文件（。ttf）
     *
     * @param fontFileName
     * @param fontSize
     * @return
     */
    public static Font loadFont(String fontFileName, float fontSize){
        try{

            InputStream aixing = Files.newInputStream(Paths.get(fontFileName));
            Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, aixing);
            Font dynamicFontPt = dynamicFont.deriveFont(fontSize);
            aixing.close();
            return dynamicFontPt;
        }
        catch (Exception e)// 异常处理
        {
            e.printStackTrace();
            return new Font("宋体", Font.BOLD, 200);
        }
    }

    public static void main(String[] args) throws Exception {

        String templateImg = "E:\\shuiyin//template.jpg";

        String erCodeImg = "E:\\shuiyin//ercode.jpg";

        String outputFile = "E:\\shuiyin//test.jpg";



        BufferedImage bufferedImage = DrawUtils.draw0(templateImg, erCodeImg, erCodeImg, "朱昊");

        File sf = new File(outputFile);
        ImageIO.write(bufferedImage, "jpg", sf); // 保存图片
        System.out.println("添加图片水印成功");

    }
}
