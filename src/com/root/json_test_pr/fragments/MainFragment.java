package com.root.json_test_pr.fragments;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.root.json_test_pr.R;
import com.root.json_test_pr.jsonparsing.Constants;
import com.root.json_test_pr.jsonparsing.HttpJSONParser;

public class MainFragment extends Fragment implements SensorEventListener {
	private int mPos = -1;
	public SensorManager sm;
	public static String object;
	public static Handler myHandler;
	private ImageView mIView;
	private TextView mTitleTv;
	private TextView mAdressTv;
	private TextView mBodyTv;
	private LinearLayout progressContainer;
	private ItemInformationLoader thread;

	public MainFragment(int pos) {
		mPos = pos + 1;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sm = (SensorManager) getActivity().getSystemService(
				Context.SENSOR_SERVICE);

	}

	@Override
	public void onResume() {
		super.onResume();
		sm.registerListener(this,
				sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		startTread();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_fragment, null);
		createUI(view);
		progressContainer = (LinearLayout) view
				.findViewById(R.id.progressContainer);

		myHandler = new Handler();
		return view;
	}

	private View createUI(View view) {
		mIView = (ImageView) view.findViewById(R.id.imageView1);
		mTitleTv = (TextView) view.findViewById(R.id.textView_title);
		mAdressTv = (TextView) view.findViewById(R.id.textView_adress);
		mBodyTv = (TextView) view.findViewById(R.id.textView_body);

		return view;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			getAccelerometer(event);
		}
	}

	private void getAccelerometer(SensorEvent event) {
		float[] values = event.values;
		float x = values[0];
		float y = values[1];
		float z = values[2];

		float accelationSquareRoot = (x * x + y * y + z * z)
				/ (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
		if (accelationSquareRoot >= 2) {
			startTread();
		}
	}

	private void startTread() {
		progressContainer.setVisibility(View.VISIBLE);
		thread = new ItemInformationLoader();
		thread.start();
	}

	Runnable itemRunnable = new Runnable() {

		@Override
		public void run() {

			try {
				TimeUnit.SECONDS.sleep(1);
				JSONObject currenObj = new JSONObject(object)
						.getJSONObject(Constants.DATA);
				ImageLoader il = ImageLoader.getInstance();
				il.displayImage(currenObj.getString(Constants.PICTURE), mIView);
				mTitleTv.setText(currenObj.getString(Constants.TITLE));
				mBodyTv.setText(currenObj.getString(Constants.BODY));
				mAdressTv.setText(Constants.ADRESS);
				progressContainer.setVisibility(View.GONE);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	class ItemInformationLoader extends Thread {
		private HttpJSONParser parser = new HttpJSONParser();

		@Override
		public void run() {
			try {
				object = parser.getJSON(Constants.URL_ITEM + mPos);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			myHandler.post(itemRunnable);

		}
	}

}
