package com.redis.controller;

import com.redis.entity.Product;
import com.redis.util.ToolsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.tools.Tool;
import java.util.Map;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author cxks
 * @Date 2021/8/8 22:45
 */
@RestController
@Slf4j
public class TestController {
    private final RedisTemplate redisTemplate;

    public TestController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

//    @RequestMapping("/test")
//    public void redisString(){
//        String key = "id:1";
//        long num =
//                redisTemplate.opsForValue().increment(key);
//        log.info("key值为{}的value值为{}",key,num);
//    }

    /**
     * 新增商品
     * @param obj
     * @throws Exception
     */
    @RequestMapping("/product")
    public void redisHash(Product obj) throws Exception {
        String key = "product:" + obj.getId();
        // 将商品对象转化为Map对象
        Map<String, Object> map = ToolsUtils.objectToMap(obj);
        log.info("map {}",map);
        // 批量put操作，putAll等于hmset命令
        // string的数据结构opsForValue hash数据结构opsForHash
        redisTemplate.opsForHash().putAll(key,map);
        Object name = redisTemplate.opsForHash().get(key,"name");
        log.info("name={}",name);
        Object price = redisTemplate.opsForHash().get(key,"price");
        log.info("price={}",price);
        Object detail = redisTemplate.opsForHash().get(key,"description");
        log.info("detail={}",detail);
    }

    /**
     * 商品涨价
     * @param id
     * @param price
     */
    @RequestMapping("/addprice")
    public void addPrice(int id,int price){
        String key = "product:" + id;
        // 对商品中的价格进行修改（增加）
        redisTemplate.opsForHash().increment(key,"price",price);
        Object price2 = redisTemplate.opsForHash().get(key,"price");
        log.info("price={}",price2);
    }


}
