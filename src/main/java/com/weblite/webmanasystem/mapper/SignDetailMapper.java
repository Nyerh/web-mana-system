package com.weblite.webmanasystem.mapper;

import com.weblite.webmanasystem.domain.entity.SignDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SignDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SignDetail record);

    int insertSelective(SignDetail record);

    SignDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SignDetail record);

    int updateByPrimaryKey(SignDetail record);

    List<SignDetail> selectAllSelective(@Param("signDetail") SignDetail signDetail,@Param("page") Integer page,@Param("pageSize") Integer pageSize);
}