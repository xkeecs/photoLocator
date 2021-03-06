package com.photolocator.photosave;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoadingListener;
import com.photolocator.R;

public class DetailActivity extends Activity
{
	private DisplayImageOptions options;
	private ImageLoader imageLoader;

	private ProgressBar pbar;
	private TextView tvTitle, tvDesc;
	private ImageView imgView;
	private Button mapButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);

		pbar = (ProgressBar) findViewById(R.id.pbardesc);
		tvTitle = (TextView) findViewById(R.id.tvtitle);
		tvDesc = (TextView) findViewById(R.id.tvdesc);
		imgView = (ImageView) findViewById(R.id.imgdesc);
		mapButton = (Button) findViewById(R.id.button_detail);
		
		mapButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps"));
			}
			
		});

		Bundle b = getIntent().getExtras();

		String title = b.getString("title");
		String desc = b.getString("desc");

		tvTitle.setText(title);
		tvDesc.setText(desc);

		Bitmap bitmap = (Bitmap)b.get("bitmap");
		imgView.setImageBitmap(bitmap);
		pbar.setVisibility(View.INVISIBLE);
		//loadImageFromURL(url);

	}

	private void loadImageFromURL(String url)
	{
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.profile)
				.showImageForEmptyUrl(R.drawable.profile).cacheInMemory().cacheOnDisc().build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage(url, imgView, options, new ImageLoadingListener()
		{
			@Override
			public void onLoadingComplete()
			{
				pbar.setVisibility(View.INVISIBLE);

			}

			@Override
			public void onLoadingFailed()
			{

				pbar.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onLoadingStarted()
			{
				pbar.setVisibility(View.VISIBLE);
			}
		});

	}
}
