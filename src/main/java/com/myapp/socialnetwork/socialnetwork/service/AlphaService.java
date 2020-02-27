package com.myapp.socialnetwork.socialnetwork.service;

import com.myapp.socialnetwork.socialnetwork.DAO.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
//@Scope("prototype")
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;

    public AlphaService() {
        System.out.println("example service");
    }
    @PostConstruct
    public void init() {
        System.out.println("init service");
    }
    @PreDestroy
    public void destroy() {
        System.out.println("destroy service");
    }
    public String find() {
        return alphaDao.select();
    }
}
