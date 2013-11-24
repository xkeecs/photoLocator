package com.photolocator.tab;

import com.photolocator.R;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

public class TakePhotoActivity extends TabItemActivity implements OnClickListener{
	
	private Button mTakePhoto;
	private Button mSend;
	private ImageView mImage;
	private Bitmap mImageBitmap;
	
	public static int TAKEPHOTOCODE=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initViews();
		initEvents();
	}

	
	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_takephoto);
		mTakePhoto=(Button)findViewById(R.id.button_takephoto);
		mSend=(Button)findViewById(R.id.button_send);
		mImage=(ImageView)findViewById(R.id.imageView_takephoto);
		
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		this.mTakePhoto.setOnClickListener(this);
		this.mSend.setOnClickListener(this);
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.button_takephoto:
			takePhoto();
			break;
		case R.id.button_send:
			send();
			break;
		}
	}
	
	public void takePhoto(){
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(takePictureIntent, TAKEPHOTOCODE);
	}


	@Override
	protected void onActivityResult(int arg0, int arg1, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, data);
		switch(arg0){
		case 1:
			if(arg1==RESULT_OK)
				handleData(data);
			break;
		}
	}
	
	private void handleData(Intent data){
		Bundle extras = data.getExtras();
		mImageBitmap = (Bitmap) extras.get("data");
		mImage.setImageBitmap(mImageBitmap);
	}
	
	private void send(){
		
//		LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);         
//
//	    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000L,500.0f, locationListener);
//	    Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//	    double latitude=0;
//	    double longitude=0;
//	    latitude = location.getLatitude();
//	    longitude = location.getLongitude();
	}

}
