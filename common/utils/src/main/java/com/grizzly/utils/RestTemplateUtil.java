package com.grizzly.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.Map;

@Slf4j
public class RestTemplateUtil {

    private static class DefaultResponseErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return response.getStatusCode().value() != HttpServletResponse.SC_OK;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getBody()));
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            throw new RuntimeException(sb.toString());
        }
    }

    public static String get(RestTemplate restTemplate, String url) {
        log.info("url={}",url);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        return restTemplate.getForObject(url, String.class);
    }

    public static JSONObject getJSONObject(RestTemplate restTemplate, String url) {
        return JSON.parseObject(get(restTemplate, url));
    }

    public static String post(RestTemplate restTemplate, String url, HttpServletRequest request) {
        return post(restTemplate, url, getParamMap(request));
    }

    public static JSONObject postJSONObject(RestTemplate restTemplate, String url, MultiValueMap<String, String> map) {
        return JSON.parseObject(post(restTemplate, url, map));
    }

    public static String post(RestTemplate restTemplate, String url, MultiValueMap<String, String> map) {
        log.info("url={}",url);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        return restTemplate.postForObject(url, map, String.class);
    }

    public static JSONObject postJSONObject(RestTemplate restTemplate, String url, JSONObject jsonObject) {
        return JSON.parseObject(post(restTemplate, url, jsonObject));
    }

    public static String post(RestTemplate restTemplate, String url, JSONObject jsonObject) {
        log.info("url={}",url);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);
        return restTemplate.postForObject(url, entity, String.class);
    }

    public static MultiValueMap<String, String> getParamMap(HttpServletRequest request) {
        Enumeration<String> params = request.getParameterNames();
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        while (params.hasMoreElements()) {
            String paraName = params.nextElement();
            // System.out.println("==="+paraName+","+request.getParameter(paraName));
            paramMap.add(paraName, request.getParameter(paraName));
        }
        return paramMap;
    }
    public static InputStream postInputStream(RestTemplate restTemplate, String url, Map<String,Object> param){
        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        HttpEntity requestEntity = new HttpEntity(param,headers);
        ResponseEntity<byte[]> entity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
        byte[] result = entity.getBody();
        return new ByteArrayInputStream(result);
    }
}