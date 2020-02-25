package com.stackingrule.coupon.advice;


import com.stackingrule.coupon.vo.CommonResponse;
import com.stackingrule.exception.CouponException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>全局异常处理</h1>
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * <h2>对 CouponException 进行统一处理</h2>
     * */
    @ExceptionHandler(value = CouponException.class)
    public CommonResponse<String> handleCouponException(
            HttpServletRequest request,
            CouponException ex
    ) {

        CommonResponse<String> response = new CommonResponse<String>(
                -1,
                "business error"
        );
        response.setData(ex.getMessage());

        return response;
    }
}
