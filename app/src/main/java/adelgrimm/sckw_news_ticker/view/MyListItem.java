package adelgrimm.sckw_news_ticker.view;

/**
 * Created by Adel on 05.07.2015.
 */
public class MyListItem {

    private String itemTitle, itemSubTitle, itemPubDate;

    public String getItemSubTitle() {
        return itemSubTitle;
    }

    public void setItemSubTitle(String itemSubTitle) {
        this.itemSubTitle = itemSubTitle;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemPubDate() {
        return itemPubDate;
    }

    public void setItemPubDate(String itemPubDate) {
        this.itemPubDate = itemPubDate;
    }

    public MyListItem(String title, String subTitle, String pubDate) {
        this.itemTitle = title;
        this.itemSubTitle = subTitle;
        this.itemPubDate = pubDate;
    }
}