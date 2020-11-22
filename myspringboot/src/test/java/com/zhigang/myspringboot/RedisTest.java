package com.zhigang.myspringboot;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * <一句话功能描述>
 *
 * @Author admin
 * @Since 2020/11/15 17:56
 */
public class RedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        System.out.println("connect successfully");
        jedis.getClient();
        // 如果设置了认证，就需要认证一下
        // jedis.auth("mypassword");
        System.out.println("Server is running: "+jedis.ping());
        // push值
        jedis.lpush("hello", "wy","hhhhh","licon","jsp");
        // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
        List<String> list = jedis.lrange("hello",0,5);
        list.forEach(s -> System.out.println("value:"+s));
    }
}
