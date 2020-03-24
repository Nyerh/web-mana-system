package com.weblite.webmanasystem.api;

import com.weblite.webmanasystem.constant.Msg;
import com.weblite.webmanasystem.constant.STATE;
import com.weblite.webmanasystem.utils.TranseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：Beatrice
 * @date ：Created in 2020/3/24 15:37
 * @Description:
 */
@RestController
@Api(description = "BV号转换ApiDemo1")
@RequestMapping("transDemo")
public class BVTransApi {


    @GetMapping("/transBVId")
    @ApiOperation("转换测试")
    @ApiImplicitParam(name = "bvId",dataType = "string",value = "BV号")
    public Msg test(String bvId)
    {
        TranseUtil transeUtil=new TranseUtil();
        Long aLong = transeUtil.transeBVToAv(bvId);
        return new Msg(STATE.Success.getState(),"获取AV号为：av"+aLong);
    }

}
