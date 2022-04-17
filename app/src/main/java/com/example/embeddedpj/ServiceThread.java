package com.example.embeddedpj;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceThread extends Thread{
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public static boolean result = false;

    Handler handler;
    boolean isRun = true;

    public ServiceThread(Handler handler){
        this.handler = handler;
    }

    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
            System.out.println("stop");
        }
    }

    public void run(){
        //반복적으로 수행할 작업
        while(isRun){
            LoadData();
            try{
                if(result == true){
                    stopForever();
                }
                Thread.sleep(15000); //15초마다
            }catch (Exception e) {}
        }
    }
    public void LoadData(){
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("drying");
        databaseReference.addListenerForSingleValueEvent(postListener);
    }

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            String temp1 = dataSnapshot.child("Temperature1").getValue(String.class);
            String humi1 = dataSnapshot.child("Humidity1").getValue(String.class);
            String temp2 = dataSnapshot.child("Temperature2").getValue(String.class);
            String humi2 = dataSnapshot.child("Humidity2").getValue(String.class);

            double humi_int1 = Double.parseDouble(humi1);
            double humi_int2 = Double.parseDouble(humi2);

            if(humi_int1 <= 45 && humi_int2 <= 45){
                MainActivity.result.setText("다 말랐습니다! 와서 확인 해주세요!");
                handler.sendEmptyMessage(0); //쓰레드에 있는 핸들러에게 메시지를 보냄
                result = true;
            }

            if(humi_int1 > humi_int2){
                MainActivity.temp.setText(temp1);
                MainActivity.humi.setText(humi1);
                System.out.println("humi1 >> temp : " + temp1 + " humi : " + humi1);
            }

            else{
                MainActivity.temp.setText(temp2);
                MainActivity.humi.setText(humi2);
                System.out.println("humi2 >> temp : " + temp2 + " humi : " + humi2);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException());
        }
    };
}
