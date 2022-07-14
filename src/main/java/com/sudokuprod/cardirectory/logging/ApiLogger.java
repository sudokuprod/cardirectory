package com.sudokuprod.cardirectory.logging;

import com.sudokuprod.cardirectory.model.ApiLog;
import com.sudokuprod.cardirectory.repo.LogRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ApiLogger {
    private final LogRepo logRepo;

    @Around("@annotation(com.sudokuprod.cardirectory.logging.LogApiCall)")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        final ApiLog apiLog = new ApiLog();
        apiLog.setCallTIme(new Date());

        long startTime = System.currentTimeMillis();
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        try {
            RequestMapping mapping = AnnotationUtils.findAnnotation(ms.getMethod(), RequestMapping.class);
            if (Objects.nonNull(mapping)) {
                apiLog.setPath(mapping.path()[0]);
            }
            apiLog.setSuccessful(true);

            return pjp.proceed();
        } catch (Throwable t) {
            log.warn("Exception while processing method {}", ms.getMethod().getName(), t);
            apiLog.setSuccessful(false);
            if (StringUtils.isNotBlank(t.getMessage())) {
                apiLog.setExMessage(t.getMessage());
            }

            throw t;
        } finally {
            long endTime = System.currentTimeMillis();
            apiLog.setExecutionTime(endTime - startTime);
            CompletableFuture.runAsync(() -> logRepo.save(apiLog));
        }
    }
}
