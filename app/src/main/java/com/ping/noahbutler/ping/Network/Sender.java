package com.ping.noahbutler.ping.Network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Noah Butler on 9/1/2015.
 */
public class Sender extends AsyncTask<String, Double, Boolean> {

    private static String URL_NEW_USER = "http://104.236.237.151/users/new/";
    private static String URL_SEND_COORDS = "https://104.236.237.151/users/send/coords/";
    public static String URL_GET_LIST = "http://104.236.237.151/users/friends/";

    @Override
    protected Boolean doInBackground(String... strings) {
        //declare new url
        URL url;
        //delcare URL Connection
        HttpURLConnection urlConnection;

        try {
            //first string of params always a command
            switch (strings[0]) {
                case "sendGPS":
                    //send GPS coords of this user to another user
                    url = new URL(URL_SEND_COORDS);
                    //setup connection
                    urlConnection = (HttpURLConnection)url.openConnection();
                    return sendGPS(urlConnection, strings);
                case "newUser":
                    //create a new user on the server
                    url = new URL(URL_NEW_USER);
                    //setup connection
                    urlConnection = (HttpURLConnection)url.openConnection();
                    return newUser(urlConnection, strings);
                default:
                    //nothing was ran
                    return false;
            }
        } catch (IOException e){
            Log.e("POST ERROR", "Error setting up HTTP POST");
        }

        return null;
    }

    /**
     * takes the url connection object and sends the param strings to it in a HTTP POST
     * creating a new LocationPartner on the database
     * @param urlConnection
     * @param strings
     * @return
     */
    private Boolean sendGPS(URLConnection urlConnection, String...strings) {
        try {
            String usernameRequester = strings[1];
            String usernameRequested = strings[2];
            String latitude = strings[3];
            String longitude = strings[4];

            urlConnection.setDoOutput(true);
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("method=\"post\"");
            stringBuilder.append("username_requester="); stringBuilder.append(usernameRequester);
            stringBuilder.append("username_requested="); stringBuilder.append(usernameRequested);
            stringBuilder.append("latitude="); stringBuilder.append(latitude);
            stringBuilder.append("longitude="); stringBuilder.append(longitude);

            7

            return true;
        }catch (IOException e) {
            Log.e("SEND GPS ERROR", "could not send gps coords, error in sendGPS in Sender Object");
            return false;
        }

    }

    /**
     * takes the url connection object and sends the param strings to it in a HTTP POST
     * creating a new user on the database
     * @param urlConnection
     * @param strings
     * @return
     */
    private Boolean newUser(URLConnection urlConnection, String...strings) {
        Log.e("NEW_USER", "Running new user network stream");
        OutputStream out;
        try {
            String username = strings[1];
            String password = strings[2];

            urlConnection.setDoOutput(true);
             out = new DataOutputStream(urlConnection.getOutputStream());

            String lineEnd = "\r\n";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Content-Disposition: form-data; ");
            stringBuilder.append("method=\"POST\"");
            stringBuilder.append("username="); stringBuilder.append(username);
            stringBuilder.append("password="); stringBuilder.append(password);
            stringBuilder.append(lineEnd);
            byte[] data = stringBuilder.toString().getBytes();
            out.write(data);
            out.flush();
            out.close();
            return true;
        }catch (IOException e) {
            Log.e("SEND GPS ERROR", "could not send gps coords, error in sendGPS in Sender Object");
            return false;
        }
    }
}
