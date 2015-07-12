package adelgrimm.sckw_news_ticker.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import adelgrimm.sckw_news_ticker.R;
import adelgrimm.sckw_news_ticker.mainActivity.MainActivity;


/**
 * Created by adel on 08.07.15.
 */
public class TheNews extends MainActivity {

    private TextView titleView, textView;
    private String title, text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.the_news_2);

        Intent i = getIntent();
        title = i.getStringExtra("TIT");
        text = i.getStringExtra("TXT");

        titleView = (TextView) findViewById(R.id.titleView);
        textView = (TextView) findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());

        if (title != null && text != null) {
//            titleView.setText(title);
            textView.setText(text);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(title);
            // Find our drawer view
            mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerToggle = setupDrawerToggle();

            // Tie DrawerLayout events to the ActionBarToggle
            mDrawer.setDrawerListener(drawerToggle);

        }


    }


}


