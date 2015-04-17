
package br.com.oauth.instagram.example;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public final class DragNDropAdapter extends BaseAdapter implements RemoveListener, DropListener , OnClickListener {

	private int[] mIds;
    private int[] mLayouts;
    private LayoutInflater mInflater;
    private ArrayList<String> mContent;
    private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
    static String TAG = DragNDropAdapter.class.getCanonicalName();

    public DragNDropAdapter(Context context, ArrayList<String> content) {
        init(context,new int[]{android.R.layout.simple_list_item_1},new int[]{android.R.id.text1}, content);
    }
    
    public DragNDropAdapter(Context context, int[] itemLayouts, int[] itemIDs, ArrayList<String> content) {
    	init(context,itemLayouts,itemIDs, content);
    }

    private void init(Context context, int[] layouts, int[] ids, ArrayList<String> content) {
    	// Cache the LayoutInflate to avoid asking for a new one each time.
    	mInflater = LayoutInflater.from(context);
    	mIds = ids;
    	mLayouts = layouts;
    	mContent = content;
    	imageLoader = new ImageLoader(context);
    	Log.e(TAG, "content 111 ---" + mContent);
    }
    
    /**
     * The number of items in the list
     * @see android.widget.ListAdapter#getCount()
     */
    public int getCount() {
        return mContent.size();
    }

    /**
     * Since the data comes from an array, just returning the index is
     * sufficient to get at the data. If we were using a more complex data
     * structure, we would return whatever object represents one row in the
     * list.
     *
     * @see android.widget.ListAdapter#getItem(int)
     */
    public String getItem(int position) {
        return mContent.get(position);
    }

    /**
     * Use the array index as a unique id.
     * @see android.widget.ListAdapter#getItemId(int)
     */
    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView text;
		public ImageView image;

	}

	/*public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;

		try {
			Log.e(TAG, "getView ---" + mContent);
			if (convertView == null) {

				*//****** Inflate tabitem.xml file for each row ( Defined below ) *******//*
				vi = inflater.inflate(R.layout.list_row, null);

				*//****** View Holder Object to contain tabitem.xml file elements ******//*

				holder = new ViewHolder();
				holder.text = (TextView) vi.findViewById(R.id.textView1);
				holder.image = (ImageView) vi.findViewById(R.id.imageView1);

				*//************ Set holder with LayoutInflater ************//*
				vi.setTag(holder);
			} else {
				holder = (ViewHolder) vi.getTag();
			}

			ImageView image = holder.image;
			Log.e(TAG, "content ---" + mContent);
			// DisplayImage function from ImageLoader Class
			imageLoader.DisplayImage(mContent.get(position), image);

			*//******** Set Item Click Listner for LayoutInflater for each row ***********//*
			vi.setOnClickListener(new OnItemClickListener(position));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vi;
	}*/
	
	 public View getView(int position, View convertView, ViewGroup parent) {
	        // A ViewHolder keeps references to children views to avoid unneccessary calls
	        // to findViewById() on each row.
	        ViewHolder holder;

	        // When convertView is not null, we can reuse it directly, there is no need
	        // to reinflate it. We only inflate a new View when the convertView supplied
	        // by ListView is null.
	        if (convertView == null) {
	            convertView = mInflater.inflate(mLayouts[0], null);

	            // Creates a ViewHolder and store references to the two children views
	            // we want to bind data to.
	            holder = new ViewHolder();
	            holder.text = (TextView) convertView.findViewById(R.id.textView1);
	            holder.image = (ImageView) convertView.findViewById(mIds[0]);

	            convertView.setTag(holder);
	        } else {
	            // Get the ViewHolder back to get fast access to the TextView
	            // and the ImageView.
	            holder = (ViewHolder) convertView.getTag();
	        }
	        
	        ImageView image = holder.image;
			Log.e(TAG, "content ---" + mContent);
			// DisplayImage function from ImageLoader Class
			imageLoader.DisplayImage(mContent.get(position), image);

			/******** Set Item Click Listner for LayoutInflater for each row ***********/
			image.setOnClickListener(new OnItemClickListener(position));
	        // Bind the data efficiently with the holder.
	        holder.text.setText(mContent.get(position));
	        holder.text.setVisibility(View.INVISIBLE);
	        return convertView;
	    }

	@Override
	public void onClick(View arg0)
	{
		// TODO Auto-generated method stub

	}

	/********* Called when Item click in ListView ************/
	private class OnItemClickListener implements OnClickListener {
		private int mPosition;

		OnItemClickListener(int position)
		{
			mPosition = position;
		}

		@Override
		public void onClick(View arg0)
		{
			TouchImageView img = (TouchImageView) arg0.findViewById(R.id.imageView1);
	        img.setMaxZoom(4);
		}
	}
	

	public void onRemove(int which) {
		Log.e(TAG, "inside onRemove -- " + which + "::" + mContent);
		if (which < 0 || which > mContent.size()) return;		
		mContent.remove(which);
	}

	public void onDrop(int from, int to) {
		String temp = mContent.get(from);
		Log.e(TAG, "inside onDrop -- FROM= " + from + ":: TO=" + to + ":: temp" + temp);
		mContent.remove(from);
		mContent.add(to,temp);
	}

}
