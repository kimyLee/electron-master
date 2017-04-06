package edu.scut.controller;

import edu.scut.dao.CostMapper;
import edu.scut.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weirilong on 2016/5/25.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public Object addUser(@RequestBody Map<String, Object> model) throws Exception {
        userMapper.insert(model);
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        return resut;
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @ResponseBody
    public Object updateUser(@RequestBody Map<String, Object> model) throws Exception {
        userMapper.update(model);
        Map<String, Object> resut = new HashMap<String, Object>();
        resut.put("ret", 0);
        resut.put("msg", "请求已处理");
        return resut;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    @ResponseBody
    public Object authUser(@RequestBody Map<String, String> user) throws Exception {
        String pass = userMapper.selectByUser(user.get("username"));
        Map<String, Object> resut = new HashMap<String, Object>();
        if(pass != null && pass.equals(user.get("password"))) {
            resut.put("ret", 0);
            resut.put("msg", "登陆成功");
        } else {
            resut.put("ret", 1);
            resut.put("msg", "用户名或密码错误");
        }

        return resut;
    }

}
