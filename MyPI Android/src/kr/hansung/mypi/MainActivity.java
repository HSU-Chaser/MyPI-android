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
		setContentView(R.layout.activity_main);

		Button changeInfoBtn = (Button) findViewById(R.id.changeBtn);

		changeInfoBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						ChangeInfoActivity.class);

				startActivity(intent);
			}
		});
	}
}
