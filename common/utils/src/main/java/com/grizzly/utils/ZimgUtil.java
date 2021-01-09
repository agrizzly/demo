package com.grizzly.utils;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.Iterator;

/**
 * @author DurantSimpson
 * @desc Zimg上传与删除图片工具类
 * @create 2018-06-07 15:34
 **/
public class ZimgUtil {

    //默认为外网地址http://61.153.61.50:14869/,内网地址为http://192.168.80.194:4869/
    private static String ZIMGURL = "http://61.153.61.50:14869/";

    private static File tmpFile = null;

    public static void main(String[] args) {
        //System.out.println(upload(new File("D:\\1.jpg")));
        // System.out.println(remove("http://192.168.80.194:4869/c8705ebd570551b7329b269a472385e0"));
        upload("");
    }

    public static void setZIMGURL(String ZIMGURL) {
        ZimgUtil.ZIMGURL = ZIMGURL;
    }

    /**
     * 上传图片
     * @param base64 图片编码
     * @return 返回图片的链接地址
     */
    public static String upload(String base64) {
        if (base64 == null){
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            // System.out.println(base64);
            byte[] bytes = null;
            if (base64.startsWith("data:image/jpeg;base64,")){
                bytes = decoder.decodeBuffer(base64.replace("data:image/jpeg;base64,", ""));
                tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "1.jpg");
            }
            if (base64.startsWith("data:image/png;base64,")){
                bytes = decoder.decodeBuffer(base64.replace("data:image/png;base64,", ""));
                tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "1.png");
            }
            /*for(int i=0;i<bytes.length;++i){
                if(bytes[i]<0){//调整异常数据
                     bytes[i]+=256;
                }
            }*/

            fos = new FileOutputStream(tmpFile);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
            return upload(tmpFile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (tmpFile!=null){
                tmpFile.delete();
            }
        }
        return null;
    }

    /**
     * 上传图片
     * @param inputStream
     * @return
     */
    public static String upload(InputStream inputStream){
        OutputStream outputStream = null;
        try {
            tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "1.png");
            if (!tmpFile.exists()){
                tmpFile.createNewFile();
            }
            outputStream = new FileOutputStream(tmpFile);
            int len;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
            return upload(tmpFile);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (tmpFile!=null){
                tmpFile.delete();
            }
        }
        return null;
    }

    /**
     * 上传图片
     * @param file 图片文件
     * @return 返回图片的链接地址
     */
    public static String upload(MultipartFile file){
        try {
            return upload(multipartToFile(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (tmpFile!=null){
                tmpFile.delete();
            }
        }
        return null;
    }

    /**
     * 上传图片
     * @param file 图片文件
     * @return 返回图片的链接地址
     */
    public static String upload(File file){
        HttpRequest httpRequest = HttpRequest
                .post(ZIMGURL+"upload")
                .form("file",file);
        HttpResponse httpResponse = httpRequest.execute();
        return readStringXml(httpResponse.body(),ZIMGURL);
    }

    /**
     * 删除图片
     * @param url 图片的链接地址
     * @return 返回删除信息
     */
    public static String remove(String url){
        HttpRequest httpRequest = HttpRequest
                .get(ZIMGURL+"admin")
                .form("md5",url.substring(ZIMGURL.length()),"t",1);
        HttpResponse httpResponse = httpRequest.execute();
        return readStringXml(httpResponse.body());
    }

    private static String readStringXml(String xml, String url) {
        return url + readStringXml(xml).substring(readStringXml(xml).indexOf(":") + 1).trim();
    }

    private static String readStringXml(String xml) {
        Document doc = null;
        try {
            xml = xml.replace("&", "&amp;");
            // 下面的是通过解析xml字符串的
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            Iterator iterator = rootElt.elementIterator("body"); ///获取根节点下的子节点body
            // 遍历body节点
            while (iterator.hasNext()) {
                Element recordEless = (Element) iterator.next();
                return recordEless.elementTextTrim("h1"); // 拿到body节点下的子节点h1加密值
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("xml字符串解析错误，xml：" + xml);
            System.out.println("出错原因：" + e.getMessage());
        }
        return null;
    }

    /**
     * MultipartFile 转换成File
     *
     * @param multfile 原文件类型
     * @return File
     * @throws IOException
     */
    private static File multipartToFile(MultipartFile multfile) throws IOException {
        tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + multfile.getOriginalFilename());
        multfile.transferTo(tmpFile);
        return tmpFile;
    }
}
