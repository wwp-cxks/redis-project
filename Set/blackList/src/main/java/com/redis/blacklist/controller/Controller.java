package com.redis.blacklist.controller;

import com.redis.blacklist.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author cxks
 * @Date 2021/8/17 22:42
 */
@RestController
@Slf4j
public class Controller {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *编写黑名单校验器接口
     * true=黑名单
     * false=不是黑名单
     */
    @GetMapping(value = "/isBlacklist")
    public boolean isBlacklist(Integer userId) {
        boolean bo=false;
        try {
            //到set集合中去校验是否黑名单，
            bo = this.redisTemplate.opsForSet().isMember(Constants.BLACKLIST_KEY,userId);
            log.info("查询结果：{}", bo);
        } catch (Exception ex) {
            //这里的异常，一般是redis瘫痪 ，或 redis网络timeout
            log.error("exception:", ex);
            //TODO 走DB查询
        }
        return bo;
    }

}

