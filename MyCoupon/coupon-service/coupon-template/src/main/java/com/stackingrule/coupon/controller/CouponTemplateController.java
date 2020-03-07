package com.stackingrule.coupon.controller;

import com.alibaba.fastjson.JSON;
import com.stackingrule.coupon.entity.CouponTemplate;
import com.stackingrule.coupon.exception.CouponException;
import com.stackingrule.coupon.service.IBuildTemplateService;
import com.stackingrule.coupon.service.ITemplateBaseService;
import com.stackingrule.coupon.vo.CouponTemplateSDK;
import com.stackingrule.coupon.vo.TemplateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <h1>优惠券模板功能相关的控制器</h1>
 */
@Slf4j
@RestController
public class CouponTemplateController {

    /** 构建优惠券模板服务 **/
    private final IBuildTemplateService buildTemplateService;

    /** 优惠券模板基础服务 */
    private final ITemplateBaseService templateBaseService;

    @Autowired
    public CouponTemplateController(IBuildTemplateService buildTemplateService,
                                    ITemplateBaseService templateBaseService) {
        this.buildTemplateService = buildTemplateService;
        this.templateBaseService = templateBaseService;
    }



    /**
     * <h2>构建优惠券模板</h2>
     *  127.0.0.1:7001/coupon-template/template/build
     *  127.0.0.1:9000/imooc/coupon-template/template/build
     * @param request {@link TemplateRequest}
     * @return {@link CouponTemplate}
     * @throws CouponException
     */
    @PostMapping("/template/build")
    public CouponTemplate buildTemplate(@RequestBody TemplateRequest request)
            throws CouponException {
        log.info("BuildTemplate : {}", JSON.toJSONString(request));
        return buildTemplateService.buildTemplate(request);
    }

    /**
     * <h2>构造优惠券模板详情</h2>
     * 127.0.0.1:7001/coupon-template/template/info?id=1
     * @param id
     * @return {@link CouponTemplate}
     * @throws CouponException
     */
    @GetMapping("/template/info")
    public CouponTemplate buildTemplateInfo(@RequestParam("id") Integer id) throws CouponException {
        log.info("Build Template Info For : {}", id);
        return templateBaseService.buildTemplateInfo(id);
    }


    /**
     ** <h2>查找所有可用的优惠券模板</h2>
     *  127.0.0.1:7001/coupon-template/template/sdk/all
     * @return {@link CouponTemplateSDK}
     */
    @GetMapping("/template/sdk/all")
    public List<CouponTemplateSDK> findAllUsableTemplate() {
        log.info("Find All Usable Template.");
        return templateBaseService.findAllUsableTemplate();
    }

    /**
     * <h2>获取模板 ids 到 CouponTemplateSDK 的映射</h2>
     * 127.0.0.1:7001/coupon-template/template/sdk/infos
     * 127.0.0.1:9000/imooc/coupon-template/template/sdk/infos?ids=1,2
     * @param ids
     * @return
     */
    @GetMapping("/template/sdk/infos")
    public Map<Integer, CouponTemplateSDK> findIds2TemplateSDK(
            @RequestParam("ids") Collection<Integer> ids) {

        log.info("findIds2TemplateSDK : {}", JSON.toJSONString(ids));

        return templateBaseService.findIds2TemplateSDK(ids);
    }

}
