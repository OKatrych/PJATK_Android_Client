package eu.warble.pjappkotlin.utils

import android.webkit.MimeTypeMap
import android.app.DownloadManager
import android.content.Intent
import android.app.PendingIntent
import android.content.Context
import android.net.Uri
import android.support.v4.content.FileProvider
import android.os.Build
import java.io.File


object FileManager {
    fun openFilePendingIntent(location: String, context: Context): PendingIntent {
        val uri: Uri = if (Build.VERSION.SDK_INT >= 24)
            FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", File(location))
        else
            Uri.parse("file:///" + location)
        val type = getType(uri)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, type)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val manager = context.packageManager
        val infos = manager.queryIntentActivities(intent, 0)
        return if (infos.size > 0) {
            //Then there is an Application(s) can handle your intent
            PendingIntent.getActivity(context, 0, intent, 0)
        } else {
            //No Application can handle your intent
            PendingIntent.getActivity(context, 0, Intent(DownloadManager.ACTION_VIEW_DOWNLOADS), 0)
        }
    }

    private fun getType(uri: Uri): String {
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        var mimeType: String? = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase())
        if (mimeType == null)
            mimeType = "*/*"
        return mimeType
    }

    fun fileExists(fileLocation: String) = File(fileLocation).exists()

    fun deleteFile(path: String?) {
        if (path != null)
            File(path).delete()
    }
}