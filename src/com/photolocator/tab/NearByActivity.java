package com.photolocator.tab;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow.OnDismissListener;

import com.photolocator.BasePopupWindow.onSubmitClickListener;
import com.photolocator.R;
import com.photolocator.popupwindow.NearByPopupWindow;
import com.photolocator.view.HeaderLayout;
import com.photolocator.view.HeaderLayout.HeaderStyle;
import com.photolocator.view.HeaderLayout.SearchState;
import com.photolocator.view.HeaderLayout.onMiddleImageButtonClickListener;
import com.photolocator.view.HeaderLayout.onSearchListener;
import com.photolocator.view.HeaderSpinner.onSpinnerClickListener;
import com.photolocator.view.SwitcherButton.SwitcherButtonState;
import com.photolocator.view.SwitcherButton.onSwitcherButtonClickListener;

public class NearByActivity extends TabItemActivity
{

	private HeaderLayout mHeaderLayout;
	private HeaderLayout mHeaderSpinner;
	private NearByPeopleFragment mPeopleFragment;

	private NearByPopupWindow mPopupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby);
		initPopupWindow();
		initViews();
		initEvents();
		init();
	}

	@Override
	protected void initViews()
	{
		mHeaderLayout = (HeaderLayout) findViewById(R.id.nearby_header);
		mHeaderLayout.initSearch(new OnSearchClickListener());

		mHeaderLayout.init(HeaderStyle.TITLE_NEARBY_PEOPLE);
	}

	@Override
	protected void initEvents()
	{

	}

	@Override
	protected void init()
	{
		mPeopleFragment = new NearByPeopleFragment(mApplication, this, this);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.nearby_layout_content, mPeopleFragment).commit();

	}

	private void initPopupWindow()
	{
		mPopupWindow = new NearByPopupWindow(this);
		mPopupWindow.setOnSubmitClickListener(new onSubmitClickListener()
		{

			@Override
			public void onClick()
			{
				mPeopleFragment.onManualRefresh();
			}
		});

		mPopupWindow.setOnDismissListener(new OnDismissListener()
		{

			@Override
			public void onDismiss()
			{
				// mHeaderSpinner.initSpinnerState(false);
			}
		});
	}

	public class OnSpinnerClickListener implements onSpinnerClickListener
	{

		@Override
		public void onClick(boolean isSelect)
		{
			/*
			 * if (isSelect) { mPopupWindow.showViewTopCenter(findViewById(R.id.nearby_layout_root)); } else {
			 * mPopupWindow.dismiss(); }
			 */
		}
	}

	public class OnSearchClickListener implements onSearchListener
	{

		@Override
		public void onSearch(EditText et)
		{
			String s = et.getText().toString().trim();
			if (TextUtils.isEmpty(s))
			{
				showCustomToast("Search");
				et.requestFocus();
			}
			else
			{
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
						NearByActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				putAsyncTask(new AsyncTask<Void, Void, Boolean>()
				{

					@Override
					protected void onPreExecute()
					{
						super.onPreExecute();
						mHeaderLayout.changeSearchState(SearchState.SEARCH);
					}

					@Override
					protected Boolean doInBackground(Void... params)
					{
						try
						{
							Thread.sleep(2000);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
						return false;
					}

					@Override
					protected void onPostExecute(Boolean result)
					{
						super.onPostExecute(result);
						mHeaderLayout.changeSearchState(SearchState.INPUT);
						showCustomToast("Couldn't find");
					}
				});
			}
		}
	}

	public class OnMiddleImageButtonClickListener implements onMiddleImageButtonClickListener
	{

		@Override
		public void onClick()
		{
			mHeaderLayout.showSearch();
		}
	}

	public class OnSwitcherButtonClickListener implements onSwitcherButtonClickListener
	{

		@Override
		public void onClick(SwitcherButtonState state)
		{
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.setCustomAnimations(R.anim.fragment_fadein, R.anim.fragment_fadeout);
			switch (state)
			{
			case LEFT:
				mHeaderLayout.init(HeaderStyle.TITLE_NEARBY_PEOPLE);
				ft.replace(R.id.nearby_layout_content, mPeopleFragment).commit();
				break;

			case RIGHT:
				mHeaderLayout.init(HeaderStyle.TITLE_NEARBY_GROUP);
				// ft.replace(R.id.nearby_layout_content, mGroupFragment).commit();
				break;
			}
		}

	}

	@Override
	public void onBackPressed()
	{
		if (mHeaderLayout.searchIsShowing())
		{
			clearAsyncTask();
			mHeaderLayout.dismissSearch();
			mHeaderLayout.clearSearch();
			mHeaderLayout.changeSearchState(SearchState.INPUT);
		}
		else
		{
			finish();
		}
	}
}
