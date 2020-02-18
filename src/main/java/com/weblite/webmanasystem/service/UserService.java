package com.weblite.webmanasystem.service;

import com.weblite.webmanasystem.constant.Common;
import com.weblite.webmanasystem.constant.USERCODE;
import com.weblite.webmanasystem.domain.dto.UserInfoDto;
import com.weblite.webmanasystem.domain.entity.Authority;
import com.weblite.webmanasystem.domain.entity.User;
import com.weblite.webmanasystem.domain.entity.View;
import com.weblite.webmanasystem.mapper.AuthorityMapper;
import com.weblite.webmanasystem.mapper.UserMapper;
import com.weblite.webmanasystem.mapper.ViewMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ：Beatrice
 * @date ：Created in 2020/2/15 11:34
 * @Description:
 */
@Service
public class UserService {
    @Resource
    UserMapper userMapper;
    @Resource
    AuthorityMapper authorityMapper;
    @Resource
    ViewMapper viewMapper;


    //身份校验
    public String getAuthByUId(String uId)
    {
        Authority authority = new Authority();
        authority.setuId(uId);
        return authorityMapper.selectBySelective(authority).get(0).getaIdentity();
    }

    //登录
    public UserInfoDto login(String username,String password)
    {
        User user = new User();
        user.setuName(username);
        user.setuPassword(password);
        List<User> userList = Optional.ofNullable(userMapper.selectBySelective(user)).orElseGet(()->new ArrayList<>());
        String uId = userList.get(0).getuId();
        Authority authority = new Authority();
        authority.setuId(uId);
        List<Authority> authList = Optional.ofNullable(authorityMapper.selectBySelective(authority)).orElseGet(() -> new ArrayList<>());
        String aIdentity = authList.get(0).getaIdentity();
        return new UserInfoDto().setuId(uId).setaIdentity(aIdentity);
    }

    //注册信息
    public Integer register(String username,String password)
    {
        Long flowNum = Common.getFlowNum();
        String uId= USERCODE.STU.getCode()+flowNum;
        User user = new User();
        user.setuId(uId);
        user.setuPassword(password);
        user.setuName(username);
        Authority authority = new Authority();
        authority.setuId(uId);
        authority.setaIdentity(USERCODE.STU.getAuth());
        int userInsert = userMapper.insertSelective(user);
        int authInsert = authorityMapper.insertSelective(authority);
        if(userInsert==0||authInsert==0)
        {
            return 0;
        }
        return 1;
    }
}
