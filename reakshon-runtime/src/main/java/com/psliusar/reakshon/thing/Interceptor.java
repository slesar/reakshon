package com.psliusar.reakshon.thing;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.psliusar.reakshon.internal.ReakshonEvent;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class Interceptor<T> extends JustThing<T> {

    public Interceptor(@NonNull Class<T> objectClass, @Nullable String methodName) {
        super(objectClass, methodName);
    }

    public <V> Interceptor<T> with(@NonNull final Function<V, V> callback) {
        final Observable<ReakshonEvent> source = getSource()
                .map(new Function<ReakshonEvent, ReakshonEvent>() {
                    @Override
                    public ReakshonEvent apply(ReakshonEvent event) throws Exception {
                        return event.mutateWithResult(callback.apply((V) event.getResult()));
                    }
                });
        setSource(source);
        return this;
    }
}
