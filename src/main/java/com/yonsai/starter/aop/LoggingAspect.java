package com.yonsai.starter.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

/**
 * 컨트롤러 및 서비스 계층의 실행 로그를 기록하는 AOP Aspect
 *
 * - 메서드 실행 전/후 로그를 남긴다
 * - 실행 시간을 측정한다
 * - 예외 발생 시 에러 로그를 기록한다
 *
 */

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /**
     * Controller / Service 계층 메서드를 감싸는 Around Advice
     * Domain 패키지 내에 controller , service 패키지가 있는 모든 메서드에 적용된다
     *
     * @param joinPoint 실행 대상 메서드 정보
     * @return 원래 메서드의 반환값
     * @throws Throwable 메서드 실행 중 발생한 예외를 그대로 전달
     */
    @Around("execution(* com.yonsai.starter..controller..*(..)) || " +
            "execution(* com.yonsai.starter..service..*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {


        String className = joinPoint.getSignature().getDeclaringTypeName();
        String layer = "";

        if (className.contains(".controller.")) {
            layer = "Controller";
        } else if (className.contains(".service.")) {
            layer = "Service";
        }

        String methodName = joinPoint.getSignature().getName();
        long start = System.currentTimeMillis();

        log.info("[AOP START] => {} : {}", layer, methodName);

        try {
            Object result = joinPoint.proceed();

            long end = System.currentTimeMillis();
            log.info("[AOP END] => {} : {} : {}ms", layer, methodName, (end - start));

            return result;

        } catch (Exception e) {
            log.error("[AOP ERROR] => {} : {} | {}", layer, methodName, e.getMessage());
            throw e;
        }
    }
}
