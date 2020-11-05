package com.karic.sdk.sdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView tvAAID;
    private View btnGetAAID;
    private View progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setupListener();
    }

    private void initViews() {
        tvAAID = findViewById(R.id.tv_aaid);
        btnGetAAID = findViewById(R.id.btn_get_aaid);
        progress = findViewById(R.id.progressBar);

        tvAAID.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        btnGetAAID.setVisibility(View.VISIBLE);
    }

    private void setupListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            int type;

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_banner:
                        type = AppConstants.AD_BANNER;
                        break;

                    case R.id.tv_splash:
                        type = AppConstants.AD_SPLASH;
                        break;

                    case R.id.tv_native:
                        type = AppConstants.AD_NATIVE;
                        break;
                }

                gotoShowAd(type);
            }
        };

        findViewById(R.id.tv_banner).setOnClickListener(listener);
        findViewById(R.id.tv_native).setOnClickListener(listener);
        findViewById(R.id.tv_splash).setOnClickListener(listener);


        findViewById(R.id.btn_get_aaid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                btnGetAAID.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);

                Executors.newSingleThreadExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String aaid = AdvertisingIdClient.getGoogleAdId(v.getContext());
                            tvAAID.post(new Runnable() {
                                @Override
                                public void run() {
                                    progress.setVisibility(View.GONE);
                                    tvAAID.setVisibility(View.VISIBLE);
                                    tvAAID.setText(aaid);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void gotoShowAd(@AppConstants.AdType int type) {
        Intent intent = new Intent(this, AdDisplayActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
