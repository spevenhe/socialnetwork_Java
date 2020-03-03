package com.myapp.socialnetwork.socialnetwork.controller;

import com.myapp.socialnetwork.socialnetwork.entity.User;
import com.myapp.socialnetwork.socialnetwork.service.UserService;
import com.myapp.socialnetwork.socialnetwork.util.SocialnetworkConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class LoginController implements SocialnetworkConstant {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "/site/register";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage() {  return "/site/login"; }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(Model model, User user) { //如果传入参数与user的属性相匹配，就会自动传入user
        Map<String, Object> map = userService.register(user);
        if (map== null || map.isEmpty()) {
            model.addAttribute("msg", "注册成功， activation email was sent, 请激活");
            model.addAttribute("target", "/index");
            return "/site/operate-result";
        }
        else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }
    }

    // http://localhost:8000/socialnetwork/activation/101/code
    @RequestMapping(path = "/activation/{userId}/{code}", method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userId,
                             @PathVariable("code") String code) {
        int result = userService.activation(userId,code);
        if(result == ACTIVATION_SUCCESS) {
            model.addAttribute("msg", "激活成功， 账号可使用");
            model.addAttribute("target", "/login");
        }else if(result == ACTIVATION_REPEAT) {
            model.addAttribute("msg", "无效，已经激活过了");
            model.addAttribute("target", "/index");
        }else if(result == ACTIVATION_FAILURE) {
            model.addAttribute("msg", "激活失败，激活码不正确");
            model.addAttribute("target", "/index");
        }
        return "/site/operate-result";
    }



}
