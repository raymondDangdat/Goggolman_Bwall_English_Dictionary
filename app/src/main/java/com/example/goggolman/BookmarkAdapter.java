package com.example.goggolman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class BookmarkAdapter extends BaseAdapter {
    private ListItemListener listener;
    private ListItemListener listenerBtnDelete;

    Context mContext;
    ArrayList<String> mSource;

    public BookmarkAdapter(Context context, ArrayList<String>  source){
        this.mSource = source;
        this.mContext = context;

    }

    @Override
    public int getCount() {
        return mSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //object of view holder class
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.bookmark_layout_item, parent, false);
            viewHolder.textViewWord = convertView.findViewById(R.id.textviewWord);
            viewHolder.btnDelete = convertView.findViewById(R.id.btn_delete);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.textViewWord.setText(mSource.get(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                listener.onItemClick(position);
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listenerBtnDelete != null)
                    listenerBtnDelete.onItemClick(position);
            }
        });
        return convertView;
    }

    public void removeItem(int position){
        mSource.remove(position);

    }



    //create a view holder class to handle views

    class ViewHolder{
        TextView textViewWord;
        ImageView btnDelete;
    }

    public void setOnItemClick(ListItemListener listItemListener){
        this.listener = listItemListener;

    }

    public void setOnItemDeleteClick(ListItemListener listItemListener){
        this.listenerBtnDelete = listItemListener;

    }


}
