package com.stackingrule.coupon.executor;

import com.stackingrule.coupon.constant.RuleFlag;
import com.stackingrule.coupon.vo.SettlementInfo;

/**
 * <h1>优惠券模板规则处理器定义</h1>
 */
public interface RuleExecutor {

    /**
     * <h2>规则类型标记</h2>
     * @return {@link RuleFlag}
     */
    RuleFlag ruleConfig();

    /**
     * <h2>优惠券规则的计算</h2>
     * @param settlement {@link SettlementInfo} 包含了选择的优惠券
     * @return {@link SettlementInfo} 修正过的结算信息
     */
    SettlementInfo computeRule(SettlementInfo settlement);

}
