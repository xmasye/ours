package com.xmasye.ours.controller;

import com.xmasye.ours.dao.mybatis.TestDao;
import com.xmasye.ours.dao.po.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestDao testDao;

    @RequestMapping("first")
    public Test insertValue(int value) {
        Test test = new Test();
        test.setId(11);
        testDao.insert(value);
        return test;
    }

}
