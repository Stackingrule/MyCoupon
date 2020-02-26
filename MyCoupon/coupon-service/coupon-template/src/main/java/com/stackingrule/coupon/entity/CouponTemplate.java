package com.stackingrule.coupon.entity;

import com.stackingrule.coupon.constant.CouponCategory;
import com.stackingrule.coupon.constant.DistributeTarget;
import com.stackingrule.coupon.constant.ProductLine;
import com.stackingrule.coupon.vo.TemplateRule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <h1>优惠券模板实体类定义: 基础属性 + 规则属性</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon_template")
public class CouponTemplate implements Serializable {

    /** 自增主键 **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /** 是否为可用状态 **/
    @Column(name = "available", nullable = false)
    private Boolean available;

    /** 是否过期 **/
    @Column(name = "expired", nullable = false)
    private Boolean expired;

    /** 优惠券名称 **/
    @Column(name = "name", nullable = false)
    private String name;

    /** 优惠券logo **/
    @Column(name = "logo", nullable = false)
    private String logo;

    /** 优惠券描述 **/
    @Column(name = "intro", nullable = false)
    private String desc;

    /** 优惠券分类 **/
    @Column(name = "category", nullable = false)
    private CouponCategory category;

    /** 产品线 **/
    @Column(name = "productLine", nullable = false)
    private ProductLine productLine;

    /** 总数 **/
    @Column(name = "coupon_count", nullable = false)
    private Integer count;

    /** 创建时间 **/
    @CreatedDate
    @Column(name = "createTime", nullable = false)
    private Date createTime;

    /** 创建用户 **/
    @Column(name = "userId", nullable = false)
    private Long userId;

    /** 优惠券模板的编码 **/
    @Column(name = "template_key", nullable = false)
    private String key;

    /** 目标用户 **/
    @Column(name = "target", nullable = false)
    private DistributeTarget target;

    /** 优惠券规则 **/
    @Column(name = "rule", nullable = false)
    private TemplateRule rule;

    public CouponTemplate(String name, String logo,
                          String desc, String category,
                          Integer productLine, Integer count,
                          Long userId, Integer target,
                          TemplateRule rule) {
        this.available = false;
        this.expired = false;
        this.name = name;
        this.logo = logo;
        this.desc = desc;
        this.category = CouponCategory.of(category);
        this.productLine = ProductLine.of(productLine);
        this.count = count;
        this.userId = userId;
        // 优惠券唯一编码 = 4(产品线类型) + 8（日期：20200226）+ id（扩充为四位）
        this.key = productLine.toString() + category
                + new SimpleDateFormat("yyyyMMdd").format(new Date());
        this.rule = rule;
    }
}
