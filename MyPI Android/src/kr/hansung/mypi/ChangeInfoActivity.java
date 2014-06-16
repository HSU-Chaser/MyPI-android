package kr.hansung.mypi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class ChangeInfoActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		setContentView(R.layout.change_info);

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
