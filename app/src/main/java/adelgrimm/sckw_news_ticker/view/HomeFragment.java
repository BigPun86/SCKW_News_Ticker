package adelgrimm.sckw_news_ticker.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adelgrimm.sckw_news_ticker.R;

/**
 * Created by Adel on 12.07.2015.
 */
public class HomeFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";

    public HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        return view;

    }
}
