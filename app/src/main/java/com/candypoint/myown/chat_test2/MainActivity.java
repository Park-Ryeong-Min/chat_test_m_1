package com.candypoint.myown.chat_test2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.candypoint.myown.chat_test2.common.ChatApplication;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ChatApplication chatApp;

    private EditText edt_access_key;
    private EditText edt_room_name;
    private Button btn_join;
    private Button btn_get_room_list;

    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_access_key = (EditText) findViewById(R.id.edt_main_access_key);
        edt_room_name = (EditText)findViewById(R.id.edt_main_room_name);
        btn_join = (Button)findViewById(R.id.btn_main_join);
        btn_get_room_list = (Button)findViewById(R.id.btn_main_get_room_list);

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String access_key = edt_access_key.getText().toString();
                String room_name = edt_room_name.getText().toString();

                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra("access_key", access_key);
                intent.putExtra("room_name", room_name);

                startActivity(intent);
            }
        });

        btn_get_room_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
