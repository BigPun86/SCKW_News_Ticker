package adelgrimm.sckw_news_ticker.dataModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Adel on 05.07.2015.
 */
public class TransformHTMLText {

    public static String br2nl(String html) {
        Document document = Jsoup.parse(html);
        document.select("br").append("\\n");
        document.select("p").prepend("\\n");
        return document.text().replace("\\n", "\n");
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

    public static String dateParser(String unformattedDate) throws ParseException {
        SimpleDateFormat parserSDF = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
        Date date = parserSDF.parse(unformattedDate);
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MMM-yyyy");
        return formatDate.format(date);
    }

}
