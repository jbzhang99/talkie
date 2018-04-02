package com.meta.controller.map;

import com.meta.json.JacksonUtil;
import com.meta.model.map.MetaBaseStation;
import com.meta.model.map.resultDemo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * create by lhq
 * create date on  18-1-30下午2:59
 *
 * @version 1.0
 **/
public class JuHeBaseStationUtil {

    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    //配置您申请的KEY
    public static final String APPKEY = "ecb0286c35231c200e0999faea01f4a9";


    //1.电信基站定位
    public static resultDemo getRequest1(MetaBaseStation metaBaseStation) {
        String result = null;
        resultDemo rd = new resultDemo();
        String url = "http://v.juhe.cn/cdma/";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("sid", metaBaseStation.getSid());//SID系统识别码（各地区一个）
        params.put("nid", metaBaseStation.getNid());//NID网络识别码（各地区1-3个）
        params.put("cellid", metaBaseStation.getCellId());//基站号(bid)
        params.put("hex", "10");//进制类型，16或10，默认：10
        params.put("dtype", "json");//返回的数据格式：json/xml/jsonp
        params.put("callback", "");//当选择jsonp格式时必须传递
        params.put("key", APPKEY);//APP KEY

        try {
            result = net(url, params, "GET");
            rd = JacksonUtil.readValue(result, resultDemo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rd;
    }


    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
                    out.writeBytes(urlencode(params));
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }


    //将map型转为请求参数型
    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
