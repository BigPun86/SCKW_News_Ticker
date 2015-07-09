package adelgrimm.sckw_news_ticker.dataModel;

/**
 * Created by Adel on 27.06.2015.
 */

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static adelgrimm.sckw_news_ticker.dataModel.TransformHTMLText.dateParser;

public class HandleXML {
    public volatile boolean parsingComplete = true;
    private int countTitles, countDescriptions, countImage, countPubDate = 0;
    private ArrayList<String> listTitles = new ArrayList<String>();
    private ArrayList<String> listDesc = new ArrayList<String>();
    private ArrayList<String> listImages = new ArrayList<String>();
    private ArrayList<String> listPubDate = new ArrayList<String>();
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;

    public HandleXML(String url) {
        this.urlString = url;
    }

    public ArrayList<String> getListImages() {
        return listImages;
    }

    public void setListPubDate(ArrayList<String> list) {
        this.listPubDate = list;
    }

    public ArrayList<String> getListPubDate() {
        return listPubDate;
    }

    public void setListImages(ArrayList<String> list) {
        this.listImages = list;
    }

    public ArrayList<String> getListDesc() {
        return listDesc;
    }

    private void setListDesc(ArrayList<String> list) {
        this.listDesc = list;
    }

    public ArrayList<String> getListTitles() {
        return listTitles;
    }

    private void setListTitle(ArrayList<String> list) {
        this.listTitles = list;
    }

    private void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text = null;
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals("title")) {
                            listTitles.add(text);
                            setListTitle(listTitles);
                            countTitles++;
                        } else if (name.equals("pubDate")) {
                            listPubDate.add(dateParser(text));
                            setListPubDate(listPubDate);
                            countPubDate++;
                        } else if (name.equals("description")) {
//                            listDesc.add(TransformHTMLText.br2nl(text));
                            listDesc.add(TransformHTMLText.html2text(text));
                            setListDesc(listDesc);
                            countDescriptions++;
                        }
                        break;
                }
                event = myParser.next();
            }
            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchXML() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);

                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);

                    parseXMLAndStoreIt(myparser);
                    Log.d("Parse", stream.toString());
                    stream.close();
                } catch (Exception e) {
                    Log.e("fetchXML", e.getMessage());
                }
            }
        });
        thread.start();
    }
}