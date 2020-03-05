package com.stackingrule.coupon.dao;

import com.stackingrule.coupon.constant.CouponStatus;
import com.stackingrule.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <h1>CouponDao 接口定义</h1>
 */
public interface CouponDao extends JpaRepository<Coupon, Integer> {

    /**
     * <h2>根据 userId + 状态 寻找优惠券记录</h2>
     * where userId = ... and status = ...
     * */
    List<Coupon> findAllByUserIdAndStatus(Long userId, CouponStatus status);

}
