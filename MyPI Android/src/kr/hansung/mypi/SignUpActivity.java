package kr.hansung.mypi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends BaseActivity {
	EditText emailEdit, passwordEdit, passwordCheckEdit;

	@Override
	protected void onCreate(Bundle savedInstanaceState) {
		super.onCreate(savedInstanaceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_signup);

		emailEdit = (EditText) findViewById(R.id.email);
		passwordEdit = (EditText) findViewById(R.id.password);
		passwordCheckEdit = (EditText) findViewById(R.id.passwordCheck);

		Button signupBtn = (Button) findViewById(R.id.signupBtn);
		signupBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new SignUpTask().execute();
			}
		});

		Button cancelBtn = (Button) findViewById(R.id.cancelBtn);
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	class SignUpTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			String email = emailEdit.getText().toString();
			String password = passwordEdit.getText().toString();
			String passwordCheck = passwordCheckEdit.getText().toString();

			if (password.equals(passwordCheck)) {
				// 회원가입 진행
				HttpURLConnection conn = null;
				String param = "email=" + email + "&password=" + password;
				Log.i("param", param);

				try {
					URL url = new URL(
							"http://mypi.co.kr/mobileSignup.jsp");
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("POST");
					conn.setDoInput(true);
					conn.setDoOutput(true);

					// Parameter 전달
					OutputStream os = conn.getOutputStream();
					os.write(param.getBytes());
					os.flush();
					os.close();

					// Response (Garbage)
					InputStreamReader isr = new InputStreamReader(
							conn.getInputStream(), "UTF-8");
					BufferedReader br = new BufferedReader(isr);
					String status = br.readLine();
					Log.i("Email", status);
					br.close();
					isr.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					conn.disconnect();
				}
				return true;
			} else {
				return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			if (result) {
				Toast.makeText(getApplicationContext(), "회원가입 완료",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	// Action Bar Menu Control
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case android.R.id.home:
			intent = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return false;
	}
}
