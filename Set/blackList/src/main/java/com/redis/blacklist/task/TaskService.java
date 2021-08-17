package com.redis.blacklist.task;

import com.redis.blacklist.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author cxks
 * @Date 2021/8/17 22:42
 */
@Service
@Slf4j
public class TaskService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 提前先把数据刷新到redis缓存中
     */
    @PostConstruct
    public void init(){
        log.info("启动初始化 ..........");
        List<Integer> blacklist=this.blacklist();
        //this.redisTemplate.delete(Constants.BLACKLIST_KEY);
        blacklist.forEach(t->this.redisTemplate.opsForSet().add(Constants.BLACKLIST_KEY,t));
    }

    /**
     * 模拟100个黑名单
     */
    public List<Integer> blacklist() {
        List<Integer> list=new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        return list;
    }
}

