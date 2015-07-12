package adelgrimm.sckw_news_ticker.intentService;

import android.os.Handler;

/**
 * Created by Adel on 12.07.2015.
 * Use this Class to Download the News Content and do the preparation with WiFi.
 * Use it for any Refresh options in other Activities/Fragments....for example SwipeDown in ListView
 */
public interface DownloadServiceInterface {


    String BLOG_AKTIVE_NEWS_URL = "http://www.sckw.de/rss/blog/Aktive";
    String BLOG_VEREIN_NEWS_URL = "http://www.sckw.de/rss/blog/Verein";
    String BLOG_JUNIOREN_NEWS_URL = "http://www.sckw.de/rss/blog/Junioren";
    DownloadResultReceiver mReceiver = new DownloadResultReceiver(new Handler());


    boolean isWifiOn();

    void turnOnWifi();

    void downloadDataService();

}

//Usage in "ChildCass"
//checking if WiFi is activated on your System
/**
 * if (isWifiOn()) {
 * downloadDataService();
 * }
 * //if not than turn on and after taht download service
 * else {
 * turnOnWifi();
 * downloadDataService();
 * }
 *
 * @Override //public boolean isWifiOn() {
 * WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
 * return wifi.isWifiEnabled();
 * <p/>
 * }
 * @Override public void turnOnWifi() {
 * <p/>
 * WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
 * wifi.setWifiEnabled(true);
 * }
 * @Override public void downloadDataService() {
 * ( Starting Download Service )
 * mReceiver.setReceiver(this);
 * Intent intent = new Intent(Intent.ACTION_SYNC, null, SplashScreen.this, DownloadService.class);
 * <p/>
 * (Send optional extras to Download IntentService )
 * intent.putExtra("urlAktive", BLOG_AKTIVE_NEWS_URL);
 * intent.putExtra("urlVerein", BLOG_VEREIN_NEWS_URL);
 * intent.putExtra("urlJunioren", BLOG_JUNIOREN_NEWS_URL);
 * intent.putExtra("receiver", mReceiver);
 * intent.putExtra("requestId", 101);
 * <p/>
 * startService(intent);
 * }
 */