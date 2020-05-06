package ink.boyuan.rbac_shiro.mapper;

import ink.boyuan.rbac_shiro.domain.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author 有缘
 * @version 1.0
 * @date 2020/4/14 10:47
 * @description
 **/
@Mapper
public interface RoleMapper {


    /**
     *根据用户id获取用户角色集合
     * @param userId
     * @return
     */
    @Select("select ur.role_id as id, " +
            "r.name as roleName, " +
            "r.description as remark " +
            " from  user_role ur left join role r on ur.role_id = r.id " +
            "where  ur.user_id = #{userId}")
    @Results(
            value = {
                    @Result(id=true, property = "id",column = "id"),
                    @Result(property = "name",column = "name"),
                    @Result(property = "description",column = "description"),
                    @Result(property = "permissionList",column = "id",
                            many = @Many(select = "ink.boyuan.rbac_shiro.mapper.PermissionMapper.getPermissionListByRoleId", fetchType = FetchType.DEFAULT)
                    )
            }
    )
    List<Role> getRoleListByUserId(@Param(value = "userId")int userId);


}
