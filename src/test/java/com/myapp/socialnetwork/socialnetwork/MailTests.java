package com.myapp.socialnetwork.socialnetwork;

import com.myapp.socialnetwork.socialnetwork.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.sql.SQLOutput;

@SpringBootTest
@ContextConfiguration(classes = SocialNetworkApplication.class)
public class MailTests {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail() {
        mailClient.sendMail("1169322148@qq.com","Test","wish not in the trash");
    }

    @Test
    public void testHtmlMail() {
        Context context = new Context();
        context.setVariable("username", "bot");
        String content = templateEngine.process("/mail/demo",context);
        System.out.println(content);

        mailClient.sendMail("1169322148@qq.com","html","wish not in the trash");
    }


}
