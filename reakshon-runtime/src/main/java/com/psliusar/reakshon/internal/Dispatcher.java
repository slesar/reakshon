package com.psliusar.reakshon.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;

import io.reactivex.subjects.PublishSubject;

@Aspect
public class Dispatcher {

    private static final PublishSubject<ReakshonEvent> SOURCE = PublishSubject.create();

    public static PublishSubject<ReakshonEvent> getSource() {
        return SOURCE;
    }

    @Nullable
    private static Object dispatch(
            @NonNull Class<?> type,
            @Nullable Object obj,
            @NonNull String methodName,
            @NonNull Object[] args,
            @Nullable Object result) {
        final ReakshonEvent event = new ReakshonEvent(type, obj, methodName, args, result);
        SOURCE.onNext(event);
        return result;
    }

    @Pointcut("within(@com.psliusar.reakshon.annotation.ReakshonTag *)")
    public void withinAnnotatedClass() {}

    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {}

    @Pointcut("execution(!synthetic *.new(..)) && withinAnnotatedClass()")
    public void constructorInsideAnnotatedType() {}

    @Pointcut("execution(@com.psliusar.reakshon.annotation.ReakshonTag * *(..)) || methodInsideAnnotatedType()")
    public void method() {}

    @Pointcut("execution(@com.psliusar.reakshon.annotation.ReakshonTag *.new(..)) || constructorInsideAnnotatedType()")
    public void constructor() {}

    @SuppressWarnings("unused")
    @Around("method() || constructor()")
    public Object notifyAndExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        final CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();

        final Class<?> type = codeSignature.getDeclaringType();
        final Object obj = joinPoint.getThis();

        final String methodName = codeSignature.getName();

        final Object[] args = joinPoint.getArgs();

        return dispatch(type, obj, methodName, args, joinPoint.proceed());
    }
}
