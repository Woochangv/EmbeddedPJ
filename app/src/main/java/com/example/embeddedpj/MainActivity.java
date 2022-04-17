package com.example.embeddedpj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static TextView result;
    public static TextView temp;
    public static TextView humi;
    //public static Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        temp = findViewById(R.id.temp);
        humi = findViewById(R.id.humi);

        Intent intent = new Intent(MainActivity.this, MyService.class);
        startService(intent);

        /*
        firebaseDAO firebase = new firebaseDAO();


        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int count  = 0;

            @Override
            public void run() {
                System.out.println("count >> " + count);
                firebase.LoadData();
                count++;
            }
        };
        timer.schedule(timerTask, 0, 10000); //Timer 실행

        //callServiceThread thread = new callServiceThread();
        //thread.start();
         */
    }

    /*
    class callServiceThread extends Thread {
        boolean isRun = false;

        public void run(){
            isRun = true;
            while(isRun){
                if(firebaseDAO.result == true){
                    Intent intent = new Intent(MainActivity.this, MyService.class);
                    startService(intent);
                }
                try{
                    Thread.sleep(100);
                }catch (Exception e) {}
            }
        }
    }
     */
}