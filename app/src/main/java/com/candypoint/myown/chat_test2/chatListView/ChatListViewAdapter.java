package com.candypoint.myown.chat_test2.chatListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.candypoint.myown.chat_test2.R;

import java.util.ArrayList;

/**
 * Created by Myown on 2017-08-01.
 */

public class ChatListViewAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> itemList = new ArrayList<ListViewItem>();

    // 디폴트
    public ChatListViewAdapter(){

    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_listview_item, parent,false);
        }

        TextView txtv_username = (TextView)convertView.findViewById(R.id.txtv_chat_username);
        TextView txtv_message = (TextView)convertView.findViewById(R.id.txtv_chat_msg);

        ListViewItem item = itemList.get(pos);
        txtv_username.setText(item.getUsername());
        txtv_message.setText(item.getMessage());

        return convertView;
    }

    public void addItem(String username, String message){
        ListViewItem item = new ListViewItem();
        item.setUsername(username);
        item.setMessage(message);
        itemList.add(item);
    }
}
