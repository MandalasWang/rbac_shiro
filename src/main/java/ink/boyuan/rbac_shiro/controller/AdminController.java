package ink.boyuan.rbac_shiro.controller;

import ink.boyuan.rbac_shiro.enums.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyy
 * @version 1.0
 * @Classname AdminController
 * @date 2020/4/28 10:56
 * @description
 **/
@RestController
@RequestMapping(value = "/admin")
public class AdminController {


    @RequestMapping(value = "/update")
    public JsonData updateGoods(){
        return JsonData.buildSuccess();
    }

}
