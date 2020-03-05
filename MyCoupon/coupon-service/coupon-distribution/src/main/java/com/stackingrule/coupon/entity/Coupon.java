package com.stackingrule.coupon.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stackingrule.coupon.constant.CouponStatus;
import com.stackingrule.coupon.converter.CouponStatusConverter;
import com.stackingrule.coupon.serialization.CouponSerialize;
import com.stackingrule.coupon.vo.CouponTemplateSDK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * <h1>优惠券（用户领取的优惠券记录）实体表</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon")
@JsonSerialize(using = CouponSerialize.class)
public class Coupon {

    /** 自增主键 **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 关联优惠券模板的主键（逻辑外键） **/
    @Column(name = "templateId")
    private Integer templateId;

    /** 领取用户 **/
    @Column(name = "userId", nullable = false)
    private Long userId;

    /** 优惠券码 **/
    @Column(name = "couponCode", nullable = false)
    private String couponCode;

    /** 领取时间 **/
    @CreatedDate
    @Column(name = "assignTime", nullable = false)
    private Date assignTime;

    /** 优惠券状态 **/
    @Column(name = "status", nullable = false)
    @Convert(converter = CouponStatusConverter.class)
    private CouponStatus status;

    /** 用户优惠券对应模板信息 **/
    @Transient
    private CouponTemplateSDK templateSDK;

    /**
     * <h2>返回一个无效的 Coupon 对象</h2>
     * @return
     */
    public static Coupon invalidCoupon() {
        Coupon coupon = new Coupon();
        coupon.setId(-1);

        return coupon;
    }

    /**
     * <h2>构造函数</h2>
     */
    public Coupon(Integer templateId,
                  Long userId,
                  String couponCode,
                  CouponStatus status) {

        this.templateId = templateId;
        this.userId = userId;
        this.couponCode = couponCode;
        this.status = status;
    }
}
