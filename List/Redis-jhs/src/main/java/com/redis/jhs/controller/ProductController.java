package com.redis.jhs.controller;

import com.redis.jhs.entity.Constants;
import com.redis.jhs.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author cxks
 * @Date 2021/8/13 0:40
 */
@RestController
@Slf4j
@RequestMapping(value = "/pruduct")
public class ProductController {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 分页查询：在高并发的情况下，只能走redis查询，走db的话必定会把db打垮
     */
    @GetMapping(value = "/find")
    public List<Product> find(int page, int size) {
        List<Product> list=null;
        long start = (page - 1) * size;
        long end = start + size - 1;
        try {
            //采用redis list数据结构的lrange命令实现分页查询
            list = redisTemplate.opsForList().range(Constants.JHS_KEY, start, end);
            if (CollectionUtils.isEmpty(list)) {
                //TODO 走DB查询
            }
            log.info("查询结果：{}", list);
        } catch (Exception ex) {
            //这里的异常，一般是redis瘫痪 ，或 redis网络timeout
            log.error("exception:", ex);
            //TODO 走DB查询
        }
        return list;
    }
}

