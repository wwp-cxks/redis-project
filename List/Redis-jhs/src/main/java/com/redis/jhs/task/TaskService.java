package com.redis.jhs.task;


import com.redis.jhs.entity.Constants;
import com.redis.jhs.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description: TODO(定时器)
 * @Author cxks
 * @Date 2021/8/12 23:53
 */
@Service
@Slf4j
public class TaskService {
//    @Autowired
//    private RedisTemplate redisTemplate;

    private final RedisTemplate redisTemplate;

    public TaskService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    @PostConstruct
    public void initJHS(){
        log.info("启动定时器......");
        new Thread(()->runJhs()).start();
    }

    /**
     * 模拟定时器，定时把数据库的特价商品，刷新到redis中
     */
    public void runJhs() {
        while (true){
            //模拟从数据库读取100件特价商品，用于加载到聚划算的页面中
            List<Product> list=this.products();
            //采用redis list数据结构的lpush来实现存储
            redisTemplate.delete(Constants.JHS_KEY);
            //lpush命令
            redisTemplate.opsForList().leftPushAll(Constants.JHS_KEY,list);
            try {
                //间隔一分钟 执行一遍
                Thread.sleep(1000*60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("runJhs定时刷新..............");
        }
    }

    /**
     * 模拟从数据库读取100件特价商品，用于加载到聚划算的页面中
     */
    public List<Product> products() {
        List<Product> list= new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Random rand = new Random();
            int id= rand.nextInt(10000);
            Product obj=new Product((long) id,"product"+i,i,"detail");
            list.add(obj);
        }
        return list;
    }
}
