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

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		IconTextView itemView;
		if (convertView == null) {
			itemView = new IconTextView(mContext);
		} else {
			itemView = (IconTextView) convertView;
		}

		// set 하고

		return itemView;
	}

}
