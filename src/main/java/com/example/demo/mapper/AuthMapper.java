package com.example.demo.mapper;

import com.example.demo.entity.Role;
import com.example.demo.entity.UserDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface AuthMapper {
    /**
     * 根据用户名查找用户
     * @param name
     * @return
     */
    @Select("SELECT id, name, password from sys_user where name = #{name}")
    UserDetail findByUsername(@Param("name") String name);

    /**
     * 创建新用户
     * @param userDetail
     */
    @Insert("insert into sys_user (name, password) VALUES (#{username}, #{password})")
    void insert(UserDetail userDetail);

    /**
     * 创建用户角色
     * @param userId
     * @param roleId
     * @return
     */
    @Insert("insert into sys_user_role (user_id, role_id) VALUES (#{userId}, #{roleId})")
    int insertRole(@Param("userId") long userId, @Param("roleId") long roleId);

    /**
     * 根据角色id查找角色
     * @param roleId
     * @return
     */
    @Select("select id, name, nameZh from sys_role where id = #{roleId}")
    Role findRoleById(@Param("roleId") long roleId);

    /**
     * 根据用户id查找该用户角色
     * @param userId
     * @return
     */
    @Select("select * from sys_role where id in (SELECT role_id from sys_user_role where user_id = #{userId})")
    Role findRoleByUserId(@Param("userId") long userId);
}