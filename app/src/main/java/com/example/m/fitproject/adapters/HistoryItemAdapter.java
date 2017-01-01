package com.example.m.fitproject.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.m.fitproject.R;
import com.example.m.fitproject.models.UserFitHistory;

import java.text.SimpleDateFormat;
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
        View rowView = inflater.inflate(R.layout.history_row,parent,false);

        TextView nameTextView = (TextView) rowView.findViewById(R.id.itemTitle);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.itemImage);

        UserFitHistory userFitHistory = (UserFitHistory) getItem(position);

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(userFitHistory.getDate());

        String photo = userFitHistory.getPhoto();

        if(photo != null)
        {
            Bitmap bitmap = BitmapFactory.decodeFile(photo, null);
            imageView.setImageBitmap(bitmap);
        }


        nameTextView.setText(date);

        return rowView;
    }
}
