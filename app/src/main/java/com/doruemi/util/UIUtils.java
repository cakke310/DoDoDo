package com.doruemi.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.doruemi.DosnapApp;
import com.doruemi.activity.MainActivity;


public class UIUtils {

	public static Context getContext() {
		return DosnapApp.getApplication();
	}

	public static Thread getMainThread() {
		return DosnapApp.getMainThread();
	}

	public static long getMainThreadId() {
		return DosnapApp.getMainThreadId();
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

	/** ��ȡ���̵߳�handler */
	public static Handler getHandler() {
		return DosnapApp.getMainThreadHandler();
	}

	/** ��ʱ�����߳�ִ��runnable */
	public static boolean postDelayed(Runnable runnable, long delayMillis) {
		return getHandler().postDelayed(runnable, delayMillis);
	}

	/** �����߳�ִ��runnable */
	public static boolean post(Runnable runnable) {
		return getHandler().post(runnable);
	}

	/** �����߳�looper�����Ƴ�runnable */
	public static void removeCallbacks(Runnable runnable) {
		getHandler().removeCallbacks(runnable);
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
	//�жϵ�ǰ���߳��ǲ��������߳� 
	public static boolean isRunInMainThread() {
		return android.os.Process.myTid() == getMainThreadId();
	}
    
	public static void runInMainThread(Runnable runnable) {
		if (isRunInMainThread()) {
			runnable.run();
		} else {
			post(runnable);
		}
	}

//	public static void startActivity(Intent intent){
//
//		MainActivity activity = MainActivity.getForegroundActivity();
//		if(activity != null){
//			activity.startActivity(intent);
//		}else{
//			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			getContext().startActivity(intent);
//		}
//	}

	/** ��toast�ļ��׷�װ���̰߳�ȫ�������ڷ�UI�̵߳��á� */
//	public static void showToastSafe(final int resId) {
//		showToastSafe(getString(resId));
//	}

//	/** ��toast�ļ��׷�װ���̰߳�ȫ�������ڷ�UI�̵߳��á� */
//	public static void showToastSafe(final String str) {
//		if (isRunInMainThread()) {
//			showToast(str);
//		} else {
//			post(new Runnable() {
//				@Override
//				public void run() {
//					showToast(str);
//				}
//			});
//		}
//	}

//	private static void showToast(String str) {
//		MainActivity frontActivity = MainActivity.getForegroundActivity();
//		if (frontActivity != null) {
//			Toast.makeText(frontActivity, str, Toast.LENGTH_LONG).show();
//		}
//	}
}


