package kr.hansung.mypi;

import android.app.Activity;
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
		backHandler = new BackPressCloseHandler(this);
		setContentView(R.layout.activity_main);

		Button changeInfoBtn = (Button) findViewById(R.id.changeBtn);
		changeInfoBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						ChangeInfoActivity.class);

				startActivity(intent);
			}
		});

		Button testBtn = (Button) findViewById(R.id.test);
		testBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						ResultActivity.class);
				startActivity(intent);
			}
		});
	}

	// Back Button Control
	@Override
	public void onBackPressed() {
		backHandler.onBackPressed();
	}
}
