package com.stackingrule.coupon.service;

import com.stackingrule.coupon.entity.CouponTemplate;
import com.stackingrule.coupon.exception.CouponException;
import com.stackingrule.coupon.vo.CouponTemplateSDK;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <h1>优惠券模板基础服务定义</h1>
 */
public interface ITemplateBaseService {

    /**
     * <h2>根据优惠券模板 id 获取优惠券模板信息</h2>
     * @param id 模板 id
     * @return {@link CouponTemplate}
     * @throws CouponException
     */
    CouponTemplate buildTemplateInfo(Integer id) throws CouponException;


    /**
     * <h2>查找所有可用的优惠券模板</h2>
     * @return {@link CouponTemplateSDK}
     */
    List<CouponTemplateSDK > findAllUsableTemplate();

    /**
     * <h2>获取模板ids到CooponTemplateSDK的映射</h2>
     * @param ids 模板 ids
     * @return Map<key: 模板 id, value: CouponTemplateSDK>
     */
    Map<Integer, CouponTemplateSDK> findIds2TemplateSDK(Collection<Integer> ids);

}
