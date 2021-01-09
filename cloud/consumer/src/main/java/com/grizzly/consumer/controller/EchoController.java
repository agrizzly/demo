package com.grizzly.consumer.controller;


import com.grizzly.consumer.client.EchoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class EchoController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EchoClient echoClient;

    @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
    public String echo(@PathVariable String str) {
        return restTemplate.getForObject("http://service-provider/echo/" + str, String.class);
    }
    @RequestMapping(value = "/echo2/{str}", method = RequestMethod.GET)
    public String echo2(@PathVariable String str) {
        return echoClient.echo(str);
    }




}
