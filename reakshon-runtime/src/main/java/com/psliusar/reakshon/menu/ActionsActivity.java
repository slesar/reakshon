package com.psliusar.reakshon.menu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.psliusar.reakshon.R;
import com.psliusar.reakshon.menu.action.MenuAction;
import com.psliusar.reakshon.menu.action.Repo;

import java.util.List;

public class ActionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.reakshon_activity_actions);

        final MenuAdapter adapter = new MenuAdapter(this, Repo.getActions());

        final ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adapter);
    }

    private class MenuAdapter extends BaseAdapter implements View.OnClickListener {

        private final Context context;
        private final LayoutInflater inflater;
        private List<MenuAction> actions;

        public MenuAdapter(@NonNull Context context, @NonNull List<MenuAction> actions) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.actions = actions;
        }

        @Override
        public int getCount() {
            return actions.size();
        }

        @Override
        public MenuAction getItem(int position) {
            return actions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return actions.get(position).getTitle().hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                convertView.setOnClickListener(this);
            }
            convertView.setTag(getItem(position));

            final TextView titleView = (TextView) convertView.findViewById(android.R.id.text1);
            titleView.setText(getItem(position).getTitle());

            return convertView;
        }

        @Override
        public void onClick(View v) {
            finish();
            final MenuAction action = (MenuAction) v.getTag();
            action.getListener().onActionSelected(v, action.getObject());
        }
    }
}
