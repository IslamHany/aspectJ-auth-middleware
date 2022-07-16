package com.example.demo.utils;

import org.springframework.stereotype.Component;

@Component
public class AuthImpl {
    public boolean authorize(String userId){
        return userId.equals("abc");
    }
}
