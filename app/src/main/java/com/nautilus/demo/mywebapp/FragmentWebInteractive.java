package com.nautilus.demo.mywebapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class FragmentWebInteractive extends Fragment {

    public Context my_context;
    public View rootView;
    public ProgressDialog pd;
    public MediaPlayer mp;
    public NotificationManager mNotificationManager;
    public WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        my_context = container.getContext();
        rootView = inflater.inflate(R.layout.fragment_web, container, false);


        String type = getArguments().getString("type");
        String url = getArguments().getString("url");


        webView = (WebView) rootView.findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new WebAppInterface(my_context), "WebAppInterface");
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);


        pd = new ProgressDialog(my_context);
        pd.setMessage("Please wait Loading...");
        pd.show();

        if (type.equals("file")) {

            webView.loadUrl("file:///android_asset/" + url);

        }

        webView.setWebViewClient(new MyWebViewClient());

        return rootView;

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            if (!pd.isShowing()) {
                pd.show();
            }

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("on finish");
            if (pd.isShowing()) {
                pd.dismiss();
            }

        }
    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        // -------------------------------- SHOW TOAST ---------------------------------------
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }

        // -------------------------------- START VIBRATE MP3 ---------------------------------------
        @JavascriptInterface
        public void vibrate(int milliseconds) {
            Vibrator v = (Vibrator) my_context.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(milliseconds);
        }

        // -------------------------------- START PLAY MP3 ---------------------------------------
        @JavascriptInterface
        public void playSound() {
            mp = MediaPlayer.create(my_context, R.raw.demo);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    mp.release();
                }

            });
            mp.start();
        }

        // -------------------------------- STOP PLAY MP3 ---------------------------------------
        @JavascriptInterface
        public void stopSound() {
            if (mp.isPlaying()) {
                mp.stop();
            }
        }

        // -------------------------------- CREATE NOTIFICATION ---------------------------------------
        @JavascriptInterface
        public void newNotification(String title, String message) {
            mNotificationManager = (NotificationManager) my_context.getSystemService(Context.NOTIFICATION_SERVICE);

            PendingIntent contentIntent = PendingIntent.getActivity(my_context, 0, new Intent(my_context, MainActivity.class), 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(my_context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(message))
                    .setContentText(message);

            mBuilder.setContentIntent(contentIntent);
            mNotificationManager.notify(1, mBuilder.build());
        }

        // -------------------------------- GET DATA ACCOUNT FROM DEVICE ---------------------------------------
        @JavascriptInterface
        public void snakBar(String message) {
            Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }




}



