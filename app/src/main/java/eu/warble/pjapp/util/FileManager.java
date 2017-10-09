package eu.warble.pjapp.util;


import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.List;

public class FileManager {
    public static PendingIntent openFilePendingIntent(String location, Context context){
        Uri uri;
        if (Build.VERSION.SDK_INT>=24)
            uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", new File(location));
        else
            uri = Uri.parse("file:///" + location);
        String type = getType(uri);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, type);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager manager = context.getPackageManager();
        List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);
        if (infos.size() > 0) {
            //Then there is an Application(s) can handle your intent
            return PendingIntent.getActivity(context, 0, intent, 0);
        } else {
            //No Application can handle your intent
            return PendingIntent.getActivity(context, 0, new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS), 0);
        }
    }

    private static String getType(Uri uri){
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        if (mimeType == null)
            mimeType = "*/*";
        return mimeType;
    }

    public static boolean fileExists(String fileLocation){
        return new File(fileLocation).exists();
    }

    public static void deleteFile(String path){
        if (path != null)
            new File(path).delete();
    }
}
