package eu.warble.pjapp.util;


import android.content.Context;

import eu.warble.pjapp.data.local.PreferencesManager;


public class CredentialsManager {

    private Credentials credentials;

    private CredentialsManager(){}

    private static class SingletonHelper{
        private static final CredentialsManager INSTANCE = new CredentialsManager();
    }

    public static CredentialsManager getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public void saveCredentials(String login, String password, Context context){
        this.credentials = new Credentials(login, password);
        PreferencesManager.saveCredentials(login, password, context);
    }

    public void deleteCredentials(Context context){
        this.credentials = null;
        PreferencesManager.saveCredentials(null, null, context);
    }

    public Credentials getCredentials(Context context){
        if (credentials == null){
            String login = PreferencesManager.getLogin(context);
            String password = PreferencesManager.getPassword(context);
            if (login == null || password == null)
                return null;
            credentials = new Credentials(login, password);
        }
        return credentials;
    }

    public static class Credentials{
        public String login;
        public String password;

        public Credentials(String login, String password){
            this.login = login;
            this.password = password;
        }
    }
}
