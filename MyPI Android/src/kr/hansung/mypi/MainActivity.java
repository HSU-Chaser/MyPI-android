package kr.hansung.mypi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private BackPressCloseHandler backHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayShowTitleEnabled(false);
		backHandler = new BackPressCloseHandler(MainActivity.this);
		setContentView(R.layout.activity_main);

		Button loginBtn = (Button) findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean signIn = true;

				// 로그인 구현필요

				if (signIn) {
					Intent intent = new Intent(getApplicationContext(),
							SearchActivity.class);
					startActivity(intent);
				} else {
					AlertDialog.Builder alert = new AlertDialog.Builder(
							MainActivity.this);
					alert.setTitle("알림");
					alert.setMessage("로그인을 실패하였습니다.");
					alert.setPositiveButton("확인", null);
					alert.show();
				}
			}
		});

		Button signUpBtn = (Button) findViewById(R.id.SignupBtn);
		signUpBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						SignUpActivity.class);
				startActivity(intent);
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
}
