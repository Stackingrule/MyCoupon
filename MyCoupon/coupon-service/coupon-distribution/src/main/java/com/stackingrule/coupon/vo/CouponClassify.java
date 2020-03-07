package com.stackingrule.coupon.vo;

import com.stackingrule.coupon.constant.CouponStatus;
import com.stackingrule.coupon.constant.PeriodType;
import com.stackingrule.coupon.entity.Coupon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.time.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <h1>用户优惠券分类，根据优惠券状态</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponClassify {

    /** 可使用的 **/
    private List<Coupon> usable;

    /** 已使用的 **/
    private List<Coupon> used;

    /** 过期的 **/
    private List<Coupon> expired;

    /**
     * <h2>对当前优惠券进行分类</h2>
     * @param coupons {@link Coupon}
     * @return
     */
    public static CouponClassify classify(List<Coupon> coupons) {

        List<Coupon> usable = new ArrayList<>(coupons.size());
        List<Coupon> used = new ArrayList<>(coupons.size());
        List<Coupon> expired = new ArrayList<>(coupons.size());

        coupons.forEach(c -> {
            boolean isTimeExpired;
            long curTime = new Date().getTime();

            if (c.getTemplateSDK().getRule().getExpiration()
                    .getPeriod().equals(PeriodType.REGULAR.getCode())) {
                isTimeExpired = c.getTemplateSDK()
                        .getRule().getExpiration().getDeadline() <= curTime;
            } else {
                isTimeExpired = DateUtils.addDays(
                        c.getAssignTime(),
                        c.getTemplateSDK().getRule().getExpiration().getGap()
                ).getTime() < curTime;
            }

            if (c.getStatus() == CouponStatus.USED) {
                used.add(c);
            } else if (c.getStatus() == CouponStatus.EXPIRED || isTimeExpired) {
                expired.add(c);
            } else {
                usable.add(c);
            }
        });

        return new CouponClassify(usable, used, expired);
    }

}
