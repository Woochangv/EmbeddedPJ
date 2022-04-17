package com.example.embeddedpj;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    ServiceThread thread;
    NotificationCompat.Builder builder;
    NotificationManager manager;
    String CHANNEL_ID = "Channel01";
    String CHANNEL_NAME = "channel01";
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    //서비스가 시작될 때 백그라운드에서 실행되는 동작들이 들어감
    public int onStartCommand(Intent intent, int flags, int startId) {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationHandler handler = new createNotificationHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    @Override
    //서비스가 종료될 때
    public void onDestroy() {
        thread.stopForever();
        thread = null;
    }

    //알림 생성
    class  createNotificationHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            // Android8.0(오레오)이상 버전 체크
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT));
                builder = new NotificationCompat.Builder(MyService.this, CHANNEL_ID);
            }else {
                builder = new NotificationCompat.Builder(MyService.this, CHANNEL_ID);
            }

            //알림 터치 시 설정한 Activity로 이동
            Intent intent = new Intent(MyService.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 0, intent, 0);


            //알림 아이콘
            builder.setSmallIcon(R.drawable.ic_small_dry);
            //알림 제목
            builder.setContentTitle("Notice");
            //알림 내용
            builder.setContentText("건조가 완료되었습니다!");

            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);
            //알림 실행
            manager.notify(803, builder.build());
        }
    }
}