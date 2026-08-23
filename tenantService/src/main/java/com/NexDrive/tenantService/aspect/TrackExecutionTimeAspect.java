package com.NexDrive.tenantService.aspect;

import com.NexDrive.tenantService.customAnnotation.TrackExecutionTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TrackExecutionTimeAspect {

    @Around("@annotation(trackExecutionTime)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint , TrackExecutionTime trackExecutionTime) throws Throwable {
        long startTime = System.currentTimeMillis();
        try
        {
            return joinPoint.proceed();
        }
        finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            long warningThreshold = trackExecutionTime.warnAfter();
            String operation = trackExecutionTime.operationName();
            if(operation.isBlank() || operation.isEmpty())
                operation = joinPoint.getSignature().getName();

            if(duration >= warningThreshold)
            {
                System.out.println("SLOW OPERATION ALERT : " + "Time taken by operation  --> " + operation +" : " +duration );
            }
            else
                System.out.println( "Time taken by operation  --> " + operation +" : " +duration );

        }
    }
}
