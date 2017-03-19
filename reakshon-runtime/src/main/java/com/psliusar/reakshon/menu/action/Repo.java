package com.psliusar.reakshon.menu.action;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Repo {

    private static final ArrayList<MenuAction> ACTIONS = new ArrayList<>();

    @NonNull
    public static MenuAction add(
            @NonNull String title,
            @NonNull MenuAction.OnMenuActionListener listener,
            @Nullable Object object) {
        final MenuAction action = new MenuAction(title, listener, object);
        ACTIONS.add(action);
        return action;
    }

    public static void remove(@NonNull MenuAction action) {
        ACTIONS.remove(action);
    }

    public static void clear() {
        ACTIONS.clear();
    }

    public static List<MenuAction> getActions() {
        final int size = ACTIONS.size();
        for (int i = size - 1; i >= 0; i--) {
            if (ACTIONS.get(i).getObject() == null) {
                ACTIONS.remove(i);
            }
        }

        Collections.sort(ACTIONS, new Comparator<MenuAction>() {
            @Override
            public int compare(MenuAction o1, MenuAction o2) {
                return o1.getTitle().compareToIgnoreCase(o2.getTitle());
            }
        });

        return Collections.unmodifiableList(ACTIONS);
    }
}
