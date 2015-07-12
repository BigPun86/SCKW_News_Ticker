package adelgrimm.sckw_news_ticker;

import junit.framework.TestCase;

import adelgrimm.sckw_news_ticker.dataModel.HandleXML;

/**
 * Created by Adel on 12.07.2015.
 */
public class NewsFromBlogTest extends TestCase {


    private HandleXML obj;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // test
        obj = new HandleXML("http://www.sckw.de/rss/blog/Aktive");
        obj.fetchXML();

        while (obj.parsingComplete) {
            obj.getListTitles();
            obj.getListDesc();
            obj.getListImages();
            obj.getListPubDate();
        }
    }

    public void testTitlesContentNotNull() {
        //test if lists are not empty -> actually testing that we received something
        assertNotNull(obj.getListTitles());
    }

    public void testDescriptionsContentNotNull() {
        //test if lists are not empty -> actually testing that we received something
        assertNotNull(obj.getListDesc());
    }

    public void testImagesContentNotNull() {
        //test if lists are not empty -> actually testing that we received something
        assertNotNull(obj.getListImages());
    }

    public void testPubDateContentNotNull() {
        //test if lists are not empty -> actually testing that we received something
        assertNotNull(obj.getListPubDate());
    }
}
