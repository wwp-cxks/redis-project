package com.redis.lottery.controller;

import com.redis.lottery.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author cxks
 * @Date 2021/8/17 23:49
 */
@RestController
@Slf4j
@RequestMapping(value = "/random")
public class RandomController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping(value = "/prize")
    public List<Integer> prize(int num) {
        try {
            SetOperations<String, Integer> setOperations= this.redisTemplate.opsForSet();
            //spop命令，即随机返回并删除set中一个元素
            List<Integer> objs = setOperations.pop(Constants.PRIZE_KEY,num);
            log.info("查询结果：{}", objs);
            return  objs;
        } catch (Exception ex) {
            log.error("exception:", ex);
        }
        return null;
    }
}

