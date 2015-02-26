package br.com.dina.oauth.instagram.example;

import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//Adapter class extends with BaseAdapter and implements with OnClickListener 
public class ListLoader extends BaseAdapter implements OnClickListener {

	private Activity activity;
	private List<String> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	private Animator mCurrentAnimator;

    private int mShortAnimationDuration;
    
	public ListLoader(Activity a, List<String> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Create ImageLoader object to download and show image in list
		// Call ImageLoader constructor to initialize FileCache
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView text;
		public ImageView image;

	}

	public View getView(int position, View convertView, ViewGroup parent)
	{

		View vi = convertView;
		ViewHolder holder;

		if (convertView == null)
		{

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.list_row, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.text = (TextView) vi.findViewById(R.id.textView1);
			holder.image = (ImageView) vi.findViewById(R.id.imageView1);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

//		holder.text.setText("" + position);
		ImageView image = holder.image;

		// DisplayImage function from ImageLoader Class
		imageLoader.DisplayImage(data.get(position), image);

		/******** Set Item Click Listner for LayoutInflater for each row ***********/
		vi.setOnClickListener(new OnItemClickListener(position));
		return vi;
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
		if (which < 0 || which > data.size()) return;		
		data.remove(which);
	}

	public void onDrop(int from, int to) {
		String temp = data.get(from);
		data.remove(from);
		data.add(to,temp);
	}
	
}
