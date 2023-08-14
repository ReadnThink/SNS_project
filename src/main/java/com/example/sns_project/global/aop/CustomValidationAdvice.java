package com.example.sns_project.global.aop;

import com.example.sns_project.global.exception.BindingException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@Aspect
public class CustomValidationAdvice {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping(){

    }
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping(){

    }

    @Before("postMapping() || putMapping()")
    public void before(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BindingResult) {
                BindingResult br = (BindingResult) arg;
                if (br.hasErrors()) {
                    throw new BindingException(br.getFieldError().getDefaultMessage());
                }
            }
        }
    }
}
