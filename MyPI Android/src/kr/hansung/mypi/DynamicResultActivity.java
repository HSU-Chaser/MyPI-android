package kr.hansung.mypi;

import java.util.ArrayList;

import kr.list.BaseExpandableAdapter;
import kr.list.ChildItem;
import kr.list.GroupItem;
import kr.object.SearchResult;

import org.json.JSONArray;

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
import android.widget.TextView;
import android.widget.Toast;

public class DynamicResultActivity extends BaseActivity {
	private ExpandableListView mListView;

	public static ArrayList<GroupItem> mGroupList = new ArrayList<GroupItem>();
	public static ArrayList<ChildItem> mChildListContent = new ArrayList<ChildItem>();
	private ArrayList<ArrayList<ChildItem>> mChildList = new ArrayList<ArrayList<ChildItem>>();
	private BaseExpandableAdapter mBaseExpandableAdapter = null;

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

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);

		for (int i = 0; i < mGroupList.size(); i++) {
			ArrayList<ChildItem> tmpChild = new ArrayList<ChildItem>();
			tmpChild.add(mChildListContent.get(i));
			mChildList.add(tmpChild);
		}

		mBaseExpandableAdapter = new BaseExpandableAdapter(this, mGroupList,
				mChildList);

		mListView.setAdapter(mBaseExpandableAdapter);

		// 그룹 클릭 했을 경우 이벤트
		mListView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "g click = " + groupPosition, Toast.LENGTH_SHORT)
				// .show();

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
				Toast.makeText(getApplicationContext(),
						"c click = " + childPosition, Toast.LENGTH_SHORT)
						.show();
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
				// Toast.makeText(getApplicationContext(),
				// "g Expand = " + groupPosition, Toast.LENGTH_SHORT)
				// .show();

				int groupCount = mBaseExpandableAdapter.getGroupCount();

				// 한 그룹을 클릭하면 나머지 그룹들은 닫힌다.
				for (int i = 0; i < groupCount; i++) {
					if (!(i == groupPosition))
						mListView.collapseGroup(i);
				}
			}
		});

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
}
