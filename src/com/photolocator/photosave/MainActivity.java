package com.photolocator.photosave;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.photolocator.R;

public class MainActivity extends Activity implements OnItemClickListener,SetbackList
{
	private static final String rssFeed = "https://www.dropbox.com/s/t4o5wo6gdcnhgj8/imagelistview.xml?dl=1";

	
	ProgressDialog pDialog;
	List<Item> arrayOfList;
	ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		listView = (ListView) findViewById(R.id.listview);
		listView.setOnItemClickListener(this);

		if (Utils.isNetworkAvailable(MainActivity.this))
		{
			new MyTask().execute(rssFeed);
		}
		else
		{
			showToast("No Network Connection!!!");
		}

	}

	// My AsyncTask start...

	class MyTask extends AsyncTask<String, Void, Void>
	{

		

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();

			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Loading...");
			pDialog.show();

		}

		@Override
		protected Void doInBackground(String... params)
		{
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
			new NamesParser(MainActivity.this).getData(MainActivity.this,prefs.getString("username", null));
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);

		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Item item = arrayOfList.get(position);
		Intent intent = new Intent(MainActivity.this, DetailActivity.class);
		intent.putExtra("bitmap", item.getBitmap());
		intent.putExtra("title", item.getTitle());
		intent.putExtra("desc", item.getDesc());
		startActivity(intent);
	}

	public void setAdapterToListview()
	{
		NewsRowAdapter objAdapter = new NewsRowAdapter(MainActivity.this, R.layout.row, arrayOfList);
		listView.setAdapter(objAdapter);
	}

	public void showToast(String msg)
	{

	}

	@Override
	public void setbackList(List<Item> l) {
		// TODO Auto-generated method stub
		arrayOfList=l;
		if (null != pDialog && pDialog.isShowing())
		{
			pDialog.dismiss();
		}

		if (null == arrayOfList || arrayOfList.size() == 0)
		{
			showToast("No data found from web!!!");
			MainActivity.this.finish();
		}
		else
		{

			// check data...
			/*
			 * for (int i = 0; i < arrayOfList.size(); i++) { Item item = arrayOfList.get(i);
			 * System.out.println(item.getId()); System.out.println(item.getTitle());
			 * System.out.println(item.getDesc()); System.out.println(item.getPubdate());
			 * System.out.println(item.getLink()); }
			 */

			runOnUiThread(new Runnable() {
			     public void run() {
			    	 setAdapterToListview();
			    }
			});
			

		}
	}
}
