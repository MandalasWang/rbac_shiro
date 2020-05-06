package ink.boyuan.rbac_shiro.service.impl;

import ink.boyuan.rbac_shiro.domain.Permission;
import ink.boyuan.rbac_shiro.domain.Role;
import ink.boyuan.rbac_shiro.domain.Users;
import ink.boyuan.rbac_shiro.mapper.PermissionMapper;
import ink.boyuan.rbac_shiro.mapper.RoleMapper;
import ink.boyuan.rbac_shiro.mapper.UserMapper;
import ink.boyuan.rbac_shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wyy
 * @version 1.0
 * @date 2020/4/14 9:46
 * @description
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public void addUser(Users users) {
        userMapper.insertUser(users);
    }


    @Override
    public Users getUserInfoByUsername(String username){
        Users users = userMapper.findByUsername(username);
        List<Role> roles = roleMapper.getRoleListByUserId(users.getId());
        users.setRoleList(roles);
        return users;
    }

    @Override
    public List<Role> getRoles(Integer id) {
        List<Role> roles = roleMapper.getRoleListByUserId(id);
        return roles;
    }
}
