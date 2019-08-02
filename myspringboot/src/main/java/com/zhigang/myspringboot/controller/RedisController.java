package com.zhigang.myspringboot.controller;

import com.zhigang.myspringboot.system.configuration.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/2 12:03
 * @Version 3.2.2
 **/
@Slf4j
@RequestMapping("/redis")
@RestController
public class RedisController {

    private static int ExpireTime = 60;   // redis中存储的过期时间60s

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("set")
    public boolean redisset(@RequestParam("key") String key, @RequestParam("value") String value){

        //return redisUtil.set(key,userEntity,ExpireTime);

        return redisUtil.set(key,value);
    }

    @RequestMapping("get")
    public Object redisget(@RequestParam("key") String key){
        return redisUtil.get(key);
    }

    @RequestMapping("expire")
    public boolean expire(@RequestParam("key") String key){
        return redisUtil.expire(key,ExpireTime);
    }
}
