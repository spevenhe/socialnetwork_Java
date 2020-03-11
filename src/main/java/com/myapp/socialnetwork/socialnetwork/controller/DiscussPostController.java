package com.myapp.socialnetwork.socialnetwork.controller;
import com.myapp.socialnetwork.socialnetwork.annotation.LoginRequired;
import com.myapp.socialnetwork.socialnetwork.entity.DiscussPost;
import com.myapp.socialnetwork.socialnetwork.entity.User;
import com.myapp.socialnetwork.socialnetwork.service.DiscussPostService;
import com.myapp.socialnetwork.socialnetwork.util.HostHolder;
import com.myapp.socialnetwork.socialnetwork.util.SocialnetworkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/discuss")
public class DiscussPostController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title, String content) {
        User user = hostHolder.getUser();
        if (user == null) {
            return SocialnetworkUtil.getJSONString(403, "你还没有登录");
        }
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());
        discussPostService.addDiscussPost(post);

        // 将来统一处理exception
        return SocialnetworkUtil.getJSONString(0, "success");
    }
}
