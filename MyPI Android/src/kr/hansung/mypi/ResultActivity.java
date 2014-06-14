package kr.hansung.mypi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ResultActivity extends Activity {
	RelativeLayout layout;
	ProgressBar mProgress;
	ProgressDialog mDialog;
	int mStatus;
	JSONArray mArray;
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_screen);
		


		// Intent intent = getIntent();
		tv = (TextView) findViewById(R.id.plain);
		// mProgress = (ProgressBar) findViewById(R.id.progress_bar);

		// ListView resultList = (ListView) findViewById(R.id.result_list);

		new ResultTask().execute();
	}

	// AsyncTask class
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

		@Override
		protected void onPostExecute(JSONArray result) {
			super.onPostExecute(result);
			mDialog.dismiss();
			
			mArray = result;

			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						public void run() {
							tv.setText(mArray.toString());
						}
					});
				}
			});
			t.start();
		}
	}
}
