package com.photolocator.photosave;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.photolocator.R;

public class SettingActivity extends PreferenceActivity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.layout.setting);

	}
}