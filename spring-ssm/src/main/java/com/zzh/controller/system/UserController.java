package com.zzh.controller.system;

import com.zzh.consts.ResultConstant;
import com.zzh.controller.BaseController;
import com.zzh.model.enums.TypeEnum;
import com.zzh.model.system.User;
import com.zzh.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Author: D.Yang
 * Email: koyangslash@gmail.com
 * Date: 16/10/9
 * Time: 上午11:58
 * Describe: 用户控制器
 *
 * 代码生成器，参考源码测试用例：
 *
 * /mybatis-plus/src/test/java/com/baomidou/mybatisplus/test/generator/MysqlGenerator.java
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        modelAndView.addObject("userList", userService.selectList(null));
        return modelAndView;
    }

    @RequestMapping("/preSave")
    public ModelAndView preSave(ModelAndView modelAndView, @RequestParam(value = "id", required = false) Long id) {
        modelAndView.setViewName("save");
        if (id != null) {
            modelAndView.addObject("user", userService.selectById(id));
        }
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("save")
    public Object save(User user) {
        user.setType(TypeEnum.DISABLED);
        if (user.getId() == null) {
            return userService.insert(user) ? render(ResultConstant.SUCCESS) : render(ResultConstant.FAILED);
        } else {
            return userService.updateById(user) ? render(ResultConstant.SUCCESS) : render(ResultConstant.FAILED);
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(@RequestParam(value = "id", required = false) Long id) {
        return userService.deleteById(id) ? render(ResultConstant.SUCCESS) : render(ResultConstant.FAILED);
    }


}