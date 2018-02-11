package eu.warble.pjapp.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import eu.warble.pjapp.R;
import eu.warble.pjapp.data.ftp.FtpManager;

public class NotificationsManager {

    private int counter = 1;
    private static final String channelId = "pjapp";

    private static class SingletonHelper {

        private static final NotificationsManager INSTANCE = new NotificationsManager();
    }

    private NotificationsManager() {
    }

    public static NotificationsManager getInstance() {
        return SingletonHelper.INSTANCE;
    }

    /**
     * @return notification builder
     */
    public NotificationCompat.Builder createDownloadNotification(
            @NonNull Context context,
            String title,
            String contentText
    ) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id = counter++;
        Bundle idBundle = new Bundle();
        idBundle.putInt("id", id);
        createChannel(notificationManager);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.network_download)
                .setContentText(contentText)
                .setOngoing(true)
                .setContentIntent(null)
                .addExtras(idBundle)
                .addAction(R.drawable.cancel, context.getString(R.string.cancel), getCancelPendingIntent(context, id))
                .setProgress(100, 30, true);
        if (notificationManager != null) {
            notificationManager.notify(id, notification.build());
        }

        return notification;
    }

    public void updateDownloadNotification(
            @NonNull Context context,
            NotificationCompat.Builder builder
    ) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(builder.getExtras().getInt("id"), builder.build());
        }
    }

    public void finishDownloadNotification(
            @NonNull Context context,
            NotificationCompat.Builder builder,
            String fileLocation,
            String fileName,
            boolean onError
    ) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            builder.setSmallIcon(R.drawable.file)
                    .setProgress(0, 0, false)
                    .setContentText(fileName)
                    .setOngoing(false);
            builder.mActions.clear();
            if (onError) {
                builder.setContentTitle(context.getString(R.string.download_error));
            } else {
                builder.setContentTitle(context.getString(R.string.download_complete))
                        .setContentIntent(FileManager.openFilePendingIntent(fileLocation, context));
            }
            notificationManager.notify(builder.getExtras().getInt("id"), builder.build());
        }
    }

    private void createChannel(NotificationManager manager) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (manager != null && manager.getNotificationChannel(channelId) == null) {
                NotificationChannel channel = new NotificationChannel(
                        "pjapp", "PJATK", NotificationManager.IMPORTANCE_LOW);
                manager.createNotificationChannel(channel);
            }
        }
    }

    private PendingIntent getCancelPendingIntent(
            Context context,
            int id
    ) {
        Intent cancelIntent = new Intent(context, NotificationReceiver.class);
        cancelIntent.putExtra("id", id);
        return PendingIntent.getBroadcast(
                context, id, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(
                Context context,
                Intent intent
        ) {
            if (context != null) {
                int id = intent.getIntExtra("id", -1);
                NotificationManager mgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (mgr != null) {
                    mgr.cancel(id);
                }
                FtpManager.getInstance(new AppExecutors(), CredentialsManager.getInstance().getCredentials(context))
                        .cancelDownloading();
            }
        }
    }
}
