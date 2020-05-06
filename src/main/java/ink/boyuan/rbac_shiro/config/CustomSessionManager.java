package ink.boyuan.rbac_shiro.config;


import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author wyy
 * @version 1.0
 * @Classname CustomSessionManager
 * @date 2020/4/16 13:35
 * @description
 **/
public class CustomSessionManager extends DefaultWebSessionManager {


    private final static String AUTHORIZATION = "token";

    public CustomSessionManager(){
         super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {

        String sessionId = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        if(null != sessionId ){
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
           return sessionId;
        }else{
          return super.getSessionId(request,response);
        }
    }

}