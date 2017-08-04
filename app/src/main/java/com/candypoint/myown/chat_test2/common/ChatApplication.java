package com.candypoint.myown.chat_test2.common;

import android.app.Application;
import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by Myown on 2017-07-31.
 */

public class ChatApplication extends Application {

    private Socket mSocket;
    {
        try{
            Log.i("SOCKET_CONN", "socket is connected");
            mSocket = IO.socket(Constants.CHAT_SERVER_URL);
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
    }

    public Socket getSocket(){
        return mSocket;
    }
}
