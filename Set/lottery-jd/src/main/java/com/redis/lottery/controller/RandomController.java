package com.redis.lottery.controller;

import com.redis.lottery.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author cxks
 * @Date 2021/8/17 23:27
 */
@RestController
@Slf4j
@RequestMapping(value = "/random")
public class RandomController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping(value = "/prize")
    public String prize() {
        String result="";
        try {
            //随机取1次。
            String object = (String)this.redisTemplate.opsForSet().randomMember(Constants.PRIZE_KEY);
            if (!StringUtils.isEmpty(object)){
                //截取序列号 例如10-1
                int temp=object.indexOf('-');
                int no=Integer.valueOf(object.substring(0,temp));
                switch (no){
                    case 0:
                        result="谢谢参与";
                        break;
                    case 1:
                        result="获得1个京豆";
                        break;
                    case 5:
                        result="获得5个京豆";
                        break;
                    case 10:
                        result="获得10个京豆";
                        break;
                    default:
                        result="谢谢参与";
                }
            }
            log.info("查询结果：{}", object);
        } catch (Exception ex) {
            log.error("exception:", ex);
        }
        return result;
    }


}
