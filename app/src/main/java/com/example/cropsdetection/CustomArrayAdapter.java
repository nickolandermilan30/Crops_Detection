package com.example.cropsdetection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<String> {
    private List<String> items;
    private Context context;

    public CustomArrayAdapter(Context context, List<String> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.list_item_with_number, parent, false);
        }

        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText((position + 1) + ". " + items.get(position));

        return view;
    }
}
