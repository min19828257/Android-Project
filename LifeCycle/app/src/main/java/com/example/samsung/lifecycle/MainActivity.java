package com.example.samsung.lifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();

        Button btExit = (Button) findViewById(R.id.Confirm);
        btExit.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause(){
        super.onPause();
        Toast.makeText(this,"onPause()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected  void onRestart(){
        super.onRestart();
        Toast.makeText(this,"onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected  void onResume(){
        super.onResume();
        Toast.makeText(this,"onResume()",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop(){
        super.onStop();
        Toast.makeText(this,"onStop()",Toast.LENGTH_SHORT).show();
    }
}
