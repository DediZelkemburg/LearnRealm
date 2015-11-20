package com.example.dedi.learnrealm.Models;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dedi.learnrealm.Models.PojoDB.Users;
import com.example.dedi.learnrealm.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter {

    List<Users> userses = new ArrayList<>();
    Activity act;

    public Adapter(Activity act, List<Users> usersList) {
        this.act = act;
        this.userses = usersList;
    }

    public int getCount() {
        return userses.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View v, ViewGroup parent) {
        final DekRow DR;

        if (v == null) {
            v = (act).getLayoutInflater().inflate(R.layout.items_data, parent, false);
            DR = new DekRow();
            DR.pass = (TextView) v.findViewById(R.id.username_txt);
            DR.usernme = (TextView) v.findViewById(R.id.password_txt);
            v.setTag(DR);
        } else {
            DR = (DekRow) v.getTag();
        }

        DR.pass.setText(userses.get(position).getName());
        DR.usernme.setText(userses.get(position).getPass());

        return v;
    }

    private class DekRow {
        TextView pass, usernme;
    }
}
