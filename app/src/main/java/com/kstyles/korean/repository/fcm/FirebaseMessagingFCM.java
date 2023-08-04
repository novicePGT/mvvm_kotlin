package com.kstyles.korean.repository.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kstyles.korean.R;
import com.kstyles.korean.view.activity.MainActivity;

public class FirebaseMessagingFCM extends FirebaseMessagingService  {

    private final String TAG = "FirebaseMessageFCM";
    private FirebaseMessaging firebaseMessaging;
    private String token = "";

    public FirebaseMessagingFCM() {
        firebaseMessaging = FirebaseMessaging.getInstance();
        getToken();
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remotemessage) {

        if (remotemessage.getNotification() != null) {
            generateNotification(remotemessage.getNotification().getTitle(), remotemessage.getNotification().getBody());
        }
    }

    private void getToken() {
        firebaseMessaging.getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG,"Fetching FCM registration token failed", task.getException());
                    }

                    token = task.getResult();
                    sendRegistrationToServer(token);
                    Log.d(TAG, "getToken(): " + token);
                });
    }

    private void sendRegistrationToServer(String token) {
    }

    private void generateNotification(String title, String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "notification_channel")
                .setSmallIcon(R.drawable.icon_logo)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000})
                .setContentIntent(pendingIntent);

        builder = builder.setContent(getRemoteView(title, message));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notification_channel = new NotificationChannel("notification_channel", "com.kstyles.korean.repository.fcm", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notification_channel);
        }

        notificationManager.notify(0, builder.build());
    }

    private RemoteViews getRemoteView(String title, String message) {
        RemoteViews remoteView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.input_notification);

        remoteView.setTextViewText(R.id.noti_title, title);
        remoteView.setTextViewText(R.id.noti_Message, message);
        remoteView.setImageViewResource(R.id.noti_logo, R.drawable.icon_logo);

        return remoteView;
    }
}
