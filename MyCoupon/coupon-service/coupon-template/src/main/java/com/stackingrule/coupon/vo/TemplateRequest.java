package com.stackingrule.coupon.vo;

import com.stackingrule.coupon.constant.CouponCategory;
import com.stackingrule.coupon.constant.DistributeTarget;
import com.stackingrule.coupon.constant.ProductLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * <h1>优惠券模板请求对象</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRequest {

    /** 优惠券名称 **/
    private String name;

    /** 优惠券logo **/
    private String logo;

    /** 优惠券描述 **/
    private String desc;

    /** 优惠券分类 **/
    private String categoty;

    /** 产品线 **/
    private Integer productLine;

    /** 总数 **/
    private Integer count;

    /** 创建用户 **/
    private Long userId;

    /** 目标用户 **/
    private Integer target;

    /** 优惠券规则 **/
    private TemplateRule rule;

    /**
     * 校验对象的合法性
     * @return
     */
    public boolean validate() {
        boolean stringValid = StringUtils.isNotEmpty(name)
                && StringUtils.isNotEmpty(logo)
                && StringUtils.isNotEmpty(desc);
        boolean enumValid = null != CouponCategory.of(categoty)
                && null != ProductLine.of(productLine)
                && null != DistributeTarget.of(target);
        boolean numValid = count > 0 && userId > 0;

        return stringValid && enumValid && numValid && rule.validate();
    }
}
