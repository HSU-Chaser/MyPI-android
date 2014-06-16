package kr.list;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class IconTextListAdapter extends BaseAdapter {

	private Context mContext;

	private List<IconTextItem> mItems = new ArrayList<IconTextItem>();

	public IconTextListAdapter(Context context) {
		mContext = context;
	}

	public void addItem(IconTextItem it) {
		mItems.add(it);
	}
	
	public void setListItems(List<IconTextItem> lit) {
		mItems = lit;
	}
	
	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		IconTextView itemView;
		if (convertView == null) {
			itemView = new IconTextView(mContext, mItems.get(position));
		} else {
			itemView = (IconTextView) convertView;
			
			itemView.setText(0, mItems.get(position).getData(0));
			itemView.setText(1, mItems.get(position).getData(1));
			itemView.setIcon(mItems.get(position).getIcon());
		}

		return itemView;
	}

}
