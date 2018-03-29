package com.pampa.officegym.Service;

import android.nfc.Tag;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by adrian on 27/02/2018.
 */

public class MiFirebaseInstanceIdService extends FirebaseInstanceIdService {
    public static final String TAG = "NOTICIAS";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"Token:"+token);
    }
}
