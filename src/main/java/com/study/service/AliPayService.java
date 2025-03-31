package com.study.service;

import com.alipay.api.AlipayApiException;
import com.study.entity.AlipayOrder;

public interface AliPayService {
    /**
     * 支付宝支付接口
     */
    String aliPay(AlipayOrder AlipayOrder) throws AlipayApiException;
}
