package com.example.m.fitproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.m.fitproject.models.UserFitHistory;

import java.util.List;

/**
 * Created by M on 29.12.2016.
 */

public class HistoryItemAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<UserFitHistory> userFitHistory;

    public HistoryItemAdapter(Context context, List<UserFitHistory> userFitHistory) {
        this.context = context;
        this.userFitHistory = userFitHistory;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return userFitHistory.size();
    }

    @Override
    public Object getItem(int position) {
        return userFitHistory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
