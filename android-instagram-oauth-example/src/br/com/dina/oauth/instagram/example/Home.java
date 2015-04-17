package br.com.oauth.instagram.example;

import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Home extends ListActivity {
	static String TAG = Home.class.getCanonicalName();

	ListView m_listView;

//	ListLoader m_listLoader;
	DragNDropAdapter adapter;
	
	String m_strHistory = "";
	
	ArrayList<String> imagesData = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		Bundle b = getIntent().getExtras();
		if(b != null)
		{
			m_strHistory = b.getString("HISTORY");
			
			Log.d("HISTORY", m_strHistory);
		}
		
		try
		{
			JSONArray jArray = new JSONArray(m_strHistory);
			
			for(int i=0; i<jArray.length(); i++)
			{
				JSONObject jObj = jArray.getJSONObject(i);
				JSONArray jjArray = jObj.getJSONArray("tags");
				if(jjArray.length() != 0)
				{
					JSONObject jj = jObj.getJSONObject("images").getJSONObject("low_resolution");
					String imageUrlString = jj.getString("url");
					
					URL url = new URL(imageUrlString);
					
					imagesData.add(url.toString());
				}
				
			}
			Log.e(TAG , "imagesData --- " + imagesData);
				
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		m_listView = (ListView) findViewById(R.id.listView1);
		
//		m_listLoader = new ListLoader(this, imagesData);
//		setListAdapter(m_listLoader);
		
		adapter = new DragNDropAdapter(this, new int[]{R.layout.list_row}, new int[]{R.id.imageView1}, imagesData);//new DragNDropAdapter(this,content)
		setListAdapter(adapter);
		
		 ListView listView = getListView();
	        
	        if (listView instanceof DragNDropListView) {
	        	((DragNDropListView) listView).setDropListener(mDropListener);
	        	((DragNDropListView) listView).setRemoveListener(mRemoveListener);
	        	((DragNDropListView) listView).setDragListener(mDragListener);
	        }
	}
		private DropListener mDropListener = 
				new DropListener() {
		        public void onDrop(int from, int to) {
//		        	Log.e(TAG, "inside mDropListener -- ");
		        	ListAdapter adapter = getListAdapter();
		        	if (adapter instanceof DragNDropAdapter) {
		        		((DragNDropAdapter)adapter).onDrop(from, to);
		        		getListView().invalidateViews();
		        	}
		        }
		    };
		    
		    private RemoveListener mRemoveListener =
		        new RemoveListener() {
		        public void onRemove(int which) {
//		        	Log.e(TAG, "inside mRemoveListener -- ");
		        	ListAdapter adapter = getListAdapter();
		        	if (adapter instanceof DragNDropAdapter) {
		        		((DragNDropAdapter)adapter).onRemove(which);
		        		getListView().invalidateViews();
		        	}
		        }
		    };
		    
		    private DragListener mDragListener =
		    	new DragListener() {

		    	int backgroundColor = 0xe0103010;
		    	int defaultBackgroundColor;
		    	
					public void onDrag(int x, int y, ListView listView) {
						// TODO Auto-generated method stub
//						Log.e(TAG, "inside onDrag -- ");
					}

					public void onStartDrag(View itemView) {
//						Log.e(TAG, "inside onStartDrag -- ");
						itemView.setVisibility(View.INVISIBLE);
						defaultBackgroundColor = itemView.getDrawingCacheBackgroundColor();
						itemView.setBackgroundColor(backgroundColor);
//						ImageView iv = (ImageView)itemView.findViewById(R.id.imageView1);
//						if (iv != null) iv.setVisibility(View.INVISIBLE);
					}

					public void onStopDrag(View itemView) {
//						Log.e(TAG, "inside onStopDrag -- ");
						itemView.setVisibility(View.VISIBLE);
						itemView.setBackgroundColor(defaultBackgroundColor);
						ImageView iv = (ImageView)itemView.findViewById(R.id.imageView1);
						if (iv != null) iv.setVisibility(View.VISIBLE);
					}
		    	
		    };
}
