package kr.hansung.mypi;

import android.app.AlertDialog;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
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
					Toast.makeText(getApplicationContext(), "오른쪽 상단에 회원정보 수정 버튼을 눌러 정보를 수정합니다.", Toast.LENGTH_LONG).show();
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
					startActivity(intent);
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
		case android.R.id.home:
			intent = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.change_info:
			intent = new Intent(getApplicationContext(),
					ChangeInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.signout:
			AlertDialog.Builder alert = new AlertDialog.Builder(
					getApplicationContext());
			alert.setTitle("로그아웃");
			alert.setMessage("로그아웃 하시겠습니까?");
			alert.setPositiveButton("확인", null);
			alert.setNegativeButton("취소", null);
			alert.show();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return false;
	}
}
