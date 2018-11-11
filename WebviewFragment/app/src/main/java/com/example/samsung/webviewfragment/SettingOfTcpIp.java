package com.example.samsung.webviewfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingOfTcpIp extends AppCompatActivity {

    private EditText port;
    private EditText host;
    private Integer Transportport;
    private String Transporthost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tcpip);

        port = (EditText) findViewById(R.id.Port);
        host = (EditText) findViewById(R.id.Host);
    }

    public  void onClickSettingBtn(View v)
    {
        try{
            Transportport = Integer.parseInt(port.getText().toString());
            Transporthost = host.getText().toString();
            Transporthost.trim();

            if(Transporthost.getBytes().length <= 0 || Transportport.equals("") )
            {
                Toast.makeText(getApplicationContext(), "값을 입력하세요", 0 );
            }
        }catch (Exception E)
        {
            Toast.makeText(getApplicationContext(), "오류입니다.", 0 );
        }

        Toast toast = Toast.makeText(getApplicationContext(), "IP와 Port설정이 되었습니다.", 0 );
        toast.show();

        MainActivity main = new MainActivity();
        main.serverIP = Transporthost;
        main.serverPort = Transportport;

        Intent intent = new Intent(SettingOfTcpIp.this, MainActivity.class);
        startActivity(intent);
    }
}