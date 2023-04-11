package com.lifeifei.seleniumspider.ui.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Description:
 * 文件操作类
 * @date:2023/04/08 15:22
 * @author: lyf
 */
public class FileUtil {

    /**
     * 保存截图文件
     * @param screen
     * @param path
     */
    public static void savePic(File screen, String path) {
        try {
            System.out.println("save screenshot");
            FileUtils.copyFile(screen, new File(path));
        } catch (IOException e) {
            System.out.println("save screenshot fail");
            e.printStackTrace();
        } finally {
            System.out.println("save screenshot finish");
        }
    }

    /**
     * 打开图片文件
     */
    public static void openPic(String path) throws Exception {
        try {
            ImageReader.open(path);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("图片读取失败");
        }
    }

//    public static void main(String[] args) throws Exception {
//        try {
//            FileUtil fileUtil = new FileUtil();
//            fileUtil.openPic("/Users/liyanfei/MyCode/Purchase/Purchase/image/taobao/QR.PNG");
//        } catch (IOException e) {
//            throw new Exception("ss");
//        }
//    }
}
