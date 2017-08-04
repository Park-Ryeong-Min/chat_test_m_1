package com.candypoint.myown.chat_test2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.candypoint.myown.chat_test2.chatListView.ChatListViewAdapter;
import com.candypoint.myown.chat_test2.common.ChatApplication;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Myown on 2017-07-31.
 */

public class ChatActivity extends Activity {

    private ChatApplication chatApp;
    private Socket mSocket;

    private Button btn_send;
    private EditText edt_input_msg;

    private ListView listView;
    private ChatListViewAdapter listAdapter;

    private String my_access_key;
    private String my_room_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        final String access_key = intent.getStringExtra("access_key");
        final String room_name = intent.getStringExtra("room_name");
        my_access_key = access_key;
        my_room_name = room_name;

        btn_send = (Button)findViewById(R.id.btn_chat_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String send_msg = edt_input_msg.getText().toString();
                JSONObject send_obj = new JSONObject();
                try{
                    send_obj.put("username", access_key);
                    send_obj.put("room_name", room_name);
                    send_obj.put("message", send_msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                listAdapter.addItem(access_key, send_msg);
                listAdapter.notifyDataSetChanged();
                mSocket.emit("send_message", send_obj);
            }
        });

        edt_input_msg = (EditText)findViewById(R.id.edt_chat_input_msg);

        chatApp = (ChatApplication)getApplication();
        mSocket = chatApp.getSocket();

        listView = (ListView)findViewById(R.id.listview_chat);
        listAdapter = new ChatListViewAdapter();
        listView.setAdapter(listAdapter);

        mSocket.connect();
        mSocket.on("send_message", onNewMessage);
        mSocket.on("room_log", getLogEvent);
        mSocket.emit("join_room", makeSendObject( access_key, room_name, "join the room"));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JSONObject send_data = new JSONObject();
        try{
            send_data.put("username" , my_access_key);
            send_data.put("room_name", my_room_name);
            send_data.put("message", my_access_key + " is out.");
        }catch (JSONException e){
            e.printStackTrace();
        }
        mSocket.emit("disconnect", send_data);
        mSocket.disconnect();
    }

    private JSONObject makeSendObject(String username, String room_name, String msg){
        JSONObject ret = new JSONObject();
        try{
            ret.put("message", msg);
            ret.put("username", username);
            ret.put("room_name", room_name);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return ret;
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject)args[0];
                    String username = null;
                    String room_name = null;
                    String message = null;
                    try{
                        username = data.getString("username");
                        room_name = data.getString("room_name");
                        message = data.getString("message");
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    Log.i("RECEIVED", "[" + username + "][" + room_name + "]:" + message);
                    listAdapter.addItem(username, message);
                    listAdapter.notifyDataSetChanged();
                }
            });
        }
    };

    private Emitter.Listener getLogEvent = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject)args[0];
            JSONArray arr = null;
            Log.i("DAYS", "hi");
            try{
                arr = data.getJSONArray("list");
            }catch (JSONException e){
                e.printStackTrace();
            }
            Log.i("RECEIVED_B", arr.toString());
            for(int i = 0; i < arr.length(); i++){
                JSONObject tmp = null;
                String tmp_username = null;
                String tmp_message = null;
                try{
                    tmp = arr.getJSONObject(i);
                    tmp_username = tmp.getString("username").toString();
                    tmp_message = tmp.getString("message").toString();
                }catch (JSONException e){
                    e.printStackTrace();
                }
                Log.i("RECEIVED", "[" + tmp_username + "]" +  tmp_message);
                listAdapter.addItem(tmp_username, tmp_message);
            }
            listAdapter.notifyDataSetChanged();
        }
    };
}
