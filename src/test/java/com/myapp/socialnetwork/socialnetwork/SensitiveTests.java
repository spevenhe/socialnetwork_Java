package com.myapp.socialnetwork.socialnetwork;

import com.myapp.socialnetwork.socialnetwork.util.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = SocialNetworkApplication.class)
public class SensitiveTests {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void setSensitiveFilterTest() {
        String text = "国家主席可以抽大麻可以嫖娼也可以赌博";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }
}
