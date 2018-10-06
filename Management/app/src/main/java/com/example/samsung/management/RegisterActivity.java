package com.example.samsung.management;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText ageText = (EditText) findViewById(R.id.ageText);

        Button registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = idText.getText().toString();
                final String userPassword = passwordText.getText().toString();
                final String userName = nameText.getText().toString();
                final String userAge1 = ageText.getText().toString();
                final int userAge = Integer.parseInt(ageText.getText().toString());

                Toast toast = Toast.makeText(getApplicationContext(),"Onclick",Toast.LENGTH_SHORT);
                toast.show();

                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            if (userID == "" || userPassword == "" || userName == "" || userAge1 == "") {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 실패했습니다")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                            } else {

                                Toast toast = Toast.makeText(getApplicationContext(), "got", Toast.LENGTH_LONG);
                                toast.show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

                                builder.setMessage("회원 등록에 성공했습니다")
                                        .setTitle("Success!!")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                               /* Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                RegisterActivity.this.startActivity(intent);*/
                                               finish();
                                            }
                                        })
                                        .create()
                                        .show();
                            }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userAge, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}

 /*
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = idText.getText().toString();
                final String userPassword = passwordText.getText().toString();
                final String userName = nameText.getText().toString();
                final int userAge = Integer.parseInt(ageText.getText().toString());

                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonReponse = new JSONObject(response);
                            boolean success = jsonReponse.getBoolean("success");
                            if(success)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 성공했습니다")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 실패했습니다")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                            }
                        }
                        catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userAge, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });*/
