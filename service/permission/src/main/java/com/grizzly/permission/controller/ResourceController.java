package com.grizzly.permission.controller;


import com.grizzly.permission.service.ResourceService;
import com.grizzly.utils.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author grizzly
 * @since 2021-01-12
 */
@RestController
@RequestMapping("/permission/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("allresource")
    public R allresource(){
        return resourceService.allresource();
    }

    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable("id") String id){
        return resourceService.delete(id);
    }


}

