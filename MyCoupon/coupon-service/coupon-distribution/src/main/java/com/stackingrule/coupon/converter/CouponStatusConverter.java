package com.stackingrule.coupon.converter;

import com.stackingrule.coupon.constant.CouponStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

/**
 * <h1>优惠券状态枚举属性转换器</h1>
 */
@Convert
public class CouponStatusConverter implements
        AttributeConverter<CouponStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CouponStatus status) {
        return status.getCode();
    }

    @Override
    public CouponStatus convertToEntityAttribute(Integer code) {
        return CouponStatus.of(code);
    }
}
