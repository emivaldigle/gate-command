package com.visp.gate_command.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

  private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Around(
      "@annotation(com.visp.gate_command.aop.Loggable) || @within(com.visp.gate_command.aop.Loggable)")
  public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
    String methodName = joinPoint.getSignature().getName();
    String className = joinPoint.getTarget().getClass().getSimpleName();
    Object[] args = joinPoint.getArgs();

    try {
      String argsJson = objectMapper.writeValueAsString(args);
      logger.info("Entering {}.{} with arguments: {}", className, methodName, argsJson);
    } catch (Exception e) {
      logger.warn("Failed to log arguments for {}.{}", className, methodName);
    }

    Object result;
    try {
      result = joinPoint.proceed();
    } catch (Exception e) {
      logger.error("Exception in {}.{}: {}", className, methodName, e.getMessage());
      throw e;
    }

    try {
      String resultJson = objectMapper.writeValueAsString(result);
      logger.info("Exiting {}.{} with result: {}", className, methodName, resultJson);
    } catch (Exception e) {
      logger.warn("Failed to log result for {}.{}", className, methodName);
    }

    return result;
  }
}
