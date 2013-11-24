package com.photolocator.tab;

import java.util.Date;

import com.photolocator.R;
import com.photolocator.cassandra.CassandraCallback;
import com.photolocator.cassandra.CassandraDataUnit;
import com.photolocator.cassandra.CassandraFunction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class TakePhotoActivity extends TabItemActivity implements OnClickListener{
	
	private Button mTakePhoto;
	private Button mSend;
	private ImageView mImage;
	private Bitmap mImageBitmap=null;
	private CassandraFunction cf;
	
	public static int TAKEPHOTOCODE=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initViews();
		initEvents();
		cf=new CassandraFunction(new CassandraCallback(){

			/* (non-Javadoc)
			 * @see com.photolocator.cassandra.CassandraCallback#dataInserted(boolean)
			 */
			@Override
			public void dataInserted(boolean ret) {
				// TODO Auto-generated method stub
				super.dataInserted(ret);
				if(ret)
					Toast.makeText(TakePhotoActivity.this, "updated", Toast.LENGTH_LONG).show();
				else
					Toast.makeText(TakePhotoActivity.this, "Fail to update", Toast.LENGTH_LONG).show();
			}
			
		});
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
		Bitmap b=(Bitmap) extras.get("data");
		mImageBitmap = Bitmap.createScaledBitmap(b, b.getWidth()/2, b.getHeight()/2, false);
		mImage.setImageBitmap(b);
	}
	
	private void send(){
		
		LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);         

	    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000L,500.0f, new LocationListener(){

			@Override
			public void onLocationChanged(Location arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });
	    
	    Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	    
	    CassandraDataUnit cdu=new CassandraDataUnit();
	    cdu.setLocation(location);
	    cdu.setBitmap(mImageBitmap);
	    cdu.setTime(new Date());
	    cdu.setLocationName("Canada");
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    cdu.setUserName(prefs.getString("username", null));
	    cdu.setCellphoneType(Build.MANUFACTURER+"-"+Build.MODEL);
	    cdu.setText("Comments");
	    cf.insertData(mApplication, cdu);
	}

}
