package com.psliusar.reakshon.thing;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.psliusar.reakshon.internal.Dispatcher;
import com.psliusar.reakshon.internal.ReakshonEvent;

import io.reactivex.Observable;

public class JustThing<T> extends Thing<T> {

    public  JustThing(@NonNull Class<T> objectClass, @Nullable String methodName) {
        final Observable<ReakshonEvent> source = Dispatcher.getSource()
                .filter(classAndMethod(objectClass, methodName));
        setSource(source);
    }
}
