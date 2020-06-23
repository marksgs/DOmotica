package com.example.marco.domotica;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificacionesID extends FirebaseInstanceIdService {

    public NotificacionesID(){

    }

    @Override
    public void onTokenRefresh(){
        String refreshedToken= FirebaseInstanceId.getInstance().getToken();
        SharedPreferences.Editor editor= (SharedPreferences.Editor) getSharedPreferences("TokenID",MODE_PRIVATE).edit();
        editor.putString("Token",refreshedToken);
        editor.commit();
        Log.i("TokenObtenido:", refreshedToken);

    }

}
