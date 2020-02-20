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
import java.util.*;

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
        String aId=Common.getFlowNum().toString();
        User user = new User();
        user.setuId(uId);
        user.setuPassword(password);
        user.setuName(username);
        Authority authority = new Authority();
        authority.setaId(aId);
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

    //
    public Authority getAuth(Authority authority)
    {
        return Optional.ofNullable(authorityMapper.selectBySelective(authority)).orElseGet(()->new ArrayList<>()).get(0);
    }

    //改变用户-身份
    public Integer changeAuth(Authority authority)
    {
        return authorityMapper.updateByPrimaryKeySelective(authority);
    }
    //改变用户信息
    public Integer changeUserInfo(User user)
    {
        return userMapper.updateByPrimaryKeySelective(user);
    }


    public Map<String,String> getUserInfoMap(User user)
    {
        List<User> users = Optional.ofNullable(userMapper.selectBySelective(user)).orElseGet(() -> new ArrayList<User>());
        Map<String, String> maps = new HashMap<>();
        users.forEach(a->maps.put(a.getuId(),a.getuName()));
        return maps;
    }

    public Map<String,String> getUserAuthMap(Authority authority)
    {
        List<Authority> authorities = Optional.ofNullable(authorityMapper.selectBySelective(authority)).orElseGet(() -> new ArrayList<Authority>());
        Map<String, String> maps = new HashMap<>();
        authorities.forEach(a->maps.put(a.getuId(),a.getaIdentity()));
        return maps;
    }


}
