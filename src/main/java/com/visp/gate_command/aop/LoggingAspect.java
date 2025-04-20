package com.visp.gate_command.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

  private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);
  private final ObjectMapper objectMapper;

  @Around(
      "@annotation(com.visp.gate_command.aop.Loggable) || @within(com.visp.gate_command.aop.Loggable)")
  public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Loggable loggable = signature.getMethod().getAnnotation(Loggable.class);

    if (loggable == null) {
      loggable = joinPoint.getTarget().getClass().getAnnotation(Loggable.class);
    }

    String methodName = signature.getName();
    String className = joinPoint.getTarget().getClass().getSimpleName();
    Object[] args = joinPoint.getArgs();
    String logLevel = loggable != null ? loggable.level().toUpperCase() : "INFO";

    String argsJson = safeToJson(args);

    logMessage(logLevel, "Entering {}.{} with arguments: {}", className, methodName, argsJson);

    Object result;
    try {
      result = joinPoint.proceed();
    } catch (Exception e) {
      logMessage("ERROR", "Exception in {}.{}: {}", className, methodName, e.getMessage());
      throw e;
    }

    String resultJson = safeToJson(result);

    logMessage(logLevel, "Exiting {}.{} with result: {}", className, methodName, resultJson);

    return result;
  }

  private String safeToJson(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      return "[unserializable]";
    }
  }

  private void logMessage(String level, String message, Object... args) {
    switch (level) {
      case "DEBUG":
        log.debug(message, args);
        break;
      case "INFO":
        log.info(message, args);
        break;
      case "WARN":
        log.warn(message, args);
        break;
      case "ERROR":
        log.error(message, args);
        break;
      default:
        log.info(message, args);
        break;
    }
  }
}
