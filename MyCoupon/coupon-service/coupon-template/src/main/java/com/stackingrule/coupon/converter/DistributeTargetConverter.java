package com.stackingrule.coupon.converter;

import com.stackingrule.coupon.constant.DistributeTarget;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * <h1>分发目标枚举属性转换器</h1>
 */
@Converter
public class DistributeTargetConverter
        implements AttributeConverter<DistributeTarget, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DistributeTarget distributeTarget) {
        if (distributeTarget == null) {
            return null;
        }
        return distributeTarget.getCode();
    }

    @Override
    public DistributeTarget convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }
        return DistributeTarget.of(code);
    }
}

