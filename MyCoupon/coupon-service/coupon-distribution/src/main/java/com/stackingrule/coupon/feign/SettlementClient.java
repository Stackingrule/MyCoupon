package com.stackingrule.coupon.feign;

import com.stackingrule.coupon.exception.CouponException;
import com.stackingrule.coupon.vo.CommonResponse;
import com.stackingrule.coupon.vo.SettlementInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <h1>优惠券结算微服务 Feign 接口定义</h1>
 */
@FeignClient(value = "eureka-client-coupon-settlement")
public interface SettlementClient {

    /**
     * <h2>优惠券规则计算</h2>
     * @param settlement
     * @return
     * @throws CouponException
     */
    @RequestMapping(value = "/coupon-settlement/settlement/compute",
            method = RequestMethod.POST)
    CommonResponse<SettlementInfo> computeRule(
            @RequestBody SettlementInfo settlement
    ) throws CouponException;

}
