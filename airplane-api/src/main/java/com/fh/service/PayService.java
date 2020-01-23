package com.fh.service;

import com.fh.util.ServerResponse;

public interface PayService {
    ServerResponse updatepay(Integer id);

    ServerResponse queryPayStatus(Integer id);

    ServerResponse selectPayMoney(String outtradeno);
}
