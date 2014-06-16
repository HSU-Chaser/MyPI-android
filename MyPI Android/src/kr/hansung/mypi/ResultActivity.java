package kr.hansung.mypi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import kr.list.DataListView;
import kr.list.IconTextItem;
import kr.list.IconTextListAdapter;
import kr.object.SearchResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ResultActivity extends Activity {
	private BackPressCloseHandler backHandler;
	private ResultActivity resultActivity = this;
	private ExpandableListView mListView;

	RelativeLayout layout;
	ProgressBar mProgress;
	ProgressDialog mDialog;
	ArrayList<SearchResult> result;
	ViewGroup.LayoutParams params;
	JSONArray mArray;
	TextView tv;
	

	DataListView list;
	IconTextListAdapter adapter;
	Drawable[] riskImgArray;
	Drawable riskImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_result);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
//		setContentView(R.layout.activity_result);
		backHandler = new BackPressCloseHandler(this);
		params = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		// Intent intent = getIntent();
		// tv = (TextView) findViewById(R.id.plain);
		// mProgress = (ProgressBar) findViewById(R.id.progress_bar);

		// ListView resultList = (ListView) findViewById(R.id.result_list);

		riskImgArray = new Drawable[3];

		riskImgArray[0] = getResources().getDrawable(R.drawable.risk_low);
		riskImgArray[1] = getResources().getDrawable(R.drawable.risk_mid);
		riskImgArray[2] = getResources().getDrawable(R.drawable.risk_high);

		list = new DataListView(this);
		adapter = new IconTextListAdapter(this);
	
//		adapter.addItem(new IconTextItem("1", "제목이 나와야 할 부분", riskImg[0]));
//		adapter.addItem(new IconTextItem("2", "제목이 나와야 할 부분", riskImg[1]));
	
		new ResultTask().execute();
	}

	private void setLayout(){
		mListView = (ExpandableListView) findViewById(R.id.expandList);
	}

	
	class ResultTask extends AsyncTask<Void/* 로그인 정보 필요 */, Void, JSONArray> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog = new ProgressDialog(ResultActivity.this);
			mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mDialog.setTitle("MyPI");
			mDialog.setMessage("결과를 분석중입니다...");
			mDialog.show();
		}

		@Override
		protected JSONArray doInBackground(Void... params) {
			HttpURLConnection conn = null;
			StringBuffer buffer = null;
			JSONArray array = null;

			try {
				// Build URL
				URL url = new URL("http://mypi.co.kr/json.txt");
				// Connect
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				// Read
				InputStreamReader isr = new InputStreamReader(
						conn.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				// Save
				String read;
				buffer = new StringBuffer();
				while ((read = br.readLine()) != null) {
					Log.i("Data", read);
					buffer.append(read);
				}
				array = new JSONArray(buffer.toString());
				// Close
				br.close();
				isr.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn.disconnect();
			}

			return array;
		}

		// 받아오는것이 완료된 시점
		@Override
		protected void onPostExecute(JSONArray array) {
			super.onPostExecute(array);
			mDialog.dismiss();

			mArray = array;

			Log.d("TEST", array.length() + "");

			result = new ArrayList<SearchResult>();
			for (int i = 0; i < mArray.length(); i++) {
				try {
					JSONObject object = (JSONObject) mArray.get(i);

					result.add(new SearchResult(object.getString("engine"),
							object.getString("title"), object.getString("URL"),
							object.getString("snippet"), object
									.getString("searchPage"), object
									.getDouble("exposure")));
					
					//index, title, riskImg
					double exposure = result.get(i).getExposure();
					if(exposure >= 120) riskImg = riskImgArray[2];
					else if(exposure < 120 && exposure >= 20) riskImg = riskImgArray[1];
					else if(exposure < 20) riskImg = riskImgArray[0];
					
					adapter.addItem(new IconTextItem(i+1 + "", result.get(i).getTitle(), riskImg));

					list.setAdapter(adapter);
					
					
					
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Log.d("TEST", result.size() + "");

			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						public void run() {

						}
					});
				}
			});
			t.start();
		}
	}

	// Action Bar Menu Control
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case android.R.id.home:
			intent = new Intent(ResultActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.change_info:
			intent = new Intent(ResultActivity.this, ChangeInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.signout:
			// 로그아웃
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return false;
	}

	// Back Button Control
	@Override
	public void onBackPressed() {
		backHandler.onBackPressed();
	}
}
