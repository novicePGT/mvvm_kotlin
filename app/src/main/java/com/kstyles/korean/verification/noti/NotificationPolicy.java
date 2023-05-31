package com.kstyles.korean.verification.noti;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class NotificationPolicy {

    public static boolean isNotificationPolicyAccessGranted(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return notificationManager.isNotificationPolicyAccessGranted();
        }
        return true; // 안드로이드 버전이 M 이전인 경우 권한이 항상 허용된 것으로 간주합니다.
    }
}
