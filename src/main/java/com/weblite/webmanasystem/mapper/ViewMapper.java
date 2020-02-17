package com.weblite.webmanasystem.mapper;

import com.weblite.webmanasystem.domain.entity.View;

public interface ViewMapper {
    int deleteByPrimaryKey(String vId);

    int insert(View record);

    int insertSelective(View record);

    View selectByPrimaryKey(String vId);

    int updateByPrimaryKeySelective(View record);

    int updateByPrimaryKey(View record);
}