package com.doruemi.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.doruemi.R;


public class TagView extends FrameLayout {
	
	private State mState = State.NORMAL;
	private TextView tagView;

	public TagView(Context context) {
		this(context, null);
	}

	public TagView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		LayoutInflater.from(context).inflate(R.layout.item_tagview, this);
		tagView = (TextView) findViewById(R.id.tv_tag);
//		tagView.setPadding((int) UIUtils.dip2px(15), (int) UIUtils.dip2px(3),
//				(int) UIUtils.dip2px(15), (int) UIUtils.dip2px(3));
		tagView.setGravity(Gravity.CENTER);
//		tagView.setBackgroundResource(R.drawable.textview_bg);
//		tagView.setTextSize(12);
		tagView.setTextColor(getResources().getColor(R.color.text_gray2));
		tagView.setSingleLine(true);
		tagView.setClickable(true);
		tagView.setOnClickListener(clickListener);
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
//			Intent intent = new Intent(v.getContext(), ThemeLabelActivity.class);
//			Bundle b = new Bundle();
//			b.putInt("id",0);
//			b.putString("labelname",((TextView)v).getText().toString());
//			intent.putExtras(b);
//			v.getContext().startActivity(intent);
		}
	};
	
	public void set(String str) {
		tagView.setText(str);
	}

	public void setclickable(boolean clickable) {
		tagView.setClickable(clickable);
	}

	public void setTextColor(int rId) {
		tagView.setTextColor(getResources().getColor(rId));
	}
	
	public enum State {
		NORMAL, PRESSED, SELECTED
	}
	
	public float sp2px(float sp) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
				getResources().getDisplayMetrics());
	}
	
	public void setState(State mState) {
		this.mState = mState;
	}

	public State getState() {
		return mState;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

}
