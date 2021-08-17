package com.redis.jhs.entity;

import lombok.Data;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author cxks
 * @Date 2021/8/13 0:10
 */
@Data
public class Product {

    private Long id;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品价格
     */
    private Integer price;
    /**
     * 产品详情
     */
    private String detail;

    public Product() {
    }

    public Product(Long id, String name, Integer price, String detail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.detail = detail;
    }
}

