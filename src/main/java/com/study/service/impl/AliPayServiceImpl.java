package com.study.service.impl;


import com.alipay.api.AlipayApiException;
import com.study.bean.Alipay;
import com.study.entity.AlipayOrder;
import com.study.service.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    private Alipay aliPay;


    @Override
    public String aliPay(AlipayOrder AlipayOrder) throws AlipayApiException {
        System.out.println("order----service--:" + AlipayOrder);
        return aliPay.pay(AlipayOrder);
    }
}
