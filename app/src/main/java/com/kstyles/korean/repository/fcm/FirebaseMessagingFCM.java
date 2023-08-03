package com.kstyles.korean.repository.fcm;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
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
}
