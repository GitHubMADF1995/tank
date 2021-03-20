package com.madf.test;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片加载测试类
 */
public class ImageTest {

    @Test
    void test() {
        try {
            BufferedImage image1 = ImageIO.read(new File("C:\\develop\\ideaprojects\\tank\\src\\images\\bulletD.gif"));
            assertNotNull(image1);

            BufferedImage image2 = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
            assertNotNull(image2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
