package ink.boyuan.rbac_shiro.mapper;

import ink.boyuan.rbac_shiro.domain.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 有缘
 * @version 1.0
 * @date 2020/4/14 10:48
 * @description
 **/
@Mapper
public interface PermissionMapper {


    /**
     * 根据角色id查询权限集合
     * @param roleId
     * @return
     */
    @Select("select p.id as id, p.name as name, p.url as url from  role_permission rp " +
            "left join permission p on rp.permission_id=p.id " +
            "where  rp.role_id= #{roleId} ")
    List<Permission> getPermissionListByRoleId(@Param("roleId") int roleId);
}
