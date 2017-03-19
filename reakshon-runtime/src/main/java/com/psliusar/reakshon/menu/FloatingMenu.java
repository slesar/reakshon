package com.psliusar.reakshon.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
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

        final VectorDrawableCompat icon = VectorDrawableCompat.create(activity.getResources(), R.drawable.reakshon_magic_menu_button, null);
        menu.setBackgroundDrawable(icon);

        menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(activity, ActionsActivity.class);
                activity.startActivity(intent);
            }
        });

        ((ViewGroup) activity.findViewById(android.R.id.content)).addView(menu);
    }

    public FloatingMenu(Context context) {
        super(context);
    }
}
