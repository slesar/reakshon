package com.psliusar.reakshon.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ReakshonEvent {

    private final Class<?> type;
    private final Object object;
    private final String methodName;
    private final Object[] args;
    private final Object result;

    public ReakshonEvent(
            @NonNull Class<?> type,
            @Nullable Object object,
            @NonNull String methodName,
            @NonNull Object[] args,
            @Nullable Object result) {
        this.type = type;
        this.object = object;
        this.methodName = methodName;
        this.args = args;
        this.result = result;
    }

    @NonNull
    public Class<?> getType() {
        return type;
    }

    @Nullable
    public Object getObject() {
        return object;
    }

    @NonNull
    public String getMethodName() {
        return methodName;
    }

    @NonNull
    public Object[] getArgs() {
        return args;
    }

    @Nullable
    public Object getResult() {
        return result;
    }

    @NonNull
    public ReakshonEvent mutateWithResult(@Nullable Object result) {
        return new ReakshonEvent(
                getType(),
                getObject(),
                getMethodName(),
                getArgs(),
                result);
    }
}
