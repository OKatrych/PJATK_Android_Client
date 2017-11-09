package eu.warble.pjapp.ui.ftp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.jcraft.jsch.ChannelSftp;

import java.util.Vector;

import eu.warble.pjapp.R;
import eu.warble.pjapp.data.ftp.FtpManager;
import eu.warble.pjapp.ui.base.BaseFragmentPresenter;
import eu.warble.pjapp.util.AppExecutors;
import eu.warble.pjapp.util.Constants;
import eu.warble.pjapp.util.CredentialsManager;
import eu.warble.pjapp.util.FileManager;
import eu.warble.pjapp.util.NetworkHelper;
import eu.warble.pjapp.util.NotificationsManager;

public class FtpPresenter extends BaseFragmentPresenter<FtpFragment> {

    FtpPresenter(FtpFragment fragment) {
        super(fragment);
    }

    @Override
    protected void initPresenter(@Nullable Bundle savedInstanceState) {
        loadDirectory("/");
    }

    void loadDirectory(String path) {
        if (!NetworkHelper.isNetworkAvailable(fragment.context)){
            FtpManager.destroyInstance();
            fragment.hideLoadingScreen();
            fragment.showError(fragment.getString(R.string.connect_error));
            return;
        }
        FtpManager ftpManager = FtpManager.getInstance(new AppExecutors(),
                CredentialsManager.getInstance().getCredentials(fragment.context));
        //if we are in main directory
        if ("/".equals(ftpManager.currentDirectory()) && ("..".equals(path) || ".".equals(path)))
            return;
        fragment.showLoadingScreen();
        ftpManager.loadFilesList(path, new FtpManager.LoadFilesListCallback() {
            @Override
            public void onFilesListLoaded(Vector<ChannelSftp.LsEntry> list) {
                fragment.updateList(list);
                fragment.hideLoadingScreen();
            }

            @Override
            public void onError(String error) {
                fragment.hideLoadingScreen();
                switch (error){
                    case Constants.CONNECTION_ERROR :
                        fragment.showError(fragment.getString(R.string.connect_error));
                        break;
                    default:
                        fragment.showError(Constants.UNKNOWN_ERROR);
                        break;
                }
            }
        });
    }

    void downloadFile(String fileName) {
        if (!NetworkHelper.isNetworkAvailable(fragment.context)){
            FtpManager.destroyInstance();
            fragment.hideLoadingScreen();
            fragment.showError(fragment.getString(R.string.connect_error));
            return;
        }
        FtpManager ftpManager = FtpManager.getInstance(new AppExecutors(),
                CredentialsManager.getInstance().getCredentials(fragment.context));
        if (ftpManager.downloading.get()) {
            fragment.showMessage(fragment.getString(R.string.download_restriction));
            return;
        }
        NotificationsManager notificationManager = NotificationsManager.getInstance();
        NotificationCompat.Builder notification = notificationManager.createDownloadNotification(fragment.context,
                fragment.getString(R.string.download), String.format("%s %s", fragment.getString(R.string.download_wait), fileName));
        fragment.showMessage(String.format("%s %s", fragment.getString(R.string.download), fileName));
        ftpManager.downloadFile(fileName, new FtpManager.DownloadFileMonitor() {
            String fileLocation;

            // variables for percentage calculating
            long maxBytes;
            long currBytes;
            int lastProgress;

            @Override
            public void onStart(int op, String src, String dest, long max) {
                maxBytes = max;
                currBytes = max;
                lastProgress = 0;
                fileLocation = dest;
                notification.setContentText(fragment.getString(R.string.download_progress) + " " + fileName)
                        .setProgress(100, 0, false);
                notificationManager.updateDownloadNotification(fragment.context, notification);
            }

            @Override
            public boolean onChange(long count) {
                currBytes -= count;
                int percent = (int) (((maxBytes - currBytes) * 100) / maxBytes);
                if (percent != lastProgress) {
                    lastProgress = percent;
                    notification.setProgress(100, percent, false);
                    notificationManager.updateDownloadNotification(fragment.context, notification);
                }
                return true;
            }

            @Override
            public void onFinish() {
                if (!ftpManager.shouldContinueDownloading.get()) {
                    FileManager.deleteFile(fileLocation);
                    return;
                }
                notificationManager.finishDownloadNotification(fragment.context, notification, fileLocation, fileName);
            }
        });
    }

    @Override
    protected void onDetachFragment() {
        FtpManager.destroyInstance();
        super.onDetachFragment();
    }
}
