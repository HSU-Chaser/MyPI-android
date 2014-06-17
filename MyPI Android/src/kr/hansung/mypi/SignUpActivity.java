package kr.hansung.mypi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanaceState) {
		super.onCreate(savedInstanaceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_signup);

		Button signupBtn = (Button) findViewById(R.id.signupBtn);
		signupBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText emailEdit = (EditText) findViewById(R.id.email);
				EditText passwordEdit = (EditText) findViewById(R.id.password);
				EditText passwordCheckEdit = (EditText) findViewById(R.id.passwordCheck);
				String email = emailEdit.getText().toString();
				String password = passwordEdit.getText().toString();
				String passwordCheck = passwordCheckEdit.getText().toString();
				
				if(password.equals(passwordCheck)) {
					// 회원가입 진행
					
				} else {
					Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
				}
			}
		});

		Button cancelBtn = (Button) findViewById(R.id.cancelBtn);
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
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
