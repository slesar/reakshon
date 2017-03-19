package com.psliusar.reakshon.menu.action;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.lang.ref.WeakReference;

public class MenuAction {

    private final String title;
    private final OnMenuActionListener listener;
    private final WeakReference<Object> reference;

    public MenuAction(@NonNull String title, @NonNull OnMenuActionListener listener, @Nullable Object object) {
        this.title = title;
        this.listener = listener;
        this.reference = new WeakReference<>(object);
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public OnMenuActionListener getListener() {
        return listener;
    }

    @Nullable
    public Object getObject() {
        return reference.get();
    }

    public interface OnMenuActionListener<V> {

        void onActionSelected(@NonNull View view, @Nullable V object);
    }
}
