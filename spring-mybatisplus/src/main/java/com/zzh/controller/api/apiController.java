package com.zzh.controller.api;

import com.zzh.comom.ResultDto;
import com.zzh.consts.ResultConstant;
import com.zzh.controller.BaseController;
import com.zzh.model.system.User;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuhao on 2018/2/5
 */
@RestController
@Api(value="用户controller",description="用户相关操作")
@RequestMapping(value = "api")
public class apiController extends BaseController {

    @RequestMapping(value = "test", method = RequestMethod.GET)
    @ApiOperation(value = "test", notes = "测试1")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "String", paramType = "query")
    public Object getIndex1(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name","朱昊");
        map.put("date", new Date());
        return render(ResultConstant.SUCCESS, map);
    }

    @RequestMapping(value = "test", method = RequestMethod.POST)
    @ApiOperation(value = "test", notes = "测试2")
    @ApiImplicitParam(name = "user", value = "user", required = true, dataType = "User", paramType = "body")
    public Object getIndex(@RequestBody User user) {
        return render(ResultConstant.SUCCESS, user);
    }

    @ApiOperation(value = "创建", notes = "创建")
    @ApiImplicitParam(name = "user", value = "创建", required = true, dataType = "User")
    @RequestMapping(method = RequestMethod.POST)
    public ResultDto release(@RequestBody User user) {
        return render(ResultConstant.SUCCESS, user);
    }


    @ApiOperation(value = "创建", notes = "创建")
    @RequestMapping(value = "/release", method = RequestMethod.POST)
    public ResultDto newApp(@ApiParam(name = "app", value = "产品临时对象", required = true) @RequestBody User user) {
        return render(ResultConstant.SUCCESS, user);
    }


    @ApiOperation(value = "应用查询", notes = "应用查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "appType",    dataType = "String",  required = true,  value = "应用类型", defaultValue = "10"),
            @ApiImplicitParam(paramType = "query", name = "appClassId", dataType = "String",  required = true,  value = "应用分类id"),
            @ApiImplicitParam(paramType = "query", name = "appId",      dataType = "String",  required = true,  value = "appId"),
            @ApiImplicitParam(paramType = "query", name = "appName",    dataType = "String",  required = false, value = "应用名称"),
            @ApiImplicitParam(paramType = "query", name = "appStatus",  dataType = "String",  required = true,  value = "状态 0：已下架 1：正常"),
            @ApiImplicitParam(paramType = "query", name = "page",       dataType = "integer", required = true,  value = "当前页码"),
            @ApiImplicitParam(paramType = "query", name = "rows",       dataType = "integer", required = true,  value = "每页条数")
    })
    @RequestMapping(method = RequestMethod.GET)
    public ResultDto list(@RequestParam("appType") String appType, @RequestParam("appClassId") String appClassId,
                          @RequestParam("appId") String appId, @RequestParam("appName") String appName,
                          @RequestParam("appStatus") String appStatus, @RequestParam(name = "page", defaultValue = "1") int page,
                          @RequestParam(name = "rows", defaultValue = "10") int rows) {
        return render(ResultConstant.SUCCESS);
    }


    @ApiOperation(value = "获取产品详情", notes = "产品详情")
    @ApiImplicitParam(name = "appId", value = "产品appId", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{appId}", method = RequestMethod.GET)
    private ResultDto getAppDetail(@PathVariable("appId") String appId) {
        return render(ResultConstant.SUCCESS, appId);
    }


    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "appId", value = "应用ID", paramType = "path", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user",  value = "用户实体", required = true, dataType = "User")
    })
    @RequestMapping(value = "/{appId}", method = RequestMethod.POST)
    public ResultDto Appupdate(@PathVariable String appId, @RequestBody User user) {
        //todo
        return render(ResultConstant.SUCCESS);
    }

    @ApiOperation(value = "创建多个User", notes = "创建多个User")
    @RequestMapping(value = "/postApps", method = RequestMethod.POST)
    public ResultDto postApps(@ApiParam(name = "users", value = "用户", required = true) @RequestBody List<User> users) {
        System.out.print(users);
        return render(ResultConstant.SUCCESS, users);
    }

}
