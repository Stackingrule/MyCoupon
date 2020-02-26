package com.stackingrule.coupon.converter;


import com.stackingrule.coupon.constant.ProductLine;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * <h1>产品线枚举属性转换器</h1>
 */
@Converter
public class ProductLineConverter
        implements AttributeConverter<ProductLine, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProductLine productLine) {
        return productLine.getCode();
    }

    @Override
    public ProductLine convertToEntityAttribute(Integer code) {
        return ProductLine.of(code);
    }
}
