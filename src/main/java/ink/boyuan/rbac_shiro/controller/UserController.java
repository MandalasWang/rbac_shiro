package ink.boyuan.rbac_shiro.controller;

import ink.boyuan.rbac_shiro.domain.Role;
import ink.boyuan.rbac_shiro.domain.Users;
import ink.boyuan.rbac_shiro.enums.JsonData;
import ink.boyuan.rbac_shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


/**
 * @author wyy
 * @version 1.0
 * @date 2020/4/14 9:46
 * @description
 **/
@RestController
@RequestMapping(name = "用户模块", value = "authc/user")
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping(name = "插入用户信息",value = "/addUser")
    public JsonData addUser(@RequestBody Users users) {

        userService.addUser(users);
        return JsonData.buildSuccess();
    }



    @RequestMapping(name = "根据id获取角色所有权限",value = "/getPermissionById")
    public JsonData getRoles(@RequestParam(value = "id") Integer id){
        List<Role> roles = userService.getRoles(id);
        return JsonData.buildSuccess(roles);

    }




}
