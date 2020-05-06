package ink.boyuan.rbac_shiro.controller;

import ink.boyuan.rbac_shiro.enums.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyy
 * @version 1.0
 * @Classname VideoController
 * @date 2020/4/23 14:43
 * @description
 **/
@RestController
@RequestMapping(value = "/goods/video")
public class VideoController {


    @RequestMapping(value = "update")
    public JsonData videoUpdate(){
        return JsonData.buildSuccess();
    }
}
