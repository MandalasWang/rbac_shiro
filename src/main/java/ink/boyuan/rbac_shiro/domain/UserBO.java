package ink.boyuan.rbac_shiro.domain;

import java.io.Serializable;

/**
 * @author wyy
 * @version 1.0
 * @Classname UserBO
 * @date 2020/4/20 11:12
 * @description
 **/
public class UserBO implements Serializable {

    private String name;

    private String pwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "UserBO{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
