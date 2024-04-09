package ru.course.taskfour.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {
    private Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Around("@annotation(ru.course.taskfour.annotations.LogTransformation)")
    public Object logInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        SimpleDateFormat startFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Object proceed = joinPoint.proceed();

        logger.log(Level.INFO, joinPoint.getSignature()
                + "; начало операции: " + startFormat.format(start));
        return proceed;
    }
}
