package org.xdl.crawler;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.*;


import java.io.IOException;

/**
 * 请求类
 * */
public class Requests {

    /*private static RestTemplate restTemplate;

    static {
        restTemplate = BeanUtils.getBean(RestTemplate.class);
    }*/

    public static String request(String url) {
        StringBuilder getUrl = new StringBuilder("https://query.sse.com.cn/statusAction.do");
        /*getUrl.append("jsonCallBack=").append("jsonpCallback7174")
                .append("&")
                .append("isPagination=").append("true")
                .append("&")
                .append("sqlId=").append("SH_XM_LB")
                .append("&")
                .append("pageHelp.pageSize=").append("20")
                .append("&")
                .append("order=").append("updateDate|desc")
                .append("&")
                .append("pageHelp.pageNo=").append("2")
                .append("&")
                .append("pageHelp.beginPage=").append("2")
                .append("&")
                .append("pageHelp.endPage=").append("2")
                .append("&")
                .append("_=").append("1639448904018");*/
        HttpClient httpClient = new HttpClient();
        // 设置 HTTP 连接超时 5s
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        // 2.生成 GetMethod 对象并设置参数
        GetMethod getMethod = new GetMethod(getUrl.toString());
        // 设置 get 请求超时 5s
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        // 设置请求重试处理
        getMethod.getParams().setParameter("jsonCallBack", "jsonpCallback7174");
        getMethod.getParams().setParameter("isPagination", "true");
        getMethod.getParams().setParameter("sqlId", "SH_XM_LB");
        getMethod.getParams().setParameter("pageHelp.pageSize", "20");
        getMethod.getParams().setParameter("order", "updateDate|desc");
        getMethod.getParams().setParameter("pageHelp.pageNo", "2");
        getMethod.getParams().setParameter("pageHelp.beginPage", "2");
        getMethod.getParams().setParameter("pageHelp.endPage", "2");
        getMethod.getParams().setParameter("Referer", "https://kcb.sse.com.cn/renewal/");
        getMethod.setRequestHeader(new Header("Referer","https://kcb.sse.com.cn/renewal/"));
        // 3.执行 HTTP GET 请求
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            // 判断访问的状态码
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
            }
            // 4.处理 HTTP 响应内容
            // 读取为字节 数组
            byte[] responseBody = getMethod.getResponseBody();
            // 得到当前返回类型
            String content = getMethod.getResponseHeader("Content-Type").getValue();
            System.out.println(content);
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("Please check your provided http address!");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
            // 释放连接
            getMethod.releaseConnection();
        }

        return null;
    }

    public static void main(String[] args) {
        request("");
    }
}
