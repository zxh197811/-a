package com.fh.service;

import com.fh.model.OrderInfo;
import com.fh.util.ServerResponse;

public interface OrderService {
    ServerResponse addOrder(OrderInfo orderInfo,Integer userid);

    ServerResponse selectOrderById(String orderid);

    ServerResponse selectOrderItem(String id);
}
