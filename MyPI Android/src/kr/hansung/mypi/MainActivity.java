package kr.hansung.mypi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
	protected static final int REQUEST_CODE_MAIN = 100;
	private BackPressCloseHandler backHandler;
	EditText emailEntry;
	EditText passwordEntry;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayShowTitleEnabled(false);
		backHandler = new BackPressCloseHandler(MainActivity.this);

		setContentView(R.layout.activity_main);

		emailEntry = (EditText) findViewById(R.id.EmailEntry);
		passwordEntry = (EditText) findViewById(R.id.passwordEntry);

		Button loginBtn = (Button) findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new SignInTask().execute();
			}
		});

		Button signUpBtn = (Button) findViewById(R.id.SignupBtn);
		signUpBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						SignUpActivity.class);
				startActivityForResult(intent, REQUEST_CODE_MAIN);
			}
		});

		Button exitBtn = (Button) findViewById(R.id.exitBtn);
		exitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	// Back Button Control
	@Override
	public void onBackPressed() {
		backHandler.onBackPressed();
	}

	class SignInTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// 로그인 요청
			HttpURLConnection conn = null;
			String param = "email=" + emailEntry.getText().toString()
					+ "&password=" + passwordEntry.getText().toString();
			String status = null;

			try {
				URL url = new URL("http://mypi.co.kr/mobileSignin.jsp");
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setDoInput(true);
				conn.setDoOutput(true);

				// Parameter 전달
				OutputStream os = conn.getOutputStream();
				os.write(param.getBytes());
				os.flush();
				os.close();

				// Response
				InputStreamReader isr = new InputStreamReader(
						conn.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				status = br.readLine();
				Log.i("Status", status);
				br.close();
				isr.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn.disconnect();
			}

			if (status.equals("success")) {
				// 쿠키 받아오기
				CookieSyncManager.createInstance(getApplicationContext());
				setCookieManager(CookieManager.getInstance());
				String cookies = "";
				Map<String, List<String>> m = conn.getHeaderFields();

				if (m.containsKey("Set-Cookie")) {
					Collection<?> c = (Collection<?>) m.get("Set-Cookie");
					for (Iterator<?> i = c.iterator(); i.hasNext();) {
						cookies += (String) i.next() + ", ";
						getCookieManager().setCookie("http://mypi.co.kr/",
								cookies);
						Log.i("Cookie", cookies);
					}
					CookieSyncManager.getInstance().sync();
				}
				return true;
			} else
				return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			// 요청 결과
			if (result) {
				Toast.makeText(MainActivity.this, "로그인을 성공하였습니다.",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(),
						SearchActivity.class);
				startActivityForResult(intent, REQUEST_CODE_MAIN);
			} else {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						MainActivity.this);
				alert.setTitle("알림");
				alert.setMessage("로그인을 실패하였습니다.");
				alert.setPositiveButton("확인", null);
				alert.show();
			}
		}
	}

	class BackPressCloseHandler {
		private long backKeyPressedTime = 0;
		private Toast toast;

		private Activity activity;

		public BackPressCloseHandler(Activity context) {
			this.activity = context;
		}

		public void onBackPressed() {
			if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
				backKeyPressedTime = System.currentTimeMillis();
				showExitMessage();
				return;
			}
			if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
				activity.finish();
				toast.cancel();
			}
		}

		private void showExitMessage() {
			toast = Toast.makeText(activity, "'뒤로'버튼을 한번 더 누르면 종료합니다.",
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}

}
