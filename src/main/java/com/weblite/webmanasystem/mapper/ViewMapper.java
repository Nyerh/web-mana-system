package com.weblite.webmanasystem.mapper;

import com.weblite.webmanasystem.domain.entity.View;
import org.apache.ibatis.annotations.Mapper;

/*@Mapper*/
public interface ViewMapper {
    int deleteByPrimaryKey(Integer vId);

    int insert(View record);

    int insertSelective(View record);

    View selectByPrimaryKey(Integer vId);

    int updateByPrimaryKeySelective(View record);

    int updateByPrimaryKey(View record);
}