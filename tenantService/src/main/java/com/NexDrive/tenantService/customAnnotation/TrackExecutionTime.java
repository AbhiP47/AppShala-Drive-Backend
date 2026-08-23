package com.NexDrive.tenantService.customAnnotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)  // Default Value
@Documented
public @interface TrackExecutionTime {

    long warnAfter() default 2000;
    String operationName();
}
