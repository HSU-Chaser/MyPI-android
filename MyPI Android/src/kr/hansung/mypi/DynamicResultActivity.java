package kr.hansung.mypi;

import java.util.ArrayList;

import kr.list.BaseExpandableAdapter;
import kr.list.IconTextItem;
import kr.object.SearchResult;

import org.json.JSONArray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DynamicResultActivity extends Activity {
	private BackPressCloseHandler backHandler;
	private ExpandableListView mListView;

	public static ArrayList<IconTextItem> mGroupList = new ArrayList<IconTextItem>();
	private ArrayList<ArrayList<String>> mChildList = new ArrayList<ArrayList<String>>();
	private ArrayList<String> mChildListContent = new ArrayList<String>();

	RelativeLayout layout;
	ProgressBar mProgress;
	ProgressDialog mDialog;
	ArrayList<SearchResult> result;
	ViewGroup.LayoutParams params;
	JSONArray mArray;
	TextView tv;
	Button resultBtn;

	//
	// DataListView list;
	// IconTextListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_result);

		setLayout();

		mChildList.clear();
		mChildListContent.clear();

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		backHandler = new BackPressCloseHandler(this);

		// list = new DataListView(this);
		// adapter = new IconTextListAdapter(this);

		Log.d("TEST", "그룹의 사이즈 : " + mGroupList.size() + "");

		mChildListContent.add("12345");
		for (int i = 0; i < mGroupList.size(); i++) {
			mChildList.add(mChildListContent);
		}

		Log.d("TEST", "그룹 사이즈 : " + mGroupList.size() + "");
		Log.d("TEST", "그룹 string 테스트 : " + mChildList.get(0).get(0) + "");
		Log.d("TEST", "그룹 string 테스트 : " + mChildList.get(14).get(0) + "");
		Log.d("TEST", "그룹 string 테스트 : " + mChildList.get(53).get(0) + "");
		
		
		mListView.setAdapter(new BaseExpandableAdapter(
				DynamicResultActivity.this, mGroupList, mChildList));

	}

	private void setLayout() {
		mListView = (ExpandableListView) findViewById(R.id.list);
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
			intent = new Intent(DynamicResultActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.change_info:
			intent = new Intent(DynamicResultActivity.this,
					ChangeInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.signout:
			// 로그아웃
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return false;
	}

	// Back Button Control
	@Override
	public void onBackPressed() {
		backHandler.onBackPressed();
	}
}
