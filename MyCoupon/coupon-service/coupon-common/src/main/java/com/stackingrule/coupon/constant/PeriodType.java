package com.stackingrule.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * <h1>有效期类型枚举</h1>
 */
@Getter
@AllArgsConstructor
public enum PeriodType {

    REGULAR("固定日期", 1),
    SHIFT("变动的（以领取之日开始计算）", 2),
    ;



    /** 有效期类型描述 **/
    private String description;

    /** 有效期类型编码 **/
    private Integer code;

    public static PeriodType of(Integer code) {
        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code+ " not exist!"));
    }

}
