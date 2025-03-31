package com.study.bean;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.config.AliPayConfig;
import com.study.dao.ProvincesMapper;
import com.study.entity.AlipayOrder;
import com.study.entity.AlipayOrder;
import com.study.entity.Orders;
import com.study.entity.Orders_item;
import com.study.service.GoodsService;
import com.study.service.OrdersService;
import com.study.service.Orders_itemService;
import com.study.service.ShopcartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Alipay {
    /*这个是沙箱组件*/
    @Autowired
    private AliPayConfig aliPayConfig;

    @Autowired
    private com.study.service.OrdersService OrdersService;
    @Autowired
    private Orders_itemService Orders_itemService;

    public String pay(AlipayOrder AlipayOrder) throws AlipayApiException {
        //支付网关
        String serverUrl = aliPayConfig.getGatewayUrl();
        // AppId
        String appId = aliPayConfig.getAppId();
        // 用户密钥(私钥)，即PKCS8格式RSA2私钥
        String privateKey = aliPayConfig.getPrivateKey();
        //格式化为json格式
        String format = "json";
        //编码
        String charset = aliPayConfig.getCharset();
        //支付宝公钥，即对应Appid下的支付宝公钥
        String alipayPublicKey = aliPayConfig.getAlipayPublicKey();
        //签名方式
        String signType = aliPayConfig.getSignType();
        //页面跳转同步通知页面路径
        String returnUrl = aliPayConfig.getReturnUrl();
        //服务器异步通知页面路径
        String notifyUrl = aliPayConfig.getNotifyUrl();

        //1、获取初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
        //2、设置请求参数
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        // 页面跳转同步通知页面路径
        alipayTradePagePayRequest.setReturnUrl(returnUrl);
        alipayTradePagePayRequest.setNotifyUrl(notifyUrl);
        //封装参数（json格式）
        alipayTradePagePayRequest.setBizContent(JSON.toJSONString(AlipayOrder));

        //    3、请求支付宝进行付款，并获取支付结果
        String body = alipayClient.pageExecute(alipayTradePagePayRequest).getBody();
        //放回信息

        System.out.println("支付完成" + AlipayOrder.getOut_trade_no());
        //这里再去更新订单支付情况
        Orders orders = OrdersService.selectOrdersByOrders_num(AlipayOrder.getOut_trade_no());
        orders.setPay_state(1);
        OrdersService.updateById(orders);
        QueryWrapper<Orders_item> queryWrapper = new QueryWrapper<Orders_item>();
        queryWrapper.eq("orders_ordernum", orders.getOrder_num());
        List<Orders_item> Orders_item_list = Orders_itemService.list(queryWrapper);
        for (Orders_item data : Orders_item_list) {
            data.setState(1);
            Orders_itemService.updateById(data);
        }
        return body;
    }
}
