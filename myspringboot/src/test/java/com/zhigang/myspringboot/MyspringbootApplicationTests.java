package com.zhigang.myspringboot;

import com.zhigang.myspringboot.app.MyspringbootApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyspringbootApplication.class)
@Rollback(value = false)
@Transactional
@Slf4j
public class MyspringbootApplicationTests {

    @Before
    public void init() {
        log.info("start my test!");
        System.out.println("开始测试-----------------");
    }

    @After
    public void after() {
        log.info("end my test");
        System.out.println("测试结束-----------------");
    }

}

