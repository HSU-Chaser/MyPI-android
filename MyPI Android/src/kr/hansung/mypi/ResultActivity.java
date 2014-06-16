package kr.hansung.mypi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import kr.list.DataListView;
import kr.list.IconTextListAdapter;
import kr.object.SearchResult;
import kr.object.StaticItem;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ResultActivity extends Activity {
	protected static final int REQUEST_CODE_ANOTHER = 101;
	// private BackPressCloseHandler backHandler;
	private ResultActivity resultActivity = this;
	private ExpandableListView mListView;

	RelativeLayout layout;
	ProgressBar mProgress;
	ProgressDialog mDialog;

	String grade, gradeExp;
	ArrayList<String> imageResult;
	ArrayList<StaticItem> staticResult;
	ArrayList<SearchResult> dynamicResult;
	ViewGroup.LayoutParams params;

	TextView tv;

	TextView safeText1, safeText2;
	Button resultBtn;

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
		// setContentView(R.layout.activity_result);
		// backHandler = new BackPressCloseHandler(this);
		params = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		// Intent intent = getIntent();
		// tv = (TextView) findViewById(R.id.plain);
		// mProgress = (ProgressBar) findViewById(R.id.progress_bar);

		// ListView resultList = (ListView) findViewById(R.id.result_list);

		resultBtn = (Button) findViewById(R.id.resultBtn);
		safeText1 = (TextView) findViewById(R.id.safeText1);
		safeText2 = (TextView) findViewById(R.id.safeText2);

		riskImgArray = new Drawable[3];

		riskImgArray[0] = getResources().getDrawable(R.drawable.risk_low);
		riskImgArray[1] = getResources().getDrawable(R.drawable.risk_mid);
		riskImgArray[2] = getResources().getDrawable(R.drawable.risk_high);

		list = new DataListView(this);
		adapter = new IconTextListAdapter(this);

		// adapter.addItem(new IconTextItem("1", "제목이 나와야 할 부분", riskImg[0]));
		// adapter.addItem(new IconTextItem("2", "제목이 나와야 할 부분", riskImg[1]));

		new ResultTask().execute();

		resultBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(getBaseContext(),
						DynamicResultActivity.class);
				startActivityForResult(intent, REQUEST_CODE_ANOTHER);
			}
		});
	}

	class ResultTask extends AsyncTask<Void/* 로그인 정보 필요 */, Void, JSONObject> {
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
		protected JSONObject doInBackground(Void... params) {
			HttpURLConnection conn = null;
			StringBuffer buffer = null;
			JSONObject array = null;

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
				array = new JSONObject(buffer.toString());
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
		protected void onPostExecute(JSONObject object) {
			super.onPostExecute(object);
			mDialog.dismiss();

			parseJSON(object);

			// UI Update
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

	private void parseJSON(JSONObject object) {
		String grade = null;
		String gradeExp = null;
		JSONArray imageSearch = null;
		JSONArray staticSearch = null;
		JSONArray dynamicSearch = null;

		try {
			grade = object.getString("grade");
			gradeExp = object.getString("gradeExp");
			imageSearch = object.getJSONArray("image");
			staticSearch = object.getJSONArray("static");
			dynamicSearch = object.getJSONArray("dynamic");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// Grade
		this.grade = grade;
		this.gradeExp = gradeExp;

		// Image Search
		imageResult = new ArrayList<String>();
		for (int i = 0; i < imageSearch.length(); i++) {
			try {
				JSONObject item = (JSONObject) imageSearch.get(i);
				imageResult.add(item.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Static Search
		staticResult = new ArrayList<StaticItem>();
		for (int i = 0; i < staticSearch.length(); i++) {
			try {
				JSONObject item = (JSONObject) staticSearch.get(i);

				staticResult.add(new StaticItem(item.getString("siteImage"),
						item.getString("siteName"), item.getString("url")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Dynamic Search
		dynamicResult = new ArrayList<SearchResult>();
		for (int i = 0; i < dynamicSearch.length(); i++) {
			try {
				JSONObject item = (JSONObject) dynamicSearch.get(i);

				dynamicResult.add(new SearchResult(item.getString("engine"),
						item.getString("title"), item.getString("URL"), item
								.getString("snippet"), item
								.getString("searchPage"), item
								.getDouble("exposure")));
			} catch (JSONException e) {
				e.printStackTrace();
			}
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

	/*
	 * // Back Button Control
	 * 
	 * @Override public void onBackPressed() { backHandler.onBackPressed(); }
	 */
}
