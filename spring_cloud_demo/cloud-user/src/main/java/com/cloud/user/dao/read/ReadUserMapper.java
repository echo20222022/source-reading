package com.cloud.user.dao.read;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

//@Mapper
//@Repository
public interface ReadUserMapper {

    @Select("select * from user where id = #{id}")
    List<Map<String, Object>> getUserById(@Param("id") Integer id);
}
