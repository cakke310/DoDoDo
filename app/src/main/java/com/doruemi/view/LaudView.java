/*
 * @Title:  LaudView.java
 * @Copyright:  Xitek Co., Ltd. Copyright 2015-2015,  All rights reserved
 * @author:  JiangHanQiao
 * @data:  2015��3��19�� ����2:02:55
 * @version:  V1.0
 */
package com.doruemi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.doruemi.DosnapApp;
import com.doruemi.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;

/**
 * ���޵��Զ���view
 * 
 * @author JiangHanQiao
 * @data: 2015��3��19�� ����2:02:55
 * @version: V1.0
 */
public class LaudView extends ImageView implements OnClickListener {

	private TextView countView;
	private int aid = 0;
	private int laud = 0;
	Animation mAnimation;
	private OnClickListener onclickListener;

	public LaudView(Context context) {
		this(context, null);
	}

	public LaudView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LaudView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setlistener(int id, int count, int lauded, OnClickListener clickListener) {
		this.onclickListener = clickListener;
		set(id, count, lauded, null);
	}

	public void setlistener(int id, int count, int lauded, TextView numView, OnClickListener clickListener) {
		this.onclickListener = clickListener;
		set(id, count, lauded, numView);
	}

	public void set(int id, int count, int lauded, TextView numView) {
		aid = id;
		laud = lauded;
		countView = numView;
		if (lauded == 0) {
			setImageResource(R.drawable.icon_shoucang);
		} else if (lauded == 1) {
			setImageResource(R.drawable.icon_yishoucang);
		}
		if (countView != null) {
			countView.setText(count + "");
		}
		mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.scale);
		mAnimation.setAnimationListener(al);
		setOnClickListener(this);
	}

	private AnimationListener al = new AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			if (laud == 1) {
				laud = 0;
				setImageResource(R.drawable.icon_shoucang);
				if (countView != null) {
					countView.setText((Integer.parseInt(countView.getText().toString()) - 1) + "");
				}
			} else if (laud == 0) {
				laud = 1;
				setImageResource(R.drawable.icon_yishoucang);
				if (countView != null) {
					countView.setText((Integer.parseInt(countView.getText().toString()) + 1) + "");
				}
			}
			LaudView.this.setClickable(true);
		}
	};

	@Override
	public void onClick(View v) {
		if (onclickListener != null) {
			onclickListener.onClick(v);
		}
		LaudView.this.setClickable(false);
		String op = "laud";
		if (laud == 1)
			op = "dellaud";
		try {
			request(aid + "", op);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void request(final String aid, String op) {
		String tlisturl = DosnapApp.apiHost + DosnapApp.apiDir + "Issue/" + op;
		OkHttpUtils.post().url(tlisturl)
				.addParams("identifier", DosnapApp.identifier)
				.addParams("token", DosnapApp.token)
				.addParams("id", aid)
				.build()
				.execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception e, int id) {
						Log.e("error", e.getMessage());
					}

					@Override
					public void onResponse(String response, int id) {
						ProcedureTask(response);
					}
				});
	}

	private void ProcedureTask(String jsonStr) {
		try {
			JSONObject resobj = new JSONObject(jsonStr);
			String code = resobj.getString("code");
			if (code.startsWith("S00")) {
				startAnimation(mAnimation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
