package com.psliusar.reakshon.thing;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.psliusar.reakshon.internal.Dispatcher;
import com.psliusar.reakshon.internal.ReakshonEvent;
import com.psliusar.reakshon.menu.action.MenuAction;
import com.psliusar.reakshon.menu.action.Repo;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public abstract class Thing<T> {

    private Observable<ReakshonEvent> source;

    protected void setSource(@NonNull Observable<ReakshonEvent> observable) {
        source = observable;
    }

    public Observable<ReakshonEvent> getSource() {
        return source;
    }

    @NonNull
    public Disposable subscribe(@NonNull Consumer<T> onNext) {
        return source
                .map(new Function<ReakshonEvent, T>() {
                    @Override
                    public T apply(ReakshonEvent event) throws Exception {
                        return (T) event.getObject();
                    }
                })
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("Thing", "Dispose: " + Thing.this);
                    }
                })
                .subscribe(onNext);
    }

    public Thing<T> menuAction(
            @NonNull final String title,
            @Nullable final String untilMethod,
            @NonNull final MenuAction.OnMenuActionListener<T> listener) {
        final Observable<ReakshonEvent> newSource = getSource()
                .map(new Function<ReakshonEvent, ReakshonEvent>() {
                    @Override
                    public ReakshonEvent apply(final ReakshonEvent event) throws Exception {
                        final MenuAction action = Repo.add(title, listener, event.getObject());

                        if (untilMethod != null) {
                            removeMenuAction(untilMethod, event.getObject(), action);
                        }

                        return event;
                    }
                });
        setSource(newSource);

        return this;
    }

    private void removeMenuAction(@NonNull final String whenMethod, @Nullable final Object sourceObject, @NonNull final MenuAction action) {
        Dispatcher.getSource()
                .filter(new Predicate<ReakshonEvent>() {
                    @Override
                    public boolean test(ReakshonEvent reakshonEvent) throws Exception {
                        return whenMethod.equals(reakshonEvent.getMethodName())
                                && sourceObject == reakshonEvent.getObject();
                    }
                })
                .map(new Function<ReakshonEvent, ReakshonEvent>() {
                    @Override
                    public ReakshonEvent apply(ReakshonEvent event) throws Exception {
                        Repo.remove(action);
                        return event;
                    }
                })
                .take(1)
                .subscribe();
    }

    protected static Predicate<ReakshonEvent> classAndMethod(@NonNull final Class<?> objectClass, @Nullable final String methodName) {
        return new Predicate<ReakshonEvent>() {
            @Override
            public boolean test(ReakshonEvent event) throws Exception {
                final String method = event.getMethodName();
                return objectClass.isAssignableFrom(event.getType())
                        && (methodName == null && "".equals(method) || method.equals(methodName));
            }
        };
    }

    protected static Predicate<ReakshonEvent> untilClassAndMethod(@NonNull final Class<?> objectClass, @NonNull final String methodName) {
        return new Predicate<ReakshonEvent>() {
            @Override
            public boolean test(ReakshonEvent event) throws Exception {
                final String method = event.getMethodName();
                return objectClass.isAssignableFrom(event.getType()) && method.equals(methodName);
            }
        };
    }

    protected static Predicate<ReakshonEvent> method(@NonNull final String methodName) {
        return new Predicate<ReakshonEvent>() {
            @Override
            public boolean test(ReakshonEvent event) throws Exception {
                return !methodName.equals(event.getMethodName());
            }
        };
    }
}
