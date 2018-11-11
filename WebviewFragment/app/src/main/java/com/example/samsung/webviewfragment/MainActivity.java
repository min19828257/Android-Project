package com.example.samsung.webviewfragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private WebSettings mWebSettings;

    private String return_msg;
    private EditText mEt;

    //서버 IP와 포트번호
    public String serverIP;
    public int serverPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //소켓통신준비
        initData();

        //웹뷰 세팅
        mWebView = (WebView) findViewById(R.id.webview);//레이어와 연결
        mWebView.setWebViewClient(new WebViewClient());//클릭시 새창 안뜨게

        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl("http://m.naver.com");
    }

    public void initData() {
        mEt = (EditText) findViewById(R.id.EditText01);
    }

    public  void onClickBtn(View v)
    {
        Toast toast = Toast.makeText(getApplicationContext(), mEt.getText().toString(), 0 );
        toast.show();
        Toast.makeText(getApplicationContext(), "사진을 스샷했습니다.", 0 ).show();
        TCPclient tcpThread = new TCPclient(mEt.getText().toString());
        Thread thread = new Thread(tcpThread);
        thread.start();
    }

    private class TCPclient implements Runnable {

        private Socket inetSocket = null;
        private String msg;

        //private String return_msg;
        public TCPclient(String _msg) {
            this.msg = _msg;
        }

        public void run() {

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
                    Toast.makeText(getApplicationContext(), return_msg, 0 ).show();
                } catch (Exception e) {
                    Log.e("TCP", "C: Error1", e);
                } finally {
                    inetSocket.close();
                }
            } catch (Exception e) {
                Log.e("TCP", "C: Error2", e);
            }
        }
    }

}
