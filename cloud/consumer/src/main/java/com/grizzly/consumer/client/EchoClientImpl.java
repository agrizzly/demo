package com.grizzly.consumer.client;

import org.springframework.stereotype.Component;

@Component
public class EchoClientImpl implements EchoClient{

    //出错之后会执行
    @Override
    public String echo(String str) {
        return "没有内容";
    }
}
