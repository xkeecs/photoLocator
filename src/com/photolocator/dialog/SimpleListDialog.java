package com.photolocator.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.photolocator.R;

public class SimpleListDialog extends Dialog implements OnItemClickListener
{
	public SimpleListDialog(Context context)
	{
		super(context, R.style.Theme_Light_FullScreenDialogAct);
		setContentView(R.layout.common_dialog_generic);
	}

	private onSimpleListItemClickListener mOnSimpleListItemClickListener;

	public interface onSimpleListItemClickListener
	{
		public void onItemClick(int position);
	}

	public void setOnSimpleListItemClickListener(onSimpleListItemClickListener listener)
	{
		mOnSimpleListItemClickListener = listener;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		if (mOnSimpleListItemClickListener != null)
		{
			mOnSimpleListItemClickListener.onItemClick(arg2);
			dismiss();
		}
	}
}
