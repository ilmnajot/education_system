package com.example.ilmnajot.service;

import com.example.ilmnajot.model.common.ApiResponse;
import com.example.ilmnajot.model.request.LoginForm;
import com.example.ilmnajot.model.request.UserRequest;


public interface AuthService {

    ApiResponse register(UserRequest form);

//    LoginResponse addUser(UserRequest request);

    ApiResponse authenticate(LoginForm form);
}
