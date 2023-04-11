package com.lifeifei.seleniumspider.ui.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Description:
 * 浏览本地图片
 * @date:2023/04/08 15:52
 * @author: lyf
 */
public class ImageReader extends JPanel {

    private BufferedImage image;

    public ImageReader(String path) {
        try {
            // 读取本地图片
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 在窗口中绘制图片
        if (image != null) {
            // 获取窗口大小和图片大小
            Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();

            // 计算图片缩放比例，确保图片不会超出窗口大小
            double scale = 1.0;
//            scale = Math.min(
//                    (double) windowSize.width / imageWidth,
//                    (double) windowSize.height / imageHeight
//            );

            // 计算图片在窗口中的位置
            int x = (int) ((windowSize.width - imageWidth * scale) / 2);
            int y = (int) ((windowSize.height - imageHeight * scale) / 2);

            // 在窗口中绘制图片
            g.drawImage(image, x, y, (int) (imageWidth * scale), (int) (imageHeight * scale), null);
        }
    }

    public static void open(String path) {
        JFrame frame = new JFrame("Image Reader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ImageReader(path));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();

        // 将窗口居中显示在屏幕上
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle bounds = frame.getBounds();
        frame.setLocation(
                (int) (screenSize.getWidth() - bounds.getWidth()) / 2,
                (int) (screenSize.getHeight() - bounds.getHeight()) / 2
        );

        frame.setVisible(true);

        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            return;
        }
        frame.dispose();
    }

}
