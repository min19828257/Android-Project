package com.example.samsung.test0828;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

public class SubActivity extends AppCompatActivity {

    Button btn_prev;

    private String fname = "memo.txt";
    private EditText ed;

    static int cnt;

    //Using the Accelometer & Gyroscoper
    private SensorManager mSensorManager = null;

    //Using the Gyroscope
    private SensorEventListener mGyroLis;
    private Sensor mGgyroSensor = null;

    //Roll and Pitch
    private double pitch;
    private double roll;
    private double yaw;

    //timestamp and dt
    private double timestamp;
    private double dt;

    // for radian -> dgree
    private double RAD2DGR = 180 / Math.PI;
    private static final float NS2S = 1.0f/1000000000.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        btn_prev = (Button)findViewById(R.id.btn_prev);
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이전 페이지로 화면 전환
/*            Intent intent =
                    new Intent(
                            SubActivity.this, MainActivity.class);
            startActivity(intent);*/

                finish();
            }
        });
    }

    //버튼 눌렀을시
    public void onClick(View v)
    {
        //자이로센서 설정
        //Using the Gyroscope & Accelometer
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Using the Accelometer
        mGgyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mGyroLis = new GyroscopeListener();

        //Touch Listener for Accelometer
        findViewById(R.id.a_start).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){

                    case MotionEvent.ACTION_DOWN:
                        mSensorManager.registerListener(mGyroLis, mGgyroSensor, SensorManager.SENSOR_DELAY_UI);
                        break;

                    case MotionEvent.ACTION_UP:
                        mSensorManager.unregisterListener(mGyroLis);
                        break;
                }
                return false;
            }
        });
    }

    // 앱 종료시
    @Override
    protected void onStop() {
        super.onStop();
        try {
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(openFileOutput(fname, MODE_PRIVATE)));
            bw.write(ed.getText().toString());
            bw.write("여기에다가 글한번 써보자");
            bw.close();
        } catch (IOException e) {
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.e("LOG", "onPause()");
        mSensorManager.unregisterListener(mGyroLis);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.e("LOG", "onDestroy()");
        mSensorManager.unregisterListener(mGyroLis);
    }

    //자이로센서 리스너
    private class GyroscopeListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {

            /* 각 축의 각속도 성분을 받는다. */
            double gyroX = event.values[0];
            double gyroY = event.values[1];
            double gyroZ = event.values[2];

            /* 각속도를 적분하여 회전각을 추출하기 위해 적분 간격(dt)을 구한다.
             * dt : 센서가 현재 상태를 감지하는 시간 간격
             * NS2S : nano second -> second */
            dt = (event.timestamp - timestamp) * NS2S;
            timestamp = event.timestamp;

            /* 맨 센서 인식을 활성화 하여 처음 timestamp가 0일때는 dt값이 올바르지 않으므로 넘어간다. */
            if (dt - timestamp * NS2S != 0) {

                /* 각속도 성분을 적분 -> 회전각(pitch, roll)으로 변환.
                 * 여기까지의 pitch, roll의 단위는 '라디안'이다.
                 * SO 아래 로그 출력부분에서 멤버변수 'RAD2DGR'를 곱해주어 degree로 변환해줌.  */
                pitch = pitch + gyroY * dt;
                roll = roll + gyroX * dt;
                yaw = yaw + gyroZ * dt;

                Log.d("LOG", "GYROSCOPE           [X]:" + String.format("%.4f", event.values[0])
                        + "           [Y]:" + String.format("%.4f", event.values[1])
                        + "           [Z]:" + String.format("%.4f", event.values[2])
                        + "           [Pitch]: " + String.format("%.1f", pitch * RAD2DGR)
                        + "           [Roll]: " + String.format("%.1f", roll * RAD2DGR)
                        + "           [Yaw]: " + String.format("%.1f", yaw * RAD2DGR)
                        + "           [dt]: " + String.format("%.4f", dt));

                // 출력 io 생성
                ed = (EditText) findViewById(R.id.editText);
                File file = new File(getFilesDir(), fname);
                if (!file.exists())
                    return;

                ed.setText("GYROSCOPE\n  [X]:" + String.format("%.4f", event.values[0]) + "\n"
                        + "  [Y]:" + String.format("%.4f", event.values[1]) + "\n"
                        + "  [Z]:" + String.format("%.4f", event.values[2]) + "\n"
                        + "  [Pitch]: " + String.format("%.1f", pitch * RAD2DGR) + "\n"
                        + "  [Roll]: " + String.format("%.1f", roll * RAD2DGR) + "\n"
                        + "  [Yaw]: " + String.format("%.1f", yaw * RAD2DGR) + "\n"
                        + "  [dt]: " + String.format("%.4f", dt));
            }


            // 파일에 저장
            cnt++;

            if(cnt == 0) {
                String ess = Environment.getExternalStorageState();
                String sdCardPath = null;
                String logPath = sdCardPath + "/test1/text.txt"; //파일 경로
                if (ess.equals(Environment.MEDIA_MOUNTED)) {
                    sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    showMsg("SD Card stored in " + sdCardPath);
                } else {
                    showMsg("SD Card not ready!");
                }

                File file = new File(sdCardPath + "/test1");
                File file1 = new File(sdCardPath + "/test1/text.txt");

                if (!file.exists()) {
                    file.mkdir();
                    showMsg("기존에없던 폴더가 생성되었습니다.");
                }

                try {
                    FileOutputStream fos = new FileOutputStream(file1);
                    String msg = "GYROSCOPE\n[X]:" + "[Y]" + "[Z]"+ "[Pitch]" + "[Roll]"+ "[Yaw]"+"[dt]";
                    RandomAccessFile raf = new RandomAccessFile(logPath, "rw"); //이어쓰기용
                    raf.seek(raf.length());//맨마지막 위치로 커서 이동
                    raf.writeBytes(msg);
                    raf.close();
                } catch (FileNotFoundException fnfe) {
                    showMsg("지정된 파일을 생성할 수 없습니다.");
                } catch (IOException ioe) {
                    showMsg("파일에 데이터를 쓸 수 없습니다.");
                }
            }else {
                //파일 이어쓰기
                String ess = Environment.getExternalStorageState();
                String sdCardPath = null;
                if (ess.equals(Environment.MEDIA_MOUNTED)) {
                    sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    showMsg("SD Card stored in " + sdCardPath);
                } else {
                    showMsg("SD Card not ready!");
                }

                String logPath = sdCardPath + "/test1/text.txt"; //파일 경로
                File templog = new File(logPath);

                try {
                    RandomAccessFile raf = new RandomAccessFile(logPath, "rw"); //이어쓰기용
                    raf.seek(raf.length());//맨마지막 위치로 커서 이동
                    String str = "GYROSCOPE\n  [X]:" + String.format("%.4f", event.values[0]) + "\n" + "  [Y]:" + String.format("%.4f", event.values[1]) + "\n"
                            + "  [Z]:" + String.format("%.4f", event.values[2]) + "\n"
                            + "  [Pitch]: " + String.format("%.1f", pitch * RAD2DGR) + "\n"
                            + "  [Roll]: " + String.format("%.1f", roll * RAD2DGR) + "\n"
                            + "  [Yaw]: " + String.format("%.1f", yaw * RAD2DGR) + "\n"
                            + "  [dt]: " + String.format("%.4f", dt); //기록할 글
                    String str1 = String.format("%.4f", event.values[0]) +
                            "   " + String.format("%.4f", event.values[1]) +
                            "   " + String.format("%.4f", event.values[2]) +
                            "   "+  String.format("%.1f", pitch * RAD2DGR) +
                            "   " + String.format("%.1f", roll * RAD2DGR) +
                            "   " + String.format("%.1f", yaw * RAD2DGR) +
                            "   " + String.format("%.4f", dt)+"\n"; //기록할 글
                    raf.writeBytes(str1);
                    raf.close();

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

        private void showMsg(String msg)
        {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }
}

