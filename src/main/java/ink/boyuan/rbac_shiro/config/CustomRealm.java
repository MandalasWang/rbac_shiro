package ink.boyuan.rbac_shiro.config;

import ink.boyuan.rbac_shiro.domain.Permission;
import ink.boyuan.rbac_shiro.domain.Role;
import ink.boyuan.rbac_shiro.domain.Users;
import ink.boyuan.rbac_shiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wyy
 * @version 1.0
 * @date 2020/4/13 17:13
 * @description  自定义realm
 **/
public class CustomRealm  extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 用户授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权doGetAuthorizationInfo");

        Users userName = (Users)principalCollection.getPrimaryPrincipal();

        Users info = userService.getUserInfoByUsername(userName.getUsername());

        List<String> roleList = new ArrayList<>();
        List<String> permissionList = new ArrayList<>();

        info.getRoleList().forEach(role->{
            roleList.add(role.getRoleName());
            permissionList.addAll(role.getPermissionList()
                    .stream()
                    .map(Permission::getName)
                    .collect(Collectors.toList()));
        });

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        simpleAuthorizationInfo.addRoles(roleList);
        simpleAuthorizationInfo.addStringPermissions(permissionList);
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     * @param authenticationToken
     * @return AuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("用户认证doGetAuthenticationInfo");

        String username = (String)authenticationToken.getPrincipal();

        Users info = userService.getUserInfoByUsername(username);

        String pwd = info.getPassword();
        if(pwd == null || "".equals(pwd)){
            return null;
        }
        return new SimpleAuthenticationInfo(info,info.getPassword(),this.getClass().getName());
    }
}
