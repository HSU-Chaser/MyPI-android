package kr.hansung.mypi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class SignUpActivity extends Activity {
	public void onCreate(Bundle savedInstanaceState) {
		super.onCreate(savedInstanaceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_signup);
	}
	
	// Action Bar Menu Control
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case android.R.id.home:
			intent = new Intent(SignUpActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return false;
	}
}
