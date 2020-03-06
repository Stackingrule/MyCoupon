package com.stackingrule.coupon.service.impl;

import com.alibaba.fastjson.JSON;
import com.stackingrule.coupon.constant.Constant;
import com.stackingrule.coupon.constant.CouponStatus;
import com.stackingrule.coupon.dao.CouponDao;
import com.stackingrule.coupon.entity.Coupon;
import com.stackingrule.coupon.exception.CouponException;
import com.stackingrule.coupon.feign.SettlementClient;
import com.stackingrule.coupon.feign.TemplateClient;
import com.stackingrule.coupon.service.IRedisService;
import com.stackingrule.coupon.service.IUserService;
import com.stackingrule.coupon.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <h1>用户服务接口实现</h1>
 * 所有的操作过程, 状态都保存在 Redis 中, 并通过 Kafka 把消息传递到 MySQL 中
 * 为什么使用 Kafka, 而不是直接使用 SpringBoot 中的异步处理 ?
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    /** Coupon Dao */
    private final CouponDao couponDao;

    /** Redis 服务 */
    private final IRedisService redisService;

    /** 模板微服务客户端 */
    private final TemplateClient templateClient;

    /** 结算微服务客户端 */
    private final SettlementClient settlementClient;

    /** Kafka 客户端 */
    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserServiceImpl(CouponDao couponDao,
                           IRedisService redisService,
                           TemplateClient templateClient,
                           SettlementClient settlementClient,
                           KafkaTemplate<String, String> kafkaTemplate) {
        this.couponDao = couponDao;
        this.redisService = redisService;
        this.templateClient = templateClient;
        this.settlementClient = settlementClient;
        this.kafkaTemplate = kafkaTemplate;
    }


    /**
     * <h2>根据用户 id 和状态查询优惠券记录</h2>
     * @param userId 用户 id
     * @param status 优惠券状态
     * @return
     * @throws CouponException
     */
    @Override
    public List<Coupon> findCouponByStatus(Long userId, Integer status)
            throws CouponException {

        List<Coupon> curCached = redisService.getCachedCoupons(userId, status);
        List<Coupon> preTarget;

        if (CollectionUtils.isNotEmpty(curCached)) {
            log.debug("coupon cache is not empty: {}, {}", userId, status);
            preTarget = curCached;
        } else {
            log.debug("coupon cache is empty, get coupon from db: {}, {}",
                    userId, status);
            List<Coupon> dbCoupons = couponDao.findAllByUserIdAndStatus(userId,
                    CouponStatus.of(status)
            );
            // 如果数据库中没有记录, 直接返回就可以, Cache 中已经加入了一张无效的优惠券
            if (CollectionUtils.isNotEmpty(dbCoupons)) {
                log.debug("current user do not have coupon: {}, {}", userId, status);
                return dbCoupons;
            }

            // 填充 dbCoupons的 templateSDK 字段
            Map<Integer, CouponTemplateSDK> id2TemplateSDK =
                    templateClient.findIds2TemplateSDK(
                      dbCoupons.stream()
                              .map(Coupon::getTemplateId)
                              .collect(Collectors.toList())
                    ).getData();

            dbCoupons.forEach(
                    dc -> dc.setTemplateSDK(
                            id2TemplateSDK.get(dc.getTemplateId())
                    )
            );

            // 数据库中存在记录
            preTarget = dbCoupons;

            // 将记录写入 Cache
            redisService.addCouponToCache(userId, preTarget, status);

        }

        // 将无效优惠券剔除
        preTarget = preTarget.stream()
                .filter(c -> c.getId() != -1)
                .collect(Collectors.toList());

        // 如果当前获取的是可用优惠券, 还需要做对已过期优惠券的延迟处理
        if (CouponStatus.of(status) == CouponStatus.USABLE) {
            CouponClassify classify = CouponClassify.classify(preTarget);
            // 如果当前获取的是可用优惠券, 还需要做对已过期优惠券的延迟处理
            if (CollectionUtils.isNotEmpty(classify.getExpired())) {
                log.info("Add Expired Coupons To Cache From FindCouponsByStatus: " +
                        "{}, {}", userId, status);
                redisService.addCouponToCache(
                        userId,
                        classify.getExpired(),
                        CouponStatus.EXPIRED.getCode()
                );

                // 发送到 kafka 中做异步处理
                kafkaTemplate.send(
                        Constant.TOPIC,
                        JSON.toJSONString(new CouponKafkaMessage(
                                CouponStatus.EXPIRED.getCode(),
                                classify.getExpired().stream()
                                        .map(Coupon::getId)
                                        .collect(Collectors.toList())
                        ))
                );

            }

            return classify.getUsable();
        }

        return preTarget;
    }

    @Override
    public List<CouponTemplateSDK> findAvailableTemplate(Long userId) throws CouponException {
        return null;
    }

    @Override
    public Coupon acquireTemplate(AcquireTemplateRequest request) throws CouponException {
        return null;
    }

    @Override
    public SettlementInfo settlement(SettlementInfo info) throws CouponException {
        return null;
    }
}
