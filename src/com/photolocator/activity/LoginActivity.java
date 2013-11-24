package com.photolocator.activity;

import java.util.regex.Pattern;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.photolocator.R;
import com.photolocator.cassandra.CassandraCallback;
import com.photolocator.cassandra.CassandraFunction;
import com.photolocator.dialog.SimpleListDialog;
import com.photolocator.dialog.SimpleListDialog.onSimpleListItemClickListener;
import com.photolocator.tab.MainTabActivity;
import com.photolocator.view.HandyTextView;
import com.photolocator.view.HeaderLayout;
import com.photolocator.view.HeaderLayout.HeaderStyle;

//import com.photolocator.view.HeaderLayout;
//import com.photolocator.view.HeaderLayout;

public class LoginActivity extends com.photolocator.BaseActivity implements OnClickListener,
		onSimpleListItemClickListener
{

	private HeaderLayout mHeaderLayout;
	private EditText mEtAccount;
	private EditText mEtPwd;
	private HandyTextView mHtvForgotPassword;
	private HandyTextView mHtvSelectCountryCode;
	private Button mBtnBack;
	private Button mBtnLogin;
	private CassandraFunction cf;

	private String correctPasswd=null;
	private String mAreaCode = "+86";
	private String mAccount;
	private String mPassword;

	private SimpleListDialog mSimpleListDialog;
	private String[] mCountryCodes;
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		initViews();
		initEvents();
		cf=new CassandraFunction(new CassandraCallback(){

			/* (non-Javadoc)
			 * @see com.photolocator.cassandra.CassandraCallback#passwdRetrived(java.lang.String)
			 */
			@Override
			public void passwdRetrived(String passwd) {
				// TODO Auto-generated method stub
				dismissLoadingDialog();
				if (passwd!=null && passwd.equals(mPassword))
				{
					Intent intent = new Intent(LoginActivity.this, MainTabActivity.class);
					startActivity(intent);

					finish();
				}
				else
					Toast.makeText(mApplication, "Username/Password,please check it", Toast.LENGTH_LONG).show();
					//showCustomToast("Username/Password,please check it");
			}
			
		});
	}

	@Override
	protected void initViews()
	{
		mHeaderLayout = (HeaderLayout) findViewById(R.id.login_header);
		mHeaderLayout.init(HeaderStyle.DEFAULT_TITLE);
		mHeaderLayout.setDefaultTitle("Login", null);
		mEtAccount = (EditText) findViewById(R.id.login_et_account);
		mEtPwd = (EditText) findViewById(R.id.login_et_pwd);
		mHtvForgotPassword = (HandyTextView) findViewById(R.id.login_htv_forgotpassword);
		/*
		 * TextUtils.addUnderlineText(this, mHtvForgotPassword, 0, mHtvForgotPassword.getText().length());
		 * mHtvSelectCountryCode = (HandyTextView) findViewById(R.id.login_htv_selectcountrycode);
		 * TextUtils.addUnderlineText(this, mHtvSelectCountryCode, 0, mHtvSelectCountryCode.getText().length());
		 */
		mBtnBack = (Button) findViewById(R.id.login_btn_back);
		mBtnLogin = (Button) findViewById(R.id.login_btn_login);
	}

	@Override
	protected void initEvents()
	{
		// mHtvForgotPassword.setOnClickListener(this);
		// mHtvSelectCountryCode.setOnClickListener(this);
		mBtnBack.setOnClickListener(this);
		mBtnLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.login_btn_back:
			finish();
			break;

		case R.id.login_btn_login:
			login();
			break;
		}
	}

	@Override
	public void onItemClick(int position)
	{

		mAccount = null;
		// String text = TextUtils.getCountryCodeBracketsInfo(mCountryCodes[position], mAreaCode);
		mEtAccount.requestFocus();
		// mEtAccount.setText(text);
		// mEtAccount.setSelection(text.length());

	}

	private void login()
	{
		mAccount=mEtAccount.getText().toString().trim();
		mPassword=mEtPwd.getText().toString().trim();
		if(mAccount==null || mPassword==null){
			return;
		}
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
		editor.putString("username", mAccount);
		editor.commit();
		cf.retrivePassword(mApplication, mAccount);
		showLoadingDialog("Please wait...");
	}
}
