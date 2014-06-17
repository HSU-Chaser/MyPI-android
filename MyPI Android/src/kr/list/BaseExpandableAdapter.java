package kr.list;

import java.util.ArrayList;

import kr.hansung.mypi.R;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BaseExpandableAdapter extends BaseExpandableListAdapter {
	private ArrayList<GroupItem> groupList = null;
	private ArrayList<ArrayList<ChildItem>> childList = null;
	private LayoutInflater inflater;
	private ViewHolder viewHolder = null;
	private Context mContext = null;

	public BaseExpandableAdapter(Context context,
			ArrayList<GroupItem> groupList,
			ArrayList<ArrayList<ChildItem>> childList) {
		super();
		this.mContext = context;
		this.inflater = LayoutInflater.from(context);
		this.groupList = groupList;
		this.childList = childList;
	}

	// 그룹 포지션을 반환한다.
	@Override
	public GroupItem getGroup(int groupPosition) {
		return groupList.get(groupPosition);
	}

	// 그룹 사이즈를 반환한다.
	@Override
	public int getGroupCount() {
		return groupList.size();
	}

	// 그룹 ID를 반환한다.
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	// 그룹뷰 각각의 ROW
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {
			viewHolder = new ViewHolder();
			v = inflater.inflate(R.layout.listitem, parent, false);
			viewHolder.index = (TextView) v.findViewById(R.id.indexText);
			viewHolder.title = (TextView) v.findViewById(R.id.titleText);
			viewHolder.riskImg = (ImageView) v.findViewById(R.id.riskImg);
			v.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) v.getTag();
		}

		// 그룹을 펼칠때와 닫을때 아이콘을 변경해 준다.
		// if (isExpanded) {
		// viewHolder.riskImg.setBackgroundColor(Color.GREEN);
		// } else {
		// viewHolder.riskImg.setBackgroundColor(Color.WHITE);
		// }

		viewHolder.index.setText(getGroup(groupPosition).getData(0));
		viewHolder.title.setText(getGroup(groupPosition).getData(1));
		viewHolder.riskImg.setImageDrawable(getGroup(groupPosition).getIcon());

		return v;
	}

	// 차일드뷰를 반환한다.
	@Override
	public ChildItem getChild(int groupPosition, int childPosition) {
		return childList.get(groupPosition).get(childPosition);
	}

	// 차일드뷰 사이즈를 반환한다.
	@Override
	public int getChildrenCount(int groupPosition) {
		return childList.get(groupPosition).size();
	}

	// 차일드뷰 ID를 반환한다.
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	// 차일드뷰 각각의 ROW
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {
			viewHolder = new ViewHolder();
			v = inflater.inflate(R.layout.childitem, null);
			viewHolder.snippet = (TextView) v.findViewById(R.id.snippet);
			viewHolder.solution1 = (TextView) v.findViewById(R.id.solution1);
			viewHolder.solution2 = (TextView) v.findViewById(R.id.solution2);

			viewHolder.urlLinkBtn = (Button) v.findViewById(R.id.urlLinkBtn);
			viewHolder.linkDeleteBtn = (Button) v
					.findViewById(R.id.linkDeleteBtn);
			viewHolder.centerBtn = (Button) v.findViewById(R.id.centerBtn);

			v.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) v.getTag();
		}

		// Set Snippet, Solution1, Solution2
		viewHolder.snippet.setText(Html.fromHtml(getChild(groupPosition,
				childPosition).getSnippet()));
		viewHolder.solution1.setText(Html.fromHtml(getChild(groupPosition,
				childPosition).getSolution1()));
		viewHolder.solution2.setText(Html.fromHtml(getChild(groupPosition,
				childPosition).getSolution2()));

		if (getChild(groupPosition, childPosition).getSolution2().length() == 0) {
			viewHolder.solution2.setText("검색결과에 대한 특정화된 솔루션이 존재하지 않습니다.");
		}

		// Button Link
		final String urlLink = getChild(groupPosition, childPosition).getUrl();
		final String urlDelete = getChild(groupPosition, childPosition)
				.getUrlDelete();
		Log.i("urlDelete", groupPosition + " " + urlDelete);
		final String center = getChild(groupPosition, childPosition)
				.getCenter();

		viewHolder.urlLinkBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(urlLink));
				v.getContext().startActivity(intent);
			}
		});
		viewHolder.linkDeleteBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(urlDelete));
				v.getContext().startActivity(intent);
			}
		});
		viewHolder.centerBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (center.equals("")) {
					Toast.makeText(mContext, "링크를 클릭하여 직접 삭제해야 합니다.",
							Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(center));
					v.getContext().startActivity(intent);
				}
			}
		});

		return v;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	class ViewHolder {
		public TextView index, title;
		public ImageView riskImg;

		public TextView snippet, solution1, solution2;
		public Button urlLinkBtn, linkDeleteBtn, centerBtn;
	}
}
