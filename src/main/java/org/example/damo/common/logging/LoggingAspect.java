package org.example.damo.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.damo.common.constant.RequestConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;



@Aspect
@Component
@Order(2)
public class LoggingAspect {

    @Autowired
    LogFormatter logFormatter;

    @Around("execution(* org.example.damo.service..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Long startTime = System.currentTimeMillis();
        String requestId = MDC.get(RequestConstant.REQUEST_ID);
        String httpMethod = MDC.get(RequestConstant.HTTP_METHOD);
        String requestPath = MDC.get(RequestConstant.REQUEST_PATH);


        log.info(logFormatter.logRequest( requestId, className , methodName  , httpMethod , requestPath , startTime));

        try{
            // execute the original method logic
            Object result = joinPoint.proceed();
            Long endTime = System.currentTimeMillis();

            log.info(logFormatter.logResponse(requestId , className , methodName  , httpMethod , requestPath , startTime , endTime));

            return result;
        }catch (Exception e){
            Long endTime = System.currentTimeMillis();
            log.info(logFormatter.logError(requestId , className , methodName  , httpMethod , requestPath , startTime , endTime ));
            throw e;
        }

    }

    @Around("execution(* org.example.damo.controller..*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String methodName = joinPoint.getSignature().getName();
        String target = joinPoint.getTarget().getClass().getSimpleName();
        Long startTime = System.currentTimeMillis();
        String requestId = MDC.get(RequestConstant.REQUEST_ID);
        String httpMethod = MDC.get(RequestConstant.HTTP_METHOD);
        String requestPath = MDC.get(RequestConstant.REQUEST_PATH);

        log.info(logFormatter.logRequest( requestId, target , methodName , httpMethod , requestPath , startTime));

        try {
            Object result = joinPoint.proceed();
            Long endTime = System.currentTimeMillis();

            log.info(logFormatter.logResponse(requestId , target , methodName  , httpMethod , requestPath , startTime , endTime));
            return result;
        }catch (Exception e){
            Long endTime = System.currentTimeMillis();
            log.error(logFormatter.logError(requestId , target , methodName  , httpMethod , requestPath , startTime , endTime));
            throw e;
        }
    }


    @Around("execution(* org.example.damo.repository..*(..))")
    public Object LogRepositoryMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String methodName = joinPoint.getSignature().getName();
        String target = joinPoint.getTarget().getClass().getSimpleName();
        Long startTime = System.currentTimeMillis();
        String requestId = MDC.get(RequestConstant.REQUEST_ID);
        String httpMethod = MDC.get(RequestConstant.HTTP_METHOD);
        String requestPath = MDC.get(RequestConstant.REQUEST_PATH);

        log.info(logFormatter.logRequest(requestId, target , methodName  , httpMethod , requestPath , startTime));

        try {
            Object result = joinPoint.proceed();
            Long endTime = System.currentTimeMillis();

            log.info(logFormatter.logResponse(requestId , target , methodName  , httpMethod , requestPath , startTime , endTime));

            return result;
        }catch (Exception e){
            Long endTime = System.currentTimeMillis();
            log.error(logFormatter.logError(requestId , target , methodName  , httpMethod , requestPath , startTime , endTime));
            throw e;
        }
    }
}
