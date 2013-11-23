package com.photolocator.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.photolocator.BaseActivity;
import com.photolocator.R;

public class RegisterActivity extends BaseActivity implements OnClickListener{

	private Button mBtnRegister;
	private Button mBtnCancel;
	
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_register);
		this.mBtnCancel=(Button)findViewById(R.id.button_register_cancel);
		this.mBtnRegister=(Button)findViewById(R.id.button_register_register);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		this.mBtnCancel.setOnClickListener(this);
		this.mBtnRegister.setOnClickListener(this);
	}

	/* (non-Javadoc)
	 * @see com.photolocator.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initViews();
		initEvents();
	}

	/* (non-Javadoc)
	 * @see com.photolocator.BaseActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.button_register_cancel:
			Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
			break;
		case R.id.button_register_register:
			Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show();
		}
	}

}
