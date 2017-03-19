package com.psliusar.reakshon.sample;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.psliusar.reakshon.Rshn;
import com.psliusar.reakshon.menu.action.MenuAction;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class AppDebug extends App {

    @Override
    public void onCreate() {
        super.onCreate();

        Rshn.withActivity(MainActivity.class, "onCreate")
                .showMenu()
                .menuAction("Finish Activity", "onDestroy", new MenuAction.OnMenuActionListener<MainActivity>() {
                    @Override
                    public void onActionSelected(@NonNull View view, @Nullable MainActivity object) {
                        object.finish();
                    }
                })
                .subscribe(new Consumer<MainActivity>() {
                    @Override
                    public void accept(MainActivity mainActivity) throws Exception {
                        Log.i("Reakshon", "onCreate: " + mainActivity);
                    }
                });
        Rshn.with(MainActivity.PlaceholderFragment.class, "onViewCreated")
                .menuAction("Show alert dialog", "onDestroyView", new MenuAction.OnMenuActionListener<MainActivity.PlaceholderFragment>() {
                    @Override
                    public void onActionSelected(@NonNull View view, @Nullable MainActivity.PlaceholderFragment object) {
                        object.showAlertDialog();
                    }
                })
                .subscribe(new Consumer<MainActivity.PlaceholderFragment>() {
                    @Override
                    public void accept(MainActivity.PlaceholderFragment fragment) throws Exception {
                        Log.i("Reakshon", "onViewCreated: " + fragment);
                    }
                });
        Rshn.intercept(Fragment.class, "onCreateView")
                .with(new Function<View, View>() {
                    @Override
                    public View apply(View view) throws Exception {
                        view.setBackgroundColor(0xffeeeeee);
                        return view;
                    }
                })
                .subscribe(new Consumer<Fragment>() {
                    @Override
                    public void accept(Fragment fragment) throws Exception {
                        Log.i("Reakshon", "onCreateView: " + fragment);
                    }
                });
    }
}
