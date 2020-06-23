package com.example.marco.domotica;

import android.graphics.Color;
import android.hardware.camera2.CameraCharacteristics;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

public class Presencia_info extends AppCompatActivity {
    LinearLayout plo;
    WebView stream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presencia_info);
        plo=findViewById(R.id.layout_presencia);
        stream=findViewById(R.id.camara_stream);
        stream.setInitialScale(1);
        stream.getSettings().setUseWideViewPort(true);
        stream.getSettings().setLoadWithOverviewMode(true);
        stream.getSettings().setBuiltInZoomControls(true);
        stream.getSettings().setSupportZoom(true);
        stream.loadUrl(getString(R.string.ip)+"/video_feed");

    }

    @Override
    public void onResume()
    {
        super.onResume();
        stream.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        stream.onPause();
    }

    @Override
    public void onDestroy(){

        stream.destroy();
        stream=null;
        super.onDestroy();
    }
}
