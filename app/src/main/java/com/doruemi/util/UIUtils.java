package com.doruemi.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

import com.doruemi.application.BaseApplication;


public class UIUtils {

	public static Context getContext() {
		return BaseApplication.getContext();
	}



	/** dipת��px */
	public static int dip2px(float dip) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	/** pxzת��dip */
	public static int px2dip(float px) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}
	
	/**
     * 将px值转换为sp值，保证文字大小不变
     * 
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int px2sp(float pxValue) { 
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity; 
        return (int) (pxValue / fontScale + 0.5f); 
    } 
   
    /**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int sp2px(float spValue) { 
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    }  





	public static View inflate(int resId){
		return LayoutInflater.from(getContext()).inflate(resId,null);
	}

	/** ��ȡ��Դ */
	public static Resources getResources() {
		return getContext().getResources();
	}

	/** ��ȡ���� */
	public static String getString(int resId) {
		return getResources().getString(resId);
	}

	/** ��ȡ�������� */
	public static String[] getStringArray(int resId) {
		return getResources().getStringArray(resId);
	}

	/** ��ȡdimen */
	public static int getDimens(int resId) {
		return getResources().getDimensionPixelSize(resId);
	}

	/** ��ȡdrawable */
	public static Drawable getDrawable(int resId) {
		return getResources().getDrawable(resId);
	}

	/** ��ȡ��ɫ */
	public static int getColor(int resId) {
		return getResources().getColor(resId);
	}

	/** ��ȡ��ɫѡ���� */
	public static ColorStateList getColorStateList(int resId) {
		return getResources().getColorStateList(resId);
	}


}


