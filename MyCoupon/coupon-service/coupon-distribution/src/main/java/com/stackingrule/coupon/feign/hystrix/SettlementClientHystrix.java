package com.stackingrule.coupon.feign.hystrix;

import com.stackingrule.coupon.exception.CouponException;
import com.stackingrule.coupon.feign.SettlementClient;
import com.stackingrule.coupon.vo.CommonResponse;
import com.stackingrule.coupon.vo.SettlementInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <h1> * <h1>结算微服务熔断策略实现</h1></h1>
 */
@Slf4j
@Component
public class SettlementClientHystrix implements SettlementClient {

    /**
     * <h2>优惠券规则计算</h2>
     * @param settlement
     * @return
     * @throws CouponException
     */
    @Override
    public CommonResponse<SettlementInfo> computeRule(SettlementInfo settlement)
            throws CouponException {

        log.error("[eureka-client-coupon-settlement] computeRule" +
                "request error");
        settlement.setEmploy(false);
        settlement.setCost(-1.0);

        return new CommonResponse<>(
                -1,
                "[eureka-client-coupon-settlement] request error",
                settlement
        );
    }
}
