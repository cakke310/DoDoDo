/*
 * @Title:  CommentView.java
 * @Copyright:  Xitek Co., Ltd. Copyright 2015-2015,  All rights reserved
 * @Description:  TODO<���������ļ�����ʲô��>
 * @author:  JiangHanQiao
 * @data:  2015��3��19�� ����2:02:55
 * @version:  V1.0
 */
package com.doruemi.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.doruemi.DosnapApp;

/**
 * 用户评论
 * 
 * @author JiangHanQiao
 * @data: 2015��3��19�� ����2:02:55
 * @version: V1.0
 */
public class CommentView extends ImageView implements OnClickListener {

	private Context context;
	private int aid = 0;

	public CommentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void set(int mAid, int mCount) {
		aid = mAid;
		setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		setClickable(false);
		showComment();
		setClickable(true);
	}

	private void showComment() {
//		Intent intent = new Intent(context, PhotoActivity.class);
//		Bundle b = new Bundle();
//		b.putInt("photoid", aid);
//		intent.putExtras(b);
//		((Activity) context).startActivityForResult(intent, DosnapApp.ACTIVITY_PHOTO);
	}
}
