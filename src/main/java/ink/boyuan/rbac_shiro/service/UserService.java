package ink.boyuan.rbac_shiro.service;

import ink.boyuan.rbac_shiro.domain.Role;
import ink.boyuan.rbac_shiro.domain.Users;

import java.util.List;

/**
 * @author 有缘
 * @version 1.0
 * @date 2020/4/14 9:46
 * @description
 **/
public interface UserService {


    /**
     * 用户信息新增
     * @param users
     */
    void addUser(Users users);

    /**
     * 通过username获取用户信息
     * @param userName
     * @return
     */
    Users getUserInfoByUsername(String userName);

    /**
     * 获取所有roles
     * @param id
     * @return
     */
    List<Role> getRoles(Integer id);
}
