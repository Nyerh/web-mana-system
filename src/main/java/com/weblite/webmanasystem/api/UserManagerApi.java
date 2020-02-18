package com.weblite.webmanasystem.api;

import com.weblite.webmanasystem.constant.Msg;
import com.weblite.webmanasystem.constant.STATE;
import com.weblite.webmanasystem.domain.dto.UserInfoDto;
import com.weblite.webmanasystem.service.SignDetailService;
import com.weblite.webmanasystem.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：Beatrice
 * @date ：Created in 2020/2/15 10:40
 * @Description:
 */
@RestController
@RequestMapping("userManager")
public class UserManagerApi {
    @Resource
    SignDetailService signDetailService;
    @Resource
    UserService userService;


    @PostMapping("userLogin")
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


    @PostMapping("userRegister")
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



}
