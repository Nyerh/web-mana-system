package com.weblite.webmanasystem.service;

import com.weblite.webmanasystem.domain.dto.UserInfoDto;
import com.weblite.webmanasystem.domain.entity.Authority;
import com.weblite.webmanasystem.domain.entity.User;
import com.weblite.webmanasystem.mapper.AuthorityMapper;
import com.weblite.webmanasystem.mapper.UserMapper;
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
}
