package com.example.samsung.myapplication;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends Activity {

    private String return_msg;
    private EditText mEt;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
    }

    public void initData()
    {
        mEt = (EditText) findViewById(R.id.EditText01);
    }

    public  void onClickBtn(View v)
    {
        Toast toast = Toast.makeText(getApplicationContext(), mEt.getText().toString(), 0 );
        toast.show();
        TCPclient tcpThread = new TCPclient(mEt.getText().toString());
        Thread thread = new Thread(tcpThread);
        thread.start();
    }

    private class TCPclient implements Runnable {
        private static final String serverIP = "192.168.35.9";
        private static final int serverPort = 12345;
        private Socket inetSocket = null;
        private String msg;

        //private String return_msg;
        public TCPclient(String _msg) {
            this.msg = _msg;
        }

        public void run() {
            // TODO Auto-generated method stub

            try {
                Log.d("TCP", "C: Connecting...");

                inetSocket = new Socket(serverIP, serverPort);
                //inetSocket.connect(socketAddr);

                try {
                    Log.d("TCP", "C: Sending: '" + msg + "'");
                    PrintWriter out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(inetSocket.getOutputStream())), true);
                    out.println(msg);

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(inetSocket.getInputStream()));
                    return_msg = in.readLine();

                    Log.d("TCP", "C: Server send to me this message -->" + return_msg);
                } catch (Exception e) {
                    Log.e("TCP", "C: Error1", e);
                } finally {
                    inetSocket.close();
                }
            } catch (Exception e) {
                Log.e("TCP", "C: Error2", e);
            }
        }
    }}