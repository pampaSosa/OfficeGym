package com.pampa.officegym.Service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by adrian on 26/02/2018.
 */

public class MiFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG ="NOTICIAS";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        Log.d(TAG,"mensaje de: " + from);

        if (remoteMessage.getNotification() != null){
            remoteMessage.getNotification().getBody();
            Log.d(TAG,"cuerpo del mensaje: "+ remoteMessage.getNotification().getBody());
        }
    }
}
