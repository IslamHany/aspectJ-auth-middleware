package com.example.demo.middlewares;

import com.example.demo.annotations.Authorized;
import com.example.demo.exceptions.UnAuthorizedException;
import com.example.demo.utils.AuthImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.TreeMap;

@Aspect
@Configuration
public class AuthMiddleware {
    @Autowired
    AuthImpl auth;

    @Pointcut("@annotation(com.example.demo.annotations.Authorized)") //expression
    private void auth() {} //signature

    @Before("auth()")
    public void beforeHello(JoinPoint jp){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Authorized authorizedAnnotation = methodSignature.getMethod().getAnnotation(Authorized.class);

        if (authorizedAnnotation.enabled()){
            Map map = new TreeMap<>((Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            String userId = (String) map.get("userId");

            if (!auth.authorize(userId))
                throw new UnAuthorizedException(userId);
        }

    }
}
