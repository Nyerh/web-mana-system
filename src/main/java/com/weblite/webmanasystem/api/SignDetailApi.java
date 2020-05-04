package com.weblite.webmanasystem.api;

import com.weblite.webmanasystem.constant.*;
import com.weblite.webmanasystem.domain.dto.UserSignDto;
import com.weblite.webmanasystem.domain.entity.SignDetail;
import com.weblite.webmanasystem.service.SignDetailService;
import com.weblite.webmanasystem.service.UserService;
import com.weblite.webmanasystem.utils.TranseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author ：Beatrice
 * @date ：Created in 2020/2/15 10:41
 * @Description:
 */
@RestController
@Api(description = "签到记录管理接口")
@RequestMapping("signDetail")
public class SignDetailApi {

    @Resource
    SignDetailService signDetailService;
    @Resource
    UserService userService;

    @GetMapping("/showDetails")
    @ApiOperation("展示签到信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "信息记录编号"),
            @ApiImplicitParam(name = "uId", dataType = "String", value = "用户id"),
            @ApiImplicitParam(name = "page", dataType = "Integer", value = "当前页"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer", value = "页面数据量"),
            @ApiImplicitParam(name = "curUId", dataType = "String", value = "当前登录用户uid")
    })
    public Msg showDetails(@RequestParam(value = "id",required = false)Integer id,
                           @RequestParam(value = "uId",required = false)String uId,
                           @RequestParam(value = "page",required = false)Integer page,
                           @RequestParam(value = "pageSize",required = false)Integer pageSize,
                           @RequestParam(value = "uId",required = false) String curUId)
    {
        String authByUId = userService.getAuthByUId(curUId);
        String s = Optional.ofNullable(authByUId).orElse("");
        if(!curUId.equals(uId) &&!s.equals("管理员"))
        {
            return new Msg()
                    .setMsg("权限不足")
                    .setState(STATE.PermissionDenied.getState());
        }
        UserSignDto userSignDto = new UserSignDto().setId(id).setuId(uId);
        List<UserSignDto> userSignList = signDetailService.getUserSignList(userSignDto, page, pageSize);
        Integer count = signDetailService.count(userSignDto);
        return new Msg().setItems(userSignList)
                .setTotal(count)
                .setMsg("获取成功")
                .setState(STATE.Success.getState());
    }

    @GetMapping("/cancelSign")
    @ApiOperation("删除签到记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "记录id"),
            @ApiImplicitParam(name = "curUId", dataType = "String", value = "当前登录用户uid"),
    })
    public Msg cancelSign(@RequestParam("id")Integer id,
                          @RequestParam("curUId") String curUId)
    {
        String authByUId = userService.getAuthByUId(curUId);
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


    @PostMapping("/exportSignDetail")
    @ApiOperation("导出所选签到记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "signDetailList", dataType = "List<Integer>", value = "需要导出的记录id列表"),
    })
    public Msg exportSignDetail(List<Integer> signDetailList, HttpServletResponse httpServletResponse)
    {
        if(signDetailList==null||signDetailList.size()==0)
        {
            return new Msg().setState(STATE.ParamErr.getState())
                    .setMsg("未选择记录，不允许导出");
        }
        List<SignDetail> signInfos = signDetailService.getSignDetailList(signDetailList);
        List<Map<String, Object>> maps = SignDetailService.convertObjToMap(signInfos);
        ExcelWriterHelper helper = new ExcelWriterHelper();
        List<ExcelWriteSheetFormat> excelWriteSheetFormatList = new ArrayList<>();
        ExcelWriteSheetFormat excelWriteSheetFormat = new ExcelWriteSheetFormat();
        excelWriteSheetFormat.setSheetName("签到记录表");
        excelWriteSheetFormat.setDataMapList(maps);
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put("id","编号");
        headerMap.put("uId","用户编号");
        headerMap.put("signTime","签到时间");
        excelWriteSheetFormat.setHeaderMap(headerMap);
        excelWriteSheetFormatList.add(excelWriteSheetFormat);
        helper.exportByMap(excelWriteSheetFormatList,"签到明细"+ Common.getNowDateString("YYYYMMdd"), httpServletResponse);
        return new Msg().setMsg("导出成功")
                .setState(STATE.Success.getState());
    }


    @PostMapping("importDetail")
    @ApiOperation("接收APP传入签到信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uId", dataType = "String", value = "用户编号"),
            @ApiImplicitParam(name = "signTime", dataType = "String", value = "签到时间")
    })
    public Msg appImportSignDetail(@RequestBody Map<String,String> map) throws Exception {
        if(map==null)
        {
            return new Msg(STATE.ParamErr.getState(),"无参数");
        }
        String uId = map.get("uId");
        if(StringUtils.isEmpty(uId))
        {
            return new Msg(STATE.ParamErr.getState(),"无用户编码");
        }
        String signTime = map.get("signTime");
        if(StringUtils.isEmpty(signTime))
        {
            return new Msg(STATE.ParamErr.getState(),"无签到时间");
        }
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
        try {
            Date parse = format.parse(signTime);
            Integer save = signDetailService.saveSignDetail(uId, parse);
            if(save==null||save==0)
            {
                throw new Exception("保存失败");
            }
        }catch (Exception e)
        {
            throw new Exception("写入时出错"+e.getMessage());
        }
        return new Msg(STATE.Success.getState(),"保存签到信息成功");
    }
}
