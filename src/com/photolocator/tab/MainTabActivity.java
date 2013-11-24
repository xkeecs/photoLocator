package com.photolocator.tab;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import com.photolocator.R;
import com.photolocator.photosave.MainActivity;
import com.photolocator.photosave.ResturatantActivity;

@SuppressWarnings("deprecation")
public class MainTabActivity extends TabActivity
{
	private TabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintabs);
		initViews();
		initTabs();
	}

	private void initViews()
	{
		mTabHost = getTabHost();
	}

	private void initTabs()
	{
		LayoutInflater inflater = LayoutInflater.from(MainTabActivity.this);

		View nearbyView = inflater.inflate(R.layout.common_bottombar_tab_nearby, null);

		TabHost.TabSpec nearbyTabSpec = mTabHost.newTabSpec(NearByActivity.class.getName()).setIndicator(nearbyView);
		nearbyTabSpec.setContent(new Intent(MainTabActivity.this, NearByActivity.class));
		mTabHost.addTab(nearbyTabSpec);

		View photoView = inflater.inflate(R.layout.common_bottombar_tab_site, null);
		TabHost.TabSpec photoViewSpec = mTabHost.newTabSpec(MainActivity.class.getName()).setIndicator(photoView);
		photoViewSpec.setContent(new Intent(MainTabActivity.this, MainActivity.class));
		mTabHost.addTab(photoViewSpec);

		/*
		 * View sessionListView = inflater.inflate(R.layout.common_bottombar_tab_chat, null); TabHost.TabSpec
		 * sessionListTabSpec = mTabHost.newTabSpec(SessionListActivity.class.getName()).setIndicator( sessionListView);
		 * sessionListTabSpec.setContent(new Intent(MainTabActivity.this, SessionListActivity.class));
		 * mTabHost.addTab(sessionListTabSpec);
		 */

		View takephotoView = inflater.inflate(R.layout.common_bottombar_tab_takephoto, null);
		TabHost.TabSpec takePhotoSpec = mTabHost.newTabSpec(TakePhotoActivity.class.getName()).setIndicator(
				takephotoView);
		takePhotoSpec.setContent(new Intent(MainTabActivity.this, TakePhotoActivity.class));
		mTabHost.addTab(takePhotoSpec);

		View resturatantView = inflater.inflate(R.layout.common_bottombar_tab_friend, null);
		TabHost.TabSpec resturatantTabSpec = mTabHost.newTabSpec(ResturatantActivity.class.getName()).setIndicator(
				resturatantView);
		resturatantTabSpec.setContent(new Intent(MainTabActivity.this, ResturatantActivity.class));
		mTabHost.addTab(resturatantTabSpec);

		View userSettingView = inflater.inflate(R.layout.common_bottombar_tab_profile, null);
		TabHost.TabSpec userSettingTabSpec = mTabHost.newTabSpec(UserSettingActivity.class.getName()).setIndicator(
				userSettingView);
		userSettingTabSpec.setContent(new Intent(MainTabActivity.this, UserSettingActivity.class));
		mTabHost.addTab(userSettingTabSpec);

	}
}
