package com.psliusar.reakshon.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.psliusar.reakshon.R;

public class FloatingMenu extends FrameLayout {

    public static void placeOn(@NonNull final Activity activity) {
        final FloatingMenu menu = new FloatingMenu(activity);

        final int size = activity.getResources().getDimensionPixelSize(R.dimen.reakshon_magic_menu_button_size);
        final LayoutParams lp = new LayoutParams(size, size);
        lp.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        menu.setLayoutParams(lp);
        menu.setBackgroundResource(R.drawable.reakshon_magic_menu_button);
        menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(activity, ActionsActivity.class);
                activity.startActivity(intent);
            }
        });

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ((ViewGroup) activity.findViewById(android.R.id.content)).addView(menu);
            }
        });
    }

    public FloatingMenu(Context context) {
        super(context);
    }
}
