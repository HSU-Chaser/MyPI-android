package kr.hansung.mypi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

public class SearchActivity extends BaseActivity {
	protected static final int REQUEST_CODE_SEARCH = 101;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_search);

		ImageView helpBtn = (ImageView) findViewById(R.id.helpBtn);
		helpBtn.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView view = (ImageView) v;

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					view.setImageResource(R.drawable.modal_p);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					view.setImageResource(R.drawable.modal);
					Toast.makeText(getApplicationContext(),
							"오른쪽 상단에 회원정보 수정 버튼을 눌러 정보를 수정합니다.",
							Toast.LENGTH_LONG).show();
				}
				return true;
			}
		});

		ImageView searchBtn = (ImageView) findViewById(R.id.searchBtn);
		searchBtn.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView view = (ImageView) v;

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					view.setImageResource(R.drawable.search_p);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					view.setImageResource(R.drawable.search);
					Intent intent = new Intent(getApplicationContext(),
							ResultActivity.class);
					startActivityForResult(intent, REQUEST_CODE_SEARCH);
				}
				return true;
			}
		});

	}

	// Action Bar Menu Control
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.change_info:
			intent = new Intent(getApplicationContext(),
					ChangeInfoActivity.class);
			startActivityForResult(intent, REQUEST_CODE_SEARCH);
			break;
		case R.id.signout:
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
