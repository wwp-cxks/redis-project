package com.redis.entity;

import lombok.Data;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author cxks
 * @Date 2021/8/9 0:07
 */
@Data
public class Product {
    private int id;
    private String name;
    private String description;
    private int price;
}
