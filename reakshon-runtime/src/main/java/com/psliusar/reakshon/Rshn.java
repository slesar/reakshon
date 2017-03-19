package com.psliusar.reakshon;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.psliusar.reakshon.thing.ActivityThing;
import com.psliusar.reakshon.thing.Interceptor;
import com.psliusar.reakshon.thing.JustThing;

public class Rshn {

    @NonNull
    public static <T> JustThing<T> with(@NonNull final Class<T> objectClass, @Nullable final String methodName) {
        return new JustThing<>(objectClass, methodName);
    }

    @NonNull
    public static <T extends Activity> ActivityThing<T> withActivity(@NonNull final Class<T> activityClass, @Nullable final String methodName) {
        return new ActivityThing<>(activityClass, methodName);
    }

    @NonNull
    public static <T> Interceptor<T> intercept(@NonNull final Class<T> objectClass, @Nullable final String methodName) {
        return new Interceptor<>(objectClass, methodName);
    }


}
