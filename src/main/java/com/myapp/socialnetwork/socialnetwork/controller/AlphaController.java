package com.myapp.socialnetwork.socialnetwork.controller;

import com.myapp.socialnetwork.socialnetwork.DAO.AlphaDao;
import com.myapp.socialnetwork.socialnetwork.service.AlphaService;
import com.myapp.socialnetwork.socialnetwork.util.SocialnetworkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Path;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {
    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "  Hello Spring Boot";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        // 获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        request.getHeaderNames();
        Enumeration<String>enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ": " + value);
        }

        System.out.println(request.getParameter("code"));

        // response
        response.setContentType("text/html;charset=utf-8");
        try (
                PrintWriter writer = response.getWriter();

                ){
            writer.write("<h1>myapp</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Get 请求

    // /students？current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(@RequestParam(name = "current", required = false, defaultValue = "1") int current,
                              @RequestParam(name = "limit", required = false, defaultValue = "10")int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // /student/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }

    // POST
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, String age) {
        System.out.println(name);
        try {
            int ageNumber = Integer.valueOf(age);
            System.out.println(age);
        } catch (NumberFormatException e){
            return "invalid number";
        }

        return "success";
    }

    // response html data
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView mAv = new ModelAndView();
        mAv.addObject("name", "zhang3");
        mAv.addObject("age", 300);
        mAv.setViewName("/demo/view");
        return mAv;
    }

    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name", "peking");
        int[] temp = {1,2,3,4};
        model.addAttribute("age",temp);
        return "/demo/view";
    }

    // response Json (异步）
    // Java对象 -> Json字符串 -> js对象
    @RequestMapping(path = "emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getEmps() {
        List<Map<String,Object>>emps = new ArrayList<>();
        Map<String,Object>emp = new HashMap<>();
        emp.put("name","zhang3");
        emp.put("age", 22);
        emp.put("salary","8000");
        emps.add(emp);
        Map<String,Object>emp2 = new HashMap<>();
        emp2.put("name","zhang4");
        emp2.put("age", 23);
        emp2.put("salary","10000");
        emps.add(emp2);
        return emps;
    }

    //cookie example
    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response) {
        // 创建cookie
        Cookie cookie = new Cookie("code", SocialnetworkUtil.generateUUID());
        // 设置cookie生效范围
        cookie.setPath("/socialnetwork/alpha");
        // 设置cookie生存时间
        cookie.setMaxAge(60*10);
        // 发送cookie
        response.addCookie(cookie);
        return "set cookie";
    }
    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code) {
        System.out.println(code);
        return "get cookie";
    }
    // session示例
    @RequestMapping(path = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session) {
        session.setAttribute("id",1);
        session.setAttribute("name","Test");
        return "set session";
    }

    @RequestMapping(path = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session) {
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }
    // ajax
    @RequestMapping(path = "/ajax", method = RequestMethod.POST)
    @ResponseBody
    public String testAjax(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return SocialnetworkUtil.getJSONString(0,"operate successfully");
    }

}
