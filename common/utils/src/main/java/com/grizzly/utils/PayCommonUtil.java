package com.grizzly.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;


public class PayCommonUtil {



    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public static Map doXMLParse(String strxml)  throws Exception{
        Map<String, String> map = new HashMap<>();
        if (null == strxml || "".equals(strxml)) {
            return null;
        }
        InputStream in = String2Inputstream(strxml);
        SAXReader read = new SAXReader();
        Document doc = read.read(in);
//得到xml根元素
        Element root = doc.getRootElement();
//遍历  得到根元素的所有子节点
        @SuppressWarnings("unchecked")
        List<Element> list = root.elements();
        for (Element element : list) {
            //装进map
            map.put(element.getName(), element.getText());
        }
//关闭流
        in.close();
        return map;
    }

    private static InputStream String2Inputstream(String strxml) throws IOException {
        return new ByteArrayInputStream(strxml.getBytes("UTF-8"));
    }


    public static boolean isTenpaySign(Map<String, String> map,String key) {
        String characterEncoding="utf-8";
        String charset = "utf-8";
        String signFromAPIResponse = map.get("sign");

        if (signFromAPIResponse == null || signFromAPIResponse.equals("")) {
            System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
//        System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);     //过滤空 设置 TreeMap
            SortedMap<String,String> packageParams = new TreeMap();
            for (String parameter : map.keySet()) {
                String parameterValue = map.get(parameter);
                String v = "";
                if (null != parameterValue) {
                    v = parameterValue.trim();
                }
                packageParams.put(parameter, v);
            }
            StringBuffer sb = new StringBuffer();
            Set es = packageParams.entrySet();
            Iterator it = es.iterator();
            while(it.hasNext()) {
                Map.Entry entry = (Map.Entry)it.next();
                String k = (String)entry.getKey();
                String v = (String)entry.getValue();
                if(!"sign".equals(k) && null != v && !"".equals(v)) {
                    sb.append(k + "=" + v + "&");
                }
            }
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较     //算出签名
            sb.append("key=" + key);
            String resultSign = "";
            String tobesign = sb.toString();
            if (null == charset || "".equals(charset)) {
                resultSign = MD5Encode(tobesign, characterEncoding).toUpperCase();
            }else{
                try{
                       resultSign = MD5Encode(tobesign, characterEncoding).toUpperCase();
                }catch (Exception e) {
                       resultSign = MD5Encode(tobesign, characterEncoding).toUpperCase();
                }
            }
            String tenpaySign = packageParams.get("sign").toUpperCase();
            return tenpaySign.equals(resultSign);
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
        n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String GetMapToXML(Map<String,String> param){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        for (Map.Entry<String,String> entry : param.entrySet()) {
            sb.append("<"+ entry.getKey() +">");
            sb.append(entry.getValue());
            sb.append("</"+ entry.getKey() +">");
        }
        sb.append("</xml>");
        return sb.toString();
    }


    public static Date stringtoDate(String dateStr, String format) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e) {
            // log.error(e);
            d = null;
        }        return d;
    }


    public static String dateToString(Date date, String format) {
        String result = "";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            result = formater.format(date);
        } catch (Exception e) {
            // log.error(e);
        }
        return result;
    }






}
