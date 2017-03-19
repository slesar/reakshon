package com.psliusar.reakshon.thing;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.psliusar.reakshon.internal.ReakshonEvent;
import com.psliusar.reakshon.menu.FloatingMenu;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class ActivityThing<T extends Activity> extends JustThing<T> {

    public ActivityThing(@NonNull Class<T> activityClass, @Nullable String methodName) {
        super(activityClass, methodName);
    }

    public ActivityThing<T> showMenu() {
        final Observable<ReakshonEvent> source = getSource()
                .doOnNext(new Consumer<ReakshonEvent>() {
                    @Override
                    public void accept(ReakshonEvent event) throws Exception {
                        FloatingMenu.placeOn((Activity) event.getObject());
                    }
                });
        setSource(source);

        return this;
    }
}
