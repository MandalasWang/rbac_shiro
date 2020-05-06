package ink.boyuan.rbac_shiro.mapper;

import ink.boyuan.rbac_shiro.domain.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 有缘
 * @version 1.0
 * @date 2020/4/14 9:30
 * @description
 **/
@Mapper
public interface UserMapper {

    /**
     *新增用户信息
     * @param users
     * @return
     */
    @Insert("insert into user(username,password,password_salt)values (#{username},#{password},#{passwordSalt})")
    int insertUser(Users users);


    /**
     * 通过username查询用户信息
     * @param username
     * @return
     */
    @Select("select id,username,password from user where username =#{username}")
    Users findByUsername(@Param(value = "username")String username);

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @Select("select * from user where id =#{id}")
    Users findById(@Param(value = "id")int id);
}
