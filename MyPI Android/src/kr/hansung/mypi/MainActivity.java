package kr.hansung.mypi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_main);

		Button loginBtn = (Button) findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						DynamicResultActivity.class);
				startActivity(intent);
			}
		});

		Button signUpBtn = (Button) findViewById(R.id.SignupBtn);
		signUpBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
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
}
