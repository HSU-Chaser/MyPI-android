package kr.list;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class DataListView extends ListView{

	public DataListView(Context context) {
		super(context);

		init();
		
	}
	
	public DataListView(Context context, AttributeSet attrs) {
		super(context);
		
		init();
	}

	private void init() {
		setOnItemClickListener(new OnItemClickAdapter());
		
	}
	
	public void setOnDataSelectionListener(OnDataSelectionListener listener){
		this.selectionListener = listener;
	}
	
	public OnDataSelectionListener getOnDataSelectionListener(){
		return selectionListener;
	}
	
	class OnItemClickAdapter implements OnItemClickListener {
		public OnItemClickAdapter(){
			
		}
		
		public void onItemClick(AdapterView parent, View v, int position, long id){
			if(selectionListener == null){
				return;
			}
			
			selectionListener.onDataSelected(parent, v, position, id);
		}
	}
}
