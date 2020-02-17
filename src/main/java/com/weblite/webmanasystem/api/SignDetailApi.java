package com.weblite.webmanasystem.api;

import com.weblite.webmanasystem.constant.Msg;
import com.weblite.webmanasystem.constant.STATE;
import com.weblite.webmanasystem.domain.dto.UserSignDto;
import com.weblite.webmanasystem.service.SignDetailService;
import com.weblite.webmanasystem.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author ：Beatrice
 * @date ：Created in 2020/2/15 10:41
 * @Description:
 */
@RestController
@RequestMapping("signDetail")
public class SignDetailApi {

    @Resource
    SignDetailService signDetailService;
    @Resource
    UserService userService;




    @RequestMapping("showDetails")
    @ApiOperation("展示签到信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "记录id"),
            @ApiImplicitParam(name = "uId", dataType = "String", value = "用户id"),
            @ApiImplicitParam(name = "page", dataType = "Integer", value = "当前页"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer", value = "页面数据量"),
            @ApiImplicitParam(name = "curUId", dataType = "String", value = "当前登录用户uid")
    })
    public Msg showDetails(@RequestParam("id")Integer id,
                           @RequestParam("uId")String uId,
                           @RequestParam("page")Integer page,
                           @RequestParam("pageSize")Integer pageSize,
                           @RequestParam("uId") String curUId)
    {
        String authByUId = userService.getAuthByUId(curUId);
        String s = Optional.ofNullable(authByUId).orElseGet(() -> "");
        if(!s.equals("管理员"))
        {
            return new Msg()
                    .setMsg("权限不足")
                    .setState(STATE.PermissionDenied.getState());
        }
        UserSignDto userSignDto = new UserSignDto().setId(id).setuId(uId);
        List<UserSignDto> userSignList = signDetailService.getUserSignList(userSignDto, page, pageSize);
        return new Msg().setItems(userSignList)
                .setMsg("获取成功")
                .setState(STATE.Success.getState());
    }

    @RequestMapping("cancelSign")
    @ApiOperation("删除签到记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "记录id"),
            @ApiImplicitParam(name = "uId", dataType = "String", value = "当前登录用户uid"),
    })
    public Msg cancelSign(@RequestParam("id")Integer id,
                          @RequestParam("uId") String uId)
    {
        String authByUId = userService.getAuthByUId(uId);
        String s = Optional.ofNullable(authByUId).orElseGet(() -> "");
        if(!s.equals("管理员"))
        {
            return new Msg()
                    .setMsg("权限不足")
                    .setState(STATE.PermissionDenied.getState());
        }

        Integer cancel = signDetailService.cancelSign(id);
        if(cancel==0)
        {
            return new Msg()
                    .setMsg("删除失败")
                    .setState(STATE.ParamErr.getState());
        }
        return null;
    }


}
