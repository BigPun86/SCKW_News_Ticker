package adelgrimm.sckw_news_ticker.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adelgrimm.sckw_news_ticker.R;
import adelgrimm.sckw_news_ticker.mainActivity.MainActivity;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class AktivenNewsFragment extends Fragment implements AbsListView.OnItemClickListener {


    // TODO: For TheNews!!!!
//    private OnFragmentInteractionListener mListener;

    String[] titles, text, pubDate;
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
//    private AbsListView mListView;
    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private MyListAdapter mAdapter;
    private List listItemList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AktivenNewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listItemList = new ArrayList();
        if (((MainActivity) getActivity()).getTitlesForAktive() != null && ((MainActivity) getActivity()).getTextForAktive() != null && ((MainActivity) getActivity()).getPubDateForAktive() != null) {
            titles = ((MainActivity) getActivity()).getTitlesForAktive();
            text = ((MainActivity) getActivity()).getTextForAktive();
            pubDate = ((MainActivity) getActivity()).getPubDateForAktive();
            setupListView();
        } else {
            listItemList.add(new MyListItem("Loading...", "Swipe Down for Update", "09-Jul-2015"));
        }
    }

    private void setupListView() {
        for (int i = 0; i < titles.length; i++) {
            listItemList.add(new MyListItem(titles[i], (text[i]).substring(0, 75) + "...", pubDate[i]));
        }
        mAdapter = new MyListAdapter(getActivity(), listItemList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        mListView = (ListView) view.findViewById(android.R.id.list);
        ((ListView) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyListItem item = (MyListItem) this.listItemList.get(position);
        Toast.makeText(getActivity(), item.getItemTitle() + " Clicked!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), TheNews.class);
        intent.putExtra("TIT", titles[position]);
        intent.putExtra("TXT", text[position]);
        startActivity(intent);
    }


    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */

    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String id);
    }


}