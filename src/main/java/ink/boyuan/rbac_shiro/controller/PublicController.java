package ink.boyuan.rbac_shiro.controller;

import ink.boyuan.rbac_shiro.domain.UserBO;
import ink.boyuan.rbac_shiro.enums.JsonData;
import ink.boyuan.rbac_shiro.utils.CommonUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wyy
 * @version 1.0
 * @Classname PublicController
 * @date 2020/4/20 10:03
 * @description
 **/
@RestController
@RequestMapping(name = "公共接口",value = "/pub")
public class PublicController {


    @RequestMapping(name = "设置的未登录拦截接口",value = "/need_login")
    public JsonData needLogin(){
        return JsonData.buildSuccess(-2,"请先登录");
    }

    @RequestMapping(name = "设置登录但未授权的用户拦截接口",value = "/need_permission")
    public JsonData needPermission(){
        return JsonData.buildSuccess(-3,"您没有权限，请联系管理员！");
    }


    @RequestMapping(name = "初始化界面",value = "/index")
    public JsonData index(){
        List<String> goods = new ArrayList<>();
        goods.add("空调");
        goods.add("水壶");
        goods.add("电冰箱");
        goods.add("热水器");
        goods.add("烤箱");
        return JsonData.buildSuccess(goods);
    }


    @RequestMapping(name = "登录",value = "login")
    public JsonData login(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response){
        Subject subject = SecurityUtils.getSubject();
        Map<String,Object> map = new HashMap<>(16);
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userBO.getName(), userBO.getPwd());
            subject.login(usernamePasswordToken);
            map.put("token",subject.getSession().getId());
        }catch (Exception e){
            map.put("msg","账号或密码不存在!");
        }
        return JsonData.buildSuccess(map);
    }
}
