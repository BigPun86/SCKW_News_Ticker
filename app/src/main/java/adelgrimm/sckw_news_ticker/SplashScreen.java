package adelgrimm.sckw_news_ticker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import adelgrimm.sckw_news_ticker.intentService.DownloadResultReceiver;
import adelgrimm.sckw_news_ticker.intentService.DownloadService;
import adelgrimm.sckw_news_ticker.intentService.DownloadServiceInterface;
import adelgrimm.sckw_news_ticker.mainActivity.MainActivity;


/**
 * Created by Adel on 05.07.2015.
 */
public class SplashScreen extends Activity implements DownloadResultReceiver.Receiver, DownloadServiceInterface {

    public static final String TITLES_AKTIVE = "TITLES_A";
    public static final String DESCRIPTIONS_AKTIVE = "DESC_A";
    public static final String TITLES_VEREIN = "TITLES_V";
    public static final String DESCRIPTIONS_VEREIN = "DESC_V";
    public static final String TITLES_JUNIOREN = "TITLES_J";
    public static final String DESCRIPTIONS_JUNIOREN = "DESC_J";
    public static final String PUB_DATE_AKTIVE = "PUB_DATE_A";
    public static final String PUB_DATE_VEREIN = "PUB_DATE_V";
    public static final String PUB_DATE_JUNIOREN = "PUB_DATE_J";
    private boolean loading = false;

    // Splash screen timer
    private static final int SPLASH_TIME_OUT = 100;

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private final Handler handler = new Handler();
    private String size;
    private String[] titlesArray, descArray, pubDateArray;
    private String[] titlesForAktive, textForAktive, pubDateForAktive, titlesForVerein, textForVerein, pubDateForVerein, titlesForJunioren, textForJunioren, pubDateForJunioren;
    private int newsKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        //checking if WiFi is activated on your System
        if (isWifiOn()) {
            downloadDataService();
        }
        //if not than turn on and after taht download service
        else {
            turnOnWifi();
            downloadDataService();
        }


        // Start long running operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    loading = true;
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
//                            textView.setText(progressStatus + "/" + progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        //Just to display the progress slowly
                        Thread.sleep(SPLASH_TIME_OUT);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                loading = false;

            }
        }).start();


    }

    @Override
    public boolean isWifiOn() {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();

    }

    @Override
    public void turnOnWifi() {

        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);
    }

    @Override
    public void downloadDataService() {
    /* Starting Download Service */
        mReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, SplashScreen.this, DownloadService.class);

        /* Send optional extras to Download IntentService */
        intent.putExtra("urlAktive", BLOG_AKTIVE_NEWS_URL);
        intent.putExtra("urlVerein", BLOG_VEREIN_NEWS_URL);
        intent.putExtra("urlJunioren", BLOG_JUNIOREN_NEWS_URL);
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("requestId", 101);

        startService(intent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

        switch (resultCode) {
            case DownloadService.STATUS_RUNNING:
                setProgressBarIndeterminateVisibility(true);
                Log.d("onReceiveResult", "Status Running");
                break;

            case DownloadService.STATUS_FINISHED:

                Log.d("onReceiveResult", "Status Finished at the beginning");
                /* Hide progress & extract result from bundle */
                setProgressBarIndeterminateVisibility(false);

                // get Bundle from Service
                titlesArray = resultData.getStringArrayList("resultTitle").toArray(new String[resultData.getStringArrayList("resultTitle").size()]);
                descArray = resultData.getStringArrayList("resultDescription").toArray(new String[resultData.getStringArrayList("resultDescription").size()]);
                pubDateArray = resultData.getStringArrayList("resultPublishDate").toArray(new String[resultData.getStringArrayList("resultPublishDate").size()]);
                newsKey = resultData.getInt("newsKey");


                switch (newsKey) {

                    case 1:
                        // News for AktiveNewsFragment
                        titlesForAktive = titlesArray;
                        textForAktive = descArray;
                        pubDateForAktive = pubDateArray;
                        break;
                    case 2:
                        // News for VereinNewsFragment
                        titlesForVerein = titlesArray;
                        textForVerein = descArray;
                        pubDateForVerein = pubDateArray;
                        break;
                    case 3:
                        // News for JuniorenNewsFragment
                        titlesForJunioren = titlesArray;
                        textForJunioren = descArray;
                        pubDateForJunioren = pubDateArray;
                        break;
                }

                // Newskey is only 3 when all news have bin fetched from Service, Then put in Bundle
                if (newsKey == 3) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);

                    i.putExtra(TITLES_AKTIVE, titlesForAktive);
                    i.putExtra(DESCRIPTIONS_AKTIVE, textForAktive);
                    i.putExtra(PUB_DATE_AKTIVE, pubDateForAktive);

                    i.putExtra(TITLES_VEREIN, titlesForVerein);
                    i.putExtra(DESCRIPTIONS_VEREIN, textForVerein);
                    i.putExtra(PUB_DATE_VEREIN, pubDateForVerein);

                    i.putExtra(TITLES_JUNIOREN, titlesForJunioren);
                    i.putExtra(DESCRIPTIONS_JUNIOREN, textForJunioren);
                    i.putExtra(PUB_DATE_JUNIOREN, pubDateForJunioren);

                    startActivity(i);
                }

                Log.d("onReceiveResult", "Status Finished");
                break;

            case DownloadService.STATUS_ERROR:
                /* Handle the error */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}