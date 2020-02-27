package com.stackingrule.coupon.constant;

/**
 * <h1>通用常量定义</h1>
 */
public class Constant {

    /** Kafka 消息topic **/
    public static final String TOPIC = "user_coupon_op";

    /**
     * <h2>Redis Key 前缀</h2>
     */
    public static class RedisPrefix {

        /** 优惠券码 前缀 **/
        public static final String COUPON_TEMPLATE = "coupon_template_code_";

        /** 当前用户所有可用的优惠券 key 前缀 **/
        public static final String USER_COUPON_USABLE= "user_coupon_usable_";

        /** 当前用户所有已经使用的优惠券 key 前缀 **/
        public static final String USER_COUPON_USED= "user_coupon_used_";

        /** 当前用户所有已过期的优惠券 key 前缀 **/
        public static final String USER_COUPON_EXPIRED= "user_coupon_expired_";

    }

}
