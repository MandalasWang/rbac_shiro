package ink.boyuan.rbac_shiro.controller;

import ink.boyuan.rbac_shiro.enums.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wyy
 * @version 1.0
 * @Classname OrderController
 * @date 2020/4/23 14:17
 * @description
 **/
@RestController
@RequestMapping(value = "/author")
public class OrderController {

    @RequestMapping(value = "listOrder")
    public JsonData ListOrder(){
        Map<String,String> map = new HashMap<>(16);
        map.put("springboot基础","19.00元");
        map.put("redis基础","29.00元");
        map.put("activeMq基础","13.00元");
        return JsonData.buildSuccess(map);

    }

}
