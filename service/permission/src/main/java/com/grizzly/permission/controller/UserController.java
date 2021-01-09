package com.grizzly.permission.controller;


import com.grizzly.permission.entity.User;
import com.grizzly.permission.service.UserService;
import com.grizzly.utils.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author grizzly
 * @since 2021-01-09
 */
@RestController
@RequestMapping("/permission/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("save")
    public R save(@RequestBody User user){
        userService.save(user);
        return R.ok();
    }

    @GetMapping("get/{id}")
    public R get(@PathVariable("id") String id){
        User user = userService.getById(id);
        return R.ok().data("user",user);
    }

    @PostMapping("update")
    public R update(@RequestBody User user){
        String id = user.getId();
        User u = userService.getById(id);
        user.setName("wxf");
        user.setVersion(u.getVersion());
        userService.updateById(user);
        return R.ok();
    }

    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable("id") String id){
        userService.removeById(id);
        return  R.ok();
    }

    @GetMapping("getAll")
    public R getAll(){
        return R.ok().data("users",userService.getMap(null));
    }

}

