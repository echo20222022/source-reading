package com.cloud.user.dao;


import com.cloud.user.bean.User;
import com.cloud.user.ds.DS;
import com.cloud.user.ds.DynamicOperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {

    @DS(DynamicOperationType.WRITE)
    @Insert("insert into user(name, age, sex) values (#{name}, #{age}, #{sex});")
    int insert(User user);

    @DS(DynamicOperationType.READ)
    @Select("select * from user where id = #{id}")
    Map<String, Object> selectById(@Param("id") Integer id);
}