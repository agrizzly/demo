package com.grizzly.permission.controller;


import com.grizzly.permission.service.RoleResourceService;
import com.grizzly.utils.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author grizzly
 * @since 2021-01-12
 */
@RestController
@RequestMapping("/permission/role-resource")
public class RoleResourceController {

    @Autowired
    private RoleResourceService roleResourceService;

    @PostMapping("resource2role")
    public R resource2role(String rid, String[] resourceids){
        return roleResourceService.resource2role(rid,resourceids);
    }
}

