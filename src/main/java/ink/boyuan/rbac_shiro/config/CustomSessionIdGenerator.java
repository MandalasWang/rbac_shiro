package ink.boyuan.rbac_shiro.config;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author wyy
 * @version 1.0
 * @Classname CustomSessionIdGenerator
 * @date 2020/4/29 13:59
 * @description
 **/
public class CustomSessionIdGenerator implements SessionIdGenerator {


    @Override
    public Serializable generateId(Session session) {

        return "boyuan"+UUID.randomUUID().toString().replace("-","");
    }
}
