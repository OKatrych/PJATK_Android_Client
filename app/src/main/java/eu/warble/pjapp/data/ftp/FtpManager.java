package eu.warble.pjapp.data.ftp;

import android.os.Environment;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

import eu.warble.pjapp.util.AppExecutors;
import eu.warble.pjapp.util.Constants;
import eu.warble.pjapp.util.CredentialsManager.Credentials;
import eu.warble.pjapp.util.FileManager;

public class FtpManager {
    private static final String BASE_URL = "sftp.pjwstk.edu.pl";
    private static FtpManager INSTANCE = null;
    private final AppExecutors executors;
    private Session session;
    private ChannelSftp channelSftp = null;
    private Credentials credentials;
    public AtomicBoolean downloading = new AtomicBoolean(false);
    public AtomicBoolean shouldContinueDownloading = new AtomicBoolean(true);

    private FtpManager(AppExecutors executors, Credentials credentials){
        this.executors = executors;
        this.credentials = credentials;
        try {
            createNewSession();
        }catch (JSchException ex){
            Log.e("FtpManager constructor", ex.toString());
        }
    }

    public static FtpManager getInstance(AppExecutors executors, Credentials credentials){
        if (INSTANCE == null)
            INSTANCE = new FtpManager(executors, credentials);
        return INSTANCE;
    }

    public static void destroyInstance(){
        if (INSTANCE != null)
            INSTANCE.disconnect();
        INSTANCE = null;
    }

    public void loadFilesList(String path, LoadFilesListCallback callback){
        executors.networkIO().execute(() -> {
            boolean connected = (session.isConnected() && channelSftp != null && channelSftp.isConnected()) || connect();
            if (connected){
                try {
                    channelSftp.cd(path);
                    final Vector<ChannelSftp.LsEntry> list = channelSftp.ls(channelSftp.pwd());
                    executors.mainThread().execute(() -> callback.onFilesListLoaded(list));
                } catch (SftpException e) {
                    e.printStackTrace();
                    executors.mainThread().execute(() -> callback.onError(Constants.UNKNOWN_ERROR));
                    Log.e("FtpMgr.loadFilesList", e.toString());
                    Crashlytics.logException(e);
                }
            }else{
                executors.mainThread().execute(() -> callback.onError(Constants.CONNECTION_ERROR));
            }
        });
    }

    public String currentDirectory(){
        try {
            if (session.isConnected() && channelSftp != null && channelSftp.isConnected())
                return channelSftp.pwd();
        } catch (SftpException e) {
            Log.e("FtpMgr.currentDirectory", e.toString());
            Crashlytics.logException(e);
        }
        return null;
    }

    public void downloadFile(String fileName, DownloadFileMonitor monitor){
        boolean connected = (session.isConnected() && channelSftp != null && channelSftp.isConnected()) || connect();
        if (!downloading.get() && connected) {
            executors.networkIO().execute(() -> {
                try {
                    File file = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS), fileName);
                    if (!file.exists())
                        if (!file.createNewFile())
                            throw new IOException("File is not created");
                    //starting downloading
                    channelSftp.get(fileName, file.getAbsolutePath(), new SftpProgressMonitor() {
                        @Override
                        public void init(int op, String src, String dest, long max) {
                            downloading.set(true);
                            shouldContinueDownloading.set(true);
                            executors.mainThread().execute(() -> monitor.onStart(op, src, dest, max));
                        }

                        @Override
                        public boolean count(long count) {
                            executors.mainThread().execute(() -> monitor.onChange(count));
                            return shouldContinueDownloading.get();
                        }

                        @Override
                        public void end() {
                            downloading.set(false);
                            executors.mainThread().execute(monitor::onFinish);
                        }
                    });
                } catch (Exception ex) {
                    FileManager.deleteFile(new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS), fileName).getAbsolutePath());
                    executors.mainThread().execute(monitor::onFinish);
                    Crashlytics.logException(ex);
                    Log.e("FtpMgr.downloadFile", ex.toString());
                    disconnect();
                }
            });
        }
    }

    public void cancelDownloading(){
        if (downloading.get())
            shouldContinueDownloading.set(false);
    }

    /*
     *  Do not run on main thread
     */
    private boolean connect(){
        try {
            if (!session.isConnected()){
                session.connect();
            }
            if (channelSftp == null)
                channelSftp = (ChannelSftp) session.openChannel("sftp");
            if (!channelSftp.isConnected())
                channelSftp.connect();
        } catch (JSchException e) {
            Log.e("FtpManager.connect()", e.toString());
            Crashlytics.logException(e);
            FtpManager.destroyInstance();
            return false;
        }
        return channelSftp.isConnected();
    }

    private void createNewSession() throws JSchException {
        session = new JSch().getSession(credentials.login, BASE_URL, 22);
        session.setPassword(credentials.password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setTimeout(7000);
    }

    private void disconnect(){
        if (session.isConnected())
            session.disconnect();
    }

    public interface LoadFilesListCallback {
        void onFilesListLoaded(Vector<ChannelSftp.LsEntry> list);
        void onError(String error);
    }

    public interface DownloadFileMonitor{

        /**
         * Will be called when a new operation starts.
         * @param op - a code indicating the direction of transfer, one of PUT and GET
         * @param dest - the destination file name.
         * @param max - the final count (i.e. length of file to transfer).
         */
        void onStart(int op, String src, String dest, long max);

        /**
         * Will be called periodically as more data is transfered.
         * To cancel downloading please use method FtpManger.cancelDownloading()
         * @param count - the number of bytes transferred so far
         * @return true if the transfer should go on, false if the transfer should be cancelled. (not working)
         */
        boolean onChange(long count);

        /**
         *  Will be called when the transfer ended, either because all the data was transferred,
         *  or because the transfer was cancelled.
         */
        void onFinish();
    }
}