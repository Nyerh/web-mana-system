package com.weblite.webmanasystem.api;

import com.weblite.webmanasystem.constant.Msg;
import com.weblite.webmanasystem.constant.STATE;
import com.weblite.webmanasystem.domain.dto.UserInfoDto;
import com.weblite.webmanasystem.domain.dto.UserListDto;
import com.weblite.webmanasystem.domain.entity.Authority;
import com.weblite.webmanasystem.domain.entity.User;
import com.weblite.webmanasystem.service.SignDetailService;
import com.weblite.webmanasystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author ：Beatrice
 * @date ：Created in 2020/2/15 10:40
 * @Description:
 */
@RestController
@Api(description = "用户管理接口")
@RequestMapping("userManager")
public class UserManagerApi {
    @Resource
    SignDetailService signDetailService;
    @Resource
    UserService userService;


    @PostMapping("/userLogin")
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", dataType = "String", value = "账号"),
            @ApiImplicitParam(name = "password", dataType = "String", value = "密码"),
    })
    public Msg userLogin(@RequestParam(value = "username") String username,
                         @RequestParam(value = "password") String password)
    {
        UserInfoDto login = userService.login(username, password);
        if(login.getuId()==null||login.getaIdentity()==null)
        {
            return new Msg().setMsg("用户不存在")
                    .setState(STATE.ParamErr.getState());
        }
        return new Msg().setMsg("登录成功")
                .setItems(login)
                .setState(STATE.ParamErr.getState());
    }


    @PostMapping("/userRegister")
    @ApiOperation("用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", dataType = "String", value = "账号"),
            @ApiImplicitParam(name = "password", dataType = "String", value = "密码"),
    })
    public Msg userRegister(@RequestParam(value = "username") String username,
                          @RequestParam(value = "password") String password)
    {
        Integer register = userService.register(username, password);
        if(register==0)
        {
            return new Msg().setState(STATE.ParamErr.getState())
                    .setMsg("注册时失败,检查参数");
        }
        return new Msg().setState(STATE.Success.getState())
                .setMsg("注册成功");
    }


    @GetMapping("/userInfoChange")
    @ApiOperation("管理员修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uId", dataType = "String", value = "用户id"),
            @ApiImplicitParam(name = "username", dataType = "String", value = "账号"),
            @ApiImplicitParam(name = "password", dataType = "String", value = "密码"),
            @ApiImplicitParam(name = "aIdentity", dataType = "String", value = "身份"),
            @ApiImplicitParam(name = "curUId", dataType = "String", value = "当前用户id"),
    })
    @Transactional(rollbackFor = Exception.class)
    public Msg userInfoChange(@RequestParam(value = "uId")String uId,
                              @RequestParam(value = "username")String username,
                              @RequestParam(value = "password") String password,
                              @RequestParam(value = "aIdentity") String aIdentity,
                              @RequestParam(value = "curUId") String curUId) throws Exception
    {
        String authByUId = userService.getAuthByUId(curUId);
        String s = Optional.ofNullable(authByUId).orElseGet(() -> "");
        if(!s.equals("管理员"))
        {
            return new Msg()
                    .setMsg("权限不足")
                    .setState(STATE.PermissionDenied.getState());
        }
        User user = new User();
        user.setuId(uId);
        user.setuName(username);
        user.setuPassword(password);
        Authority auth = new Authority();
        auth.setuId(uId);
        Authority auth1 = userService.getAuth(auth);
        if(auth1==null)
        {
            return new Msg()
                    .setState(STATE.ParamErr.getState())
                    .setMsg("获取失败");
        }
        auth1.setaIdentity(aIdentity);
        Integer userChange = userService.changeUserInfo(user);
        if(userChange==0)
        {
            throw new Exception("更新用户信息失败");
        }
        Integer authChange = userService.changeAuth(auth1);
        if(authChange==0)
        {
            throw new Exception("更新用户身份失败");
        }
        return new Msg()
                .setState(STATE.Success.getState())
                .setMsg("用户信息修改成功");
    }


    @PostMapping("/showUserList")
    @ApiOperation("展示用户清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", dataType = "String", value = "账号"),
            @ApiImplicitParam(name = "uId", dataType = "String", value = "用户id"),
            @ApiImplicitParam(name = "aIdentity", dataType = "String", value = "身份"),
    })
    public Msg showUserList(@RequestParam(value = "username",required = false)String username,
                            @RequestParam(value = "uId",required = false)String uId,
                            @RequestParam(value = "aIdentity",required = false)String aIdentity)
    {
        User user = new User();
        user.setuId(uId);
        user.setuName(username);
        Map<String, String> userInfoMap = userService.getUserInfoMap(user);
        Authority authority = new Authority();
        authority.setuId(uId);
        authority.setaIdentity(aIdentity);
        Map<String, String> userAuthMap = userService.getUserAuthMap(authority);
        List<UserListDto> list=new ArrayList<>();
        userInfoMap.forEach((key,value)->{
            String auth = userAuthMap.get(key);
            UserListDto userListDto = new UserListDto();
            userListDto.setuId(key).setuId(value).setaIdentify(auth);
            list.add(userListDto);
        });
        return new Msg().setTotal(list.size())
                .setMsg("查询成功")
                .setItems(list)
                .setState(STATE.Success.getState());
    }



}
