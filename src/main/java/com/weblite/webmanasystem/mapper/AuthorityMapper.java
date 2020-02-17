package com.weblite.webmanasystem.mapper;

import com.weblite.webmanasystem.domain.entity.Authority;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthorityMapper {
    int deleteByPrimaryKey(String aId);

    int insert(Authority record);

    int insertSelective(Authority record);

    Authority selectByPrimaryKey(String aId);

    int updateByPrimaryKeySelective(Authority record);

    int updateByPrimaryKey(Authority record);

    List<Authority> selectAllSelective(@Param("authority") Authority authority,@Param("page") Integer page,@Param("pageSize") Integer pageSize);

    List<Authority> selectBySelective(@Param("authority")Authority authority);
}