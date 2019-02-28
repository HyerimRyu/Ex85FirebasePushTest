package kr.co.teada.ex85firebasepushtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);

        //메세지를 받으면 실행되는 메소드
        //메세지 내용은 파라미터로 전달된 remoteMessage 로 부터 얻어오면 됨
        Log.e("TAG", "received Message");

        //전달된 메세지 가져오기
        Map<String, String> datas=remoteMessage.getData();
        if (datas!=null){
            String name=datas.get("name");
            String msg=datas.get("msg");

            //받은 메세지를 알림(Notification)으로 보내주기!
            NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder builder=null;
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                String channelId= "ch01";
                String channelName= "channel01";
                NotificationChannel notiChannel = new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(notiChannel);
                builder = new NotificationCompat.Builder(this,channelId);
            }else {
                builder = new NotificationCompat.Builder(this, null);
            }

            builder.setSmallIcon(android.R.drawable.ic_dialog_email);
            builder.setContentTitle(name);
            builder.setContentText(msg);
            builder.setAutoCancel(true);

            Notification notification=builder.build();

            notificationManager.notify(100, notification);

        }



    }//end of  onMessageReceived



}//end of MyFirebaseMessagingService
