package com.stackingrule.coupon.service.impl;

import com.alibaba.fastjson.JSON;
import com.stackingrule.coupon.constant.Constant;
import com.stackingrule.coupon.constant.CouponStatus;
import com.stackingrule.coupon.dao.CouponDao;
import com.stackingrule.coupon.entity.Coupon;
import com.stackingrule.coupon.service.IKafkaService;
import com.stackingrule.coupon.vo.CouponKafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * <h1>Kafka 相关服务接口实现</h1>
 * 核心思想: 是将 Cache 中的 Coupon 的状态变化同步到 DB 中
 */
@Slf4j
@Component
public class KafkaServiceImpl implements IKafkaService {

    /** Coupon Dao */
    private final CouponDao couponDao;

    @Autowired
    public KafkaServiceImpl(CouponDao couponDao) {
        this.couponDao = couponDao;
    }


    @Override
    @KafkaListener(topics = {Constant.TOPIC}, groupId = "coupon-1")
    public void consumeCouponKafkaMessage(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            CouponKafkaMessage couponInfo = JSON.parseObject(
                    message.toString(),
                    CouponKafkaMessage.class
            );

            log.info("Receive CouponKafkaMessage: {}", message.toString());

            CouponStatus status = CouponStatus.of(couponInfo.getStatus());

            switch (status) {
                case USABLE:
                    break;
                case USED:
                    processUsedCoupons(couponInfo, status);
                    break;
                case EXPIRED:
                    processExpiredCoupons(couponInfo, status);
                    break;
            }
        }

    }


    /**
     * <h2>处理已使用的用户优惠券</h2>
     * @param kafkaMessage {@link CouponKafkaMessage}
     * @param status {@link CouponStatus}
     */
    private void processUsedCoupons(CouponKafkaMessage kafkaMessage,
                                    CouponStatus status) {
        // TODO 给用户发送短信
        processCouponsByStatus(kafkaMessage, status);
    }

    /**
     * <h2>处理已过期的用户优惠券</h2>
     * @param kafkaMessage {@link CouponKafkaMessage}
     * @param status {@link CouponStatus}
     */
    private void processExpiredCoupons(CouponKafkaMessage kafkaMessage,
                                       CouponStatus status) {
        // TODO 给用户发送推送
        processCouponsByStatus(kafkaMessage, status);
    }


    /**
     * <h2>根据状态处理优惠券信息</h2>
     * @param kafkaMessage {@link CouponKafkaMessage}
     * @param status {@link CouponStatus}
     */
    private void processCouponsByStatus(CouponKafkaMessage kafkaMessage,
                                        CouponStatus status) {
         List<Coupon> coupons = couponDao.findAllById(kafkaMessage.getIds());

         if (CollectionUtils.isEmpty(coupons)
                 || coupons.size() != kafkaMessage.getIds().size()) {
             log.error("Can Not Find Right Coupon Info: {}",
                     JSON.toJSONString(kafkaMessage));
             // TODO 发送邮件
             return;
         }

         coupons.forEach(c -> c.setStatus(status));
        log.info("CouponKafkaMessage Op Coupon Count: {}",
                couponDao.saveAll(coupons).size());
    }


}
