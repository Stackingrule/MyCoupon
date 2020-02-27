package com.stackingrule.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>微服务间用的优惠券模板信息定义</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponTemplateSDK {

    /** 优惠券模板主键 **/
    private Integer id;

    /** 优惠券模板名称 **/
    private String name;

    /** 优惠券logo **/
    private String logo;

    /** 优惠券描述 **/
    private String desc;

    /** 优惠券分类 **/
    private String code;

    /** 产品线 **/
    private Integer productLine;

    /** 优惠券模板编码 **/
    private String key;

    /** 优惠券目标用户 **/
    private Integer target;

    /** 优惠券规则 **/
    private TemplateRule rule;

}
