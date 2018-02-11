package eu.warble.pjapp.ui.ftp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.jcraft.jsch.ChannelSftp;

import java.util.List;
import java.util.Vector;

import eu.warble.getter.DownloadCallback;
import eu.warble.getter.Getter;
import eu.warble.getter.LoadDirectoryCallback;
import eu.warble.getter.model.Credentials;
import eu.warble.getter.model.GetterFile;
import eu.warble.pjapp.R;
import eu.warble.pjapp.data.ftp.FtpManager;
import eu.warble.pjapp.ui.base.BaseFragmentPresenter;
import eu.warble.pjapp.util.AppExecutors;
import eu.warble.pjapp.util.CredentialsManager;
import eu.warble.pjapp.util.FileManager;
import eu.warble.pjapp.util.NetworkHelper;
import eu.warble.pjapp.util.NotificationsManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

import static eu.warble.pjapp.util.Constants.*;

public class FtpPresenter extends BaseFragmentPresenter<FtpFragment> {

    FtpPresenter(FtpFragment fragment) {
        super(fragment);
    }

    @Override
    protected void initPresenter(@Nullable Bundle savedInstanceState) {
        initFTP(() -> {
            loadDirectory("/");
            return Unit.INSTANCE;
        });
    }

    private void initFTP(Function0<Unit> onSuccess) {
        CredentialsManager.Credentials credentials =
                CredentialsManager.getInstance().getCredentials(fragment.context);
        Getter.INSTANCE.init(
                SFTP_SERVER_URL,
                new Credentials(credentials.login, credentials.password),
                () -> {
                    onSuccess.invoke();
                    return Unit.INSTANCE;
                },
                throwable -> {
                    onError(throwable.getMessage());
                    return Unit.INSTANCE;
                }
        );
    }

    void loadDirectory(String path) {
        if (!NetworkHelper.isNetworkAvailable(fragment.context)) {
            onError(CONNECTION_ERROR);
            return;
        }

        fragment.showLoadingScreen();

        Getter.INSTANCE.loadDirectory(path, new LoadDirectoryCallback() {
            @Override
            public void onDirectoryLoaded(final List<GetterFile> list) {
                fragment.updateList(list);
                fragment.hideLoadingScreen();
            }

            @Override
            public void onError(final String error) {
                FtpPresenter.this.onError(error);
            }
        });
    }

    void downloadFile(GetterFile file) {
        if (!NetworkHelper.isNetworkAvailable(fragment.context)) {
            onError(CONNECTION_ERROR);
            return;
        }
        NotificationsManager notificationManager = NotificationsManager.getInstance();
        NotificationCompat.Builder notification = notificationManager.createDownloadNotification(fragment.context,
                fragment.getString(R.string.download), String.format("%s %s", fragment.getString(R.string.download_wait), file.getName())
        );
        fragment.showMessage(String.format("%s %s", fragment.getString(R.string.download), file.getName()));

        Getter.INSTANCE.download(file, new DownloadCallback() {
            @Override
            public void onProgressChanged(final int percent) {
                notification.setProgress(100, percent, false);
                notificationManager.updateDownloadNotification(fragment.context, notification);
            }

            @Override
            public void onEnd() {
                //TODO file location
                notificationManager.finishDownloadNotification(
                        fragment.context, notification, "", file.getName(), false);
            }

            @Override
            public void onError() {
                notificationManager.finishDownloadNotification(
                        fragment.context, notification, null, file.getName(), true);
            }
        });
    }

    private void onError(String error) {
        fragment.hideLoadingScreen();
        switch (error) {
            case CONNECTION_ERROR:
                fragment.showError(fragment.getString(R.string.connect_error));
                break;
            default:
                fragment.showError(UNKNOWN_ERROR);
                break;
        }
    }

    @Override
    protected void onDetachFragment() {
        FtpManager.destroyInstance();
        super.onDetachFragment();
    }
}
