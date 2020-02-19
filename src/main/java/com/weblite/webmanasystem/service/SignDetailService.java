package com.weblite.webmanasystem.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.weblite.webmanasystem.constant.Common;
import com.weblite.webmanasystem.constant.ExcelWriteSheetFormat;
import com.weblite.webmanasystem.constant.ExcelWriterHelper;
import com.weblite.webmanasystem.domain.dto.UserSignDto;
import com.weblite.webmanasystem.domain.entity.Authority;
import com.weblite.webmanasystem.domain.entity.SignDetail;
import com.weblite.webmanasystem.mapper.AuthorityMapper;
import com.weblite.webmanasystem.mapper.SignDetailMapper;
import com.weblite.webmanasystem.mapper.UserMapper;
import com.weblite.webmanasystem.mapper.ViewMapper;
import org.apache.commons.collections4.BagUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author ：Beatrice
 * @date ：Created in 2020/2/15 11:34
 * @Description:
 */
@Service
public class SignDetailService {
    @Resource
    ViewMapper viewMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    SignDetailMapper signDetailMapper;
    @Resource
    AuthorityMapper authorityMapper;


    public List<UserSignDto> getUserSignList(UserSignDto userSignDto,Integer page,Integer pageSize)
    {
        page=(page-1)*pageSize;
        //判空
        UserSignDto userSignDtos1 = Optional.ofNullable(userSignDto).orElseGet(UserSignDto::new);
        SignDetail signDetail = new SignDetail();
        BeanUtils.copyProperties(userSignDtos1,signDetail);
        List<SignDetail> signDetails = signDetailMapper.selectAllSelective(signDetail,page,pageSize);
        List<UserSignDto> userSignDtos = new ArrayList<>();
        //BeanUtils.copyProperties(signDetails,userSignDtos);
        //entity装填Dto
        signDetails.forEach(signDetail1 -> {
            UserSignDto userDto = new UserSignDto();
            BeanUtils.copyProperties(signDetail1,userDto);
            userSignDtos.add(userDto);
        });
        return userSignDtos;
    }

    public Integer count(UserSignDto userSignDto)
    {
        //判空
        UserSignDto userSignDtos1 = Optional.ofNullable(userSignDto).orElseGet(UserSignDto::new);
        SignDetail signDetail = new SignDetail();
        BeanUtils.copyProperties(userSignDtos1,signDetail);
        return signDetailMapper.selectCountBySelective(signDetail);
    }

    public List<SignDetail> getSignDetailList(List<Integer> signDetailList)
    {
        List<SignDetail> list=new ArrayList<>();
        signDetailList.forEach(sign->{
            SignDetail signDetail = signDetailMapper.selectByPrimaryKey(sign);
            list.add(signDetail);
        });
        return list;
    }

    public Integer cancelSign(Integer id)
    {
        return signDetailMapper.deleteByPrimaryKey(id);
    }


    //Excel映射对象
    public static <T> List<Map<String,Object>> convertObjToMap(List<T> list)
    {
        return JSON.parseObject(JSONArray.toJSONString(list), new TypeReference<List<Map<String, Object>>>() {});
    }




}
