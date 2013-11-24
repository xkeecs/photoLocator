package com.photolocator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.photolocator.BaseActivity;
import com.photolocator.R;
import com.photolocator.cassandra.CassandraCallback;
import com.photolocator.cassandra.CassandraFunction;
import com.photolocator.tab.MainTabActivity;

public class RegisterActivity extends BaseActivity implements OnClickListener{

	private Button mBtnRegister;
	private Button mBtnCancel;
	private EditText mEditUserName;
	private EditText mEditEMail;
	private EditText mEditPassword;
	private EditText mEditConfirmPasswd;
	private CassandraFunction cf;
	
	
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_register);
		this.mBtnCancel=(Button)findViewById(R.id.button_register_cancel);
		this.mBtnRegister=(Button)findViewById(R.id.button_register_register);
		this.mEditUserName=(EditText)findViewById(R.id.edittext_user_name);
		this.mEditEMail=(EditText)findViewById(R.id.edittext_email);
		this.mEditPassword=(EditText)findViewById(R.id.edittext_password);
		this.mEditConfirmPasswd=(EditText)findViewById(R.id.edittext_confirm_password);
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
		cf=new CassandraFunction(new CassandraCallback(){

			/* (non-Javadoc)
			 * @see com.photolocator.cassandra.CassandraCallback#registered(boolean)
			 */
			@Override
			public void registered(boolean ret) {
				// TODO Auto-generated method stub
				Log.i("RegisterActivity", ret+"");
				if (ret){
					Intent intent = new Intent(RegisterActivity.this, MainTabActivity.class);
					startActivity(intent);
					finish();
					//Toast.makeText(null, "Registered", Toast.LENGTH_SHORT).show();
				}
				else{
					finish();
				}
			}
			
		});
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
			register();
			break;
		}
	}
	
	private void register(){
		String username=this.mEditUserName.getText().toString().trim();
		String email=this.mEditEMail.getText().toString().trim();
		String password=this.mEditPassword.getText().toString().trim();
		String confirmPasswd=this.mEditConfirmPasswd.getText().toString().trim();
		if(!password.equals(confirmPasswd)){
			mEditPassword.requestFocus();
			Toast.makeText(this, "Password and confirm password are different!", Toast.LENGTH_SHORT).show();
			return;
		}
		cf.register(this.getApplicationContext(), username, email, password);
		Toast.makeText(this, "Registering", Toast.LENGTH_SHORT).show();
	}

}
