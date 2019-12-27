package com.ygaps.travelapp.viewmodel;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.model.isAccept;
import com.ygaps.travelapp.response.RecoveryResponse;
import com.ygaps.travelapp.retrofit.Service.Tour.TourAPI;
import com.ygaps.travelapp.retrofit.Service.User.UserAPI;
import com.ygaps.travelapp.retrofit.retrofitRequest;
import com.ygaps.travelapp.view.Activity.MainActivity;
import com.ygaps.travelapp.view.Activity.SplashActivity;
import com.ygaps.travelapp.view.Fragment.ProfileFragment;

import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseService extends FirebaseMessagingService {
    private static final String TAG = "Firebaselog";
    private static NotificationManager notificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // handle a notification payload.
        Log.d(TAG, remoteMessage.getData().toString());
        if (remoteMessage.getData() != null) {
            Log.d(TAG, "Lay data firebase");
            sendNotification(remoteMessage.getData());
        }
    }
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
    public static class DenyAction extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Myapp","Deny");
            Integer tourID = intent.getExtras().getInt("TourID");
            isAccept param = new isAccept();
            param.setAccepted(false);
            param.setTourId(tourID.toString());
            String header = SplashActivity.appPreference.getToken();
            TourAPI tour = retrofitRequest.getRetrofitInstance().create(TourAPI.class);
            Call<RecoveryResponse> tourCall = tour.responseTourInvite(header,param);
            tourCall.enqueue(new Callback<RecoveryResponse>() {
                @Override
                public void onResponse(Call<RecoveryResponse> call, Response<RecoveryResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
//                            SplashActivity.appPreference.showToast("Deny invite of tour ID : "+ tourID.toString());
                            SplashActivity.appPreference.showToast("Deny invite successful");
                        } else {
                            SplashActivity.appPreference.showToast("Failed");
                        }
                    }
                }
                @Override
                public void onFailure(Call<RecoveryResponse> call, Throwable t) {
                }
            });
            SplashActivity.appPreference.showToast("Deny");
        }
    }

    public static class ViewAction extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Myapp","View");
            SplashActivity.appPreference.showToast("View");
        }
    }
    private void sendNotification(Map<String, String> data) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        int m = Integer.parseInt(data.get("id"));
        //Custom
        RemoteViews myNoti = new RemoteViews(getPackageName(), R.layout.notification);
        Intent closeButton = new Intent("Deny_action");
        closeButton.putExtra("TourID", m);
        closeButton.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent viewButton = new Intent("View_action");
        viewButton.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent closeIntent = PendingIntent.getBroadcast(this, 0, closeButton, PendingIntent.FLAG_CANCEL_CURRENT);
        myNoti.setOnClickPendingIntent(R.id.btnDeny, closeIntent);
        PendingIntent viewIntent = PendingIntent.getBroadcast(this, 0, viewButton, PendingIntent.FLAG_ONE_SHOT);
        myNoti.setOnClickPendingIntent(R.id.btnView, viewIntent);


        String channelId = getString(R.string.app_name);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String tourName = "A user invited you to tour : "+ data.get("name");
        myNoti.setTextViewText(R.id.message,tourName);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                        .setAutoCancel(true)
                        .setCustomContentView(myNoti)
                        .setSound(defaultSoundUri)
                        .setPriority(NotificationManager.IMPORTANCE_HIGH);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(m, notificationBuilder.build());
    }
}