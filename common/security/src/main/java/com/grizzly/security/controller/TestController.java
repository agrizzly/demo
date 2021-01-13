package com.grizzly.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String index(){
        return "security jwt";
    }

    @GetMapping("/role1")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String role1(){
        return "需要获得admin权限，才可以访问";
    }

    @GetMapping("/role2")
    @PreAuthorize("hasAnyAuthority('editor')")
    public String role2(){
        return "需要获得kdream权限，才可以访问";
    }

    @GetMapping("/role3")
    @PreAuthorize("hasAnyAuthority('manager')")
    public String role3(){
        return "需要获得manager权限，才可以访问";
    }
}
