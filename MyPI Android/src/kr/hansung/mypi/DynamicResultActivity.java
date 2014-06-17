package kr.hansung.mypi;

import java.util.ArrayList;

import kr.list.BaseExpandableAdapter;
import kr.list.ChildItem;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class DynamicResultActivity extends BaseActivity {
	private ExpandableListView mListView;

	private ArrayList<ArrayList<ChildItem>> mChildList = new ArrayList<ArrayList<ChildItem>>();
	private BaseExpandableAdapter mBaseExpandableAdapter = null;

	RelativeLayout layout;
	ProgressBar mProgress;
	ProgressDialog mDialog;
	ViewGroup.LayoutParams params;

	Button urlLinkBtn, linkDeleteBtn, centerBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_dynamic_result);

		mListView = (ExpandableListView) findViewById(R.id.list);
		mChildList.clear();

		for (int i = 0; i < ResultActivity.mGroupList.size(); i++) {
			ArrayList<ChildItem> tmpChild = new ArrayList<ChildItem>();
			tmpChild.add(ResultActivity.mChildListContent.get(i));
			mChildList.add(tmpChild);
		}

		mBaseExpandableAdapter = new BaseExpandableAdapter(this,
				ResultActivity.mGroupList, mChildList);

		mListView.setAdapter(mBaseExpandableAdapter);

		// 그룹 클릭 했을 경우 이벤트
		mListView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Listener 에서 Adapter 사용법 (getExpandableListAdapter 사용해야함.)
				// BaseExpandableAdpater에 오버라이드 된 함수들을 사용할 수 있다.
				int groupCount = (int) parent.getExpandableListAdapter()
						.getGroupCount();
				int childCount = (int) parent.getExpandableListAdapter()
						.getChildrenCount(groupPosition);

				return false;
			}
		});

		mListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// parent.getExpandableListAdapter().getGroup(groupPosition).
				return false;
			}

		});

		// 차일드 클릭 했을 경우 이벤트
		mListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				return false;
			}
		});

		// 그룹이 닫힐 경우 이벤트
		mListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {

			}
		});

		// 그룹이 열릴 경우 이벤트
		mListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				int groupCount = mBaseExpandableAdapter.getGroupCount();

				// 한 그룹을 클릭하면 나머지 그룹들은 닫힌다.
				for (int i = 0; i < groupCount; i++) {
					if (!(i == groupPosition))
						mListView.collapseGroup(i);
				}
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
			intent = new Intent(DynamicResultActivity.this,
					SearchActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.change_info:
			intent = new Intent(DynamicResultActivity.this,
					ChangeInfoActivity.class);
			startActivity(intent);
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
