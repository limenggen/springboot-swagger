package com.morgan.controller;


import com.morgan.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Api(tags = "用户管理")
@RestController
@RequestMapping(value = "user")
public class testController {
    private static Map<Integer, User> userMap;

    static {
        userMap = new ConcurrentHashMap<>();
        User user = new User(100, "admin", true, new Date());
    }

    @ApiOperation("列表查询")
    @GetMapping(value = "list")
    public List<User> list(){
        return new ArrayList<>(userMap.values());
    }

    @ApiOperation(value = "获取用户详细信息", notes = "路径参数ID")
    @GetMapping(value = "{id}")
    public User detail(@PathVariable Integer id){
        return userMap.get(id);
    }

    @ApiOperation(value = "新增或更新用户信息", notes = "insert和update共用", response = User.class)
    @PostMapping(value = "add")
    public User add(@RequestBody User user){
        if(user == null || user.getId() == null || StringUtils.isEmpty(user.getName()) || userMap.containsKey(user.getId())){
            return null;
        }
        user.setUpdateTime(new Date());
        userMap.put(user.getId(), user);
        return user;
    }

    @ApiOperation(value = "删除用户")
    @GetMapping(value = "del/{id}")
    public Boolean delete(@ApiParam(name = "用户ID", required = true, example = "100") Integer id){
        if(userMap.containsKey(id)){
            userMap.remove(id);
            return true;
        }

        return false;
    }
}
