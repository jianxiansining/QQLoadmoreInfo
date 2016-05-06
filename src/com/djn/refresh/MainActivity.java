package com.djn.refresh;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnRefreshListener {

	private ListView lv;
	private List<String> list=new ArrayList<String>();
	private MyAdapter adapter;
	private List<String> listTest=new ArrayList<String>();
	
	// 下拉刷新
	private RefreshLayout swipeLayout;
	// 用于刷新的标记
	private boolean refreshFlag = false;
	private boolean flag=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		lv = (ListView) findViewById(R.id.lv);
		swipeLayout = (RefreshLayout) findViewById(R.id.test_swipe_container);
		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		
		initDataNum();
		
		adapter=new MyAdapter(this, list);
	    lv.setAdapter(adapter);
	    
	    
	    lv.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				// 当不滚动时
				case OnScrollListener.SCROLL_STATE_IDLE:
					// 判断滚动到顶部
					if (view.getFirstVisiblePosition() == 0) {
						swipeLayout.setEnabled(true);
					} else {
						swipeLayout.setEnabled(false);
					}

					break;
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
			}
		});
	}
	
	public void initDataNum(){
		if(!flag){
			for (int i = 1; i <=10; i++) {
				list.add("this is item "+i);
			}
			flag=true;
		}else{
			Log.d("listTest", list.size()+"::");
			listTest.clear();
			for (int i = list.size()+1; i <=list.size()+10; i++) {
				listTest.add("this is item "+i);
			}
			list.addAll(0,listTest);
			adapter.notifyDataSetChanged();
			lv.setSelection(10);
			swipeLayout.setRefreshing(false);
		}
	}
	
	@Override
	public void onRefresh() {
		swipeLayout.postDelayed(new Runnable() {

			@Override
			public void run() {
				initDataNum();
			}
		}, 0);
	}
}
