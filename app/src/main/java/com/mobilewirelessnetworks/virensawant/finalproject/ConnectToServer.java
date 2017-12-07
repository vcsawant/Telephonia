package com.mobilewirelessnetworks.virensawant.finalproject;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ConnectToServer extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SEND = "com.mobilewirelessnetworks.virensawant.finalproject.action.SEND";
    private static final String ACTION_LOGIN = "com.mobilewirelessnetworks.virensawant.finalproject.action.LOGIN";
    private static final String ACTION_RECEIVE = "com.mobilewirelessnetworks.virensawant.finalproject.action.RECEIVE";
    private static final String ACTION_CONNECT = "com.mobilewirelessnetworks.virensawant.finalproject.action.CONNECT";
    private static final String ACTION_REGISTER = "com.mobilewirelessnetworks.virensawant.finalproject.action.REGISTER";

    // TODO: Rename parameters
    private static final String SEND_STRING = "com.mobilewirelessnetworks.virensawant.finalproject.extra.PARAM1";


    private final static String TAG = "SERVICE";


    private final String GROUPCAST_SERVER = "ec2-13-58-144-237.us-east-2.compute.amazonaws.com";
    private final int GROUPCAST_PORT = 20001;
    // networking
    public static Socket mSocket = null;
    public static BufferedReader mIn = null;
    public static PrintWriter mOut = null;
    public static boolean mConnected = false;
    public static String user = null;

    private static String mostRecentResponse = "default_response";

    public ConnectToServer() {


        super("ConnectToServer");
    }

    public static void registerAction(Context context, String username, String pass){

        Intent intent = new Intent(context, ConnectToServer.class);
        intent.setAction(ACTION_REGISTER);
        intent.putExtra("USERNAME",username);
        intent.putExtra("PASSWORD",pass);
        context.startService(intent);
    }

    public static void connectAction(Context context){
        Intent intent = new Intent(context, ConnectToServer.class);
        intent.setAction(ACTION_CONNECT);
        context.startService(intent);
    }

    public static void loginAction(Context context, String username, String pass){
        Intent intent = new Intent(context, ConnectToServer.class);
        intent.setAction(ACTION_LOGIN);
        intent.putExtra("USERNAME",username);
        intent.putExtra("PASSWORD",pass);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void sendAction(Context context, String sendString) {
        Intent intent = new Intent(context, ConnectToServer.class);
        intent.setAction(ACTION_SEND);
        intent.putExtra(SEND_STRING, sendString);
        context.startService(intent);

        Log.e(TAG,mostRecentResponse);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static String receiveAction(Context context) {
        Intent intent = new Intent(context, ConnectToServer.class);
        intent.setAction(ACTION_RECEIVE);
        context.startService(intent);

        return mostRecentResponse;
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SEND.equals(action)) {
                final String param1 = intent.getStringExtra(SEND_STRING);
                handleActionSend(param1);
            } else if (ACTION_RECEIVE.equals(action)) {
                String s = handleActionReceive();
            } else if (ACTION_LOGIN.equals(action)) {
                String user = intent.getStringExtra("USERNAME");
                String pass = intent.getStringExtra("PASSWORD");
                handleActionLogin(user, pass);
            } else if (ACTION_REGISTER.equals(action)) {
                String user = intent.getStringExtra("USERNAME");
                String pass = intent.getStringExtra("PASSWORD");
                handleActionRegister(user, pass);
            }
            if (ACTION_CONNECT.equals(action)){
                handleActionConnect();
            }
        }
    }


    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSend(String param1) {
        // TODO: Handle action Foo
        send(param1);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private String handleActionReceive() {
        return "hi";
    }

    private void handleActionConnect(){
        connect();
    }

    private void handleActionLogin(String name, String pass){
        send("LOGIN," + name+ "," +pass);
    }

    private void handleActionRegister(String name, String pass){
        send("REGISTER," + name+","+pass);
    }

    @SuppressLint("StaticFieldLeak")
    private boolean send(String msg) {
        if (!mConnected) {
            Log.i(TAG, "can't send: not mConnected");
            return false;
        }

        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... msg) {
                Log.i(TAG, "sending: " + msg[0]);
                mOut.println(msg[0]);
                mOut.flush();
                return mOut.checkError();
            }

            @Override
            protected void onPostExecute(Boolean error) {
                if (!error) {
                    Log.i(TAG,"Message sent to server");
                }
                else {
                    Log.i(TAG,"Failed to send message to server");
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, msg);

        try {
            Log.e(TAG,"HAVE COME HERE : " + mostRecentResponse);
            mostRecentResponse = mIn.readLine();
            Log.e(TAG,"NEW RESPONSE: "+ mostRecentResponse );
        } catch (Exception e) {
            Log.e(TAG,e.getLocalizedMessage());
        }
        return true;
    }


    @SuppressLint("StaticFieldLeak")
    private void connect() {
        new AsyncTask<Void, Void, String>() {
            String errorMsg = null;

            @Override
            protected String doInBackground(Void... args) {
                Log.i(TAG, "Connect task started");
                try {
                    mConnected = false;
                    mSocket = new Socket(GROUPCAST_SERVER, GROUPCAST_PORT);
                    Log.i(TAG, "Socket created");
                    mIn = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                    mOut = new PrintWriter(mSocket.getOutputStream());

                    mConnected = true;

                    Log.i(TAG, "Input and output streams ready");


                } catch (UnknownHostException e1) {
                    errorMsg = e1.getMessage();
                } catch (IOException e1) {
                    errorMsg = e1.getMessage();
                    try {
                        if (mOut != null) {
                            mOut.close();
                        }
                        if (mSocket != null) {
                            mSocket.close();
                        }
                    } catch (IOException ignored) {
                    }
                }
                Log.i(TAG, "Connect task finished");
                return errorMsg;
            }

            @Override
            protected void onPostExecute(String errorMsg) {
                if (errorMsg == null) {

                    Log.i(TAG,"CONNECTED:" + mConnected);
                }
                else {
                    // can't connect: close the activity
                    Log.e(TAG,"CAN'T CONNECT:" +errorMsg);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
