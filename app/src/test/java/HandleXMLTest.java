/**
 * Created by Adel on 11.07.2015.
 */

import org.junit.Test;

import adelgrimm.sckw_news_ticker.dataModel.HandleXML;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Adel on 11.07.2015.
 */
public class HandleXMLTest {

    @Test
    public void testHandleXMLContent()  {
        // test
        HandleXML obj = new HandleXML("http://www.sckw.de/rss/blog/Aktive");
        obj.fetchXML();

        while (obj.parsingComplete) {
            obj.getListTitles();
            obj.getListDesc();
            obj.getListImages();
            obj.getListPubDate();
        }

        //test if lists are not empty -> actually testing that we received something
        assertNotNull(obj.getListTitles());
        assertNotNull(obj.getListDesc());
        assertNotNull(obj.getListImages());
        assertNotNull(obj.getListPubDate());
    }

}
