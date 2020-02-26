package com.stackingrule.coupon.dao;

import com.stackingrule.coupon.entity.CouponTemplate;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <h1>CouponTemplateDao 接口定义</h1>
 */
public interface CouponTemplateDao
        extends JpaRepository<CouponTemplate, Integer> {

    /**
     * <h2>根据模板名称查询模板</h2>
     * where name = ...
     * @param name
     * @return
     */
    CouponTemplate findByName(String name);

    /**
     * <h2>根据 available 和 expired 标记查找模板记录</h2>
     * @param available
     * @param expired
     * @return
     */
    List<CouponTemplate> findAllByAvailableAAndExpired(
            Boolean available, Boolean expired
    );

    /**
     * <h2>根据 expired 标记查找模板记录</h2>
     * @param expired
     * @return
     */
    List<CouponTemplate> findAllByExpired(Boolean expired);


}
