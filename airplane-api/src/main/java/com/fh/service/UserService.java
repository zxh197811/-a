package com.fh.service;

import com.fh.util.ServerResponse;

public interface UserService {
    ServerResponse dl(String name, String password);
}
