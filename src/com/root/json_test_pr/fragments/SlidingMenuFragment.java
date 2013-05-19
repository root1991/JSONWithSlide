package com.root.json_test_pr.fragments;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import com.root.json_test_pr.R;
import com.root.json_test_pr.activities.MainActivity;
import com.root.json_test_pr.adapters.AllItemsAdapter;
import com.root.json_test_pr.jsonparsing.Constants;
import com.root.json_test_pr.jsonparsing.HttpJSONParser;
import com.root.json_test_pr.jsonparsing.JSONStringsGetter;
import com.root.json_test_pr.jsonparsing.Model;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SlidingMenuFragment extends ListFragment {
	public static String object;
	public static Handler myHandler;
	private MyJSONLoader thread;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list, null);		
		return	view; 
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myHandler = new Handler();
		thread = new MyJSONLoader();
		thread.start();
		
	}

	
	Runnable r = new Runnable() {

		@Override
		public void run() {
			try {
				
				JSONStringsGetter getter = new JSONStringsGetter();
				ArrayList<Model> mData = getter.getData(object);
				AllItemsAdapter adapter = new AllItemsAdapter(getActivity(),
						mData);
				setListAdapter(adapter);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = new MainFragment(position);
		if (newContent != null)
			switchFragment(newContent);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity ma = (MainActivity) getActivity();
			ma.switchContent(fragment);
		}
	}

	class MyJSONLoader extends Thread {
		private HttpJSONParser parser = new HttpJSONParser();
		@Override
		public void run() {
			try {
				object = parser.getJSON(Constants.URL);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.d("obj", object.toString());
			myHandler.post(r);
		}

	}
}
