package com.weblite.webmanasystem.mapper;

import com.weblite.webmanasystem.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;import java.util.List;
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(String uId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String uId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAllSelective(@Param("user") User user, @Param("page") Integer page, @Param("pageSize") Integer pageSize);

    List<User> selectBySelective(@Param("user") User user);
}