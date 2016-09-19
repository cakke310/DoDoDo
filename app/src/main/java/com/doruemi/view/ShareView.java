///*
// * @Title:  ShareView.java
// * @Copyright:  Xitek Co., Ltd. Copyright 2015-2015,  All rights reserved
// * @Description:  ���ڷ�����Զ���view
// * @author:  JiangHanQiao
// * @data:  2015��3��19�� ����2:02:55
// * @version:  V1.0
// */
//package com.doruemi.view;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.doruemi.DosnapApp;
//import com.doruemi.R;
//import com.doruemi.activity.MainActivity;
//import com.doruemi.protocol.PhotoProtocol;
//import com.zhy.http.okhttp.callback.StringCallback;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//
//import okhttp3.Call;
//
//public class ShareView extends ImageView implements OnClickListener {
//
//	private Context mContext;
//	private String shareFormat;
//	private int aid;
//	private String username;
//	private String activityinfo;
//	private String imgurl;
//	private String weburl;
//	private int userid;
//	private DosnapPopupWindow menuWindow = null;
//	private ShareLayout view;
//
//	public ShareView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		setOnClickListener(this);
//		this.mContext = context;
//		shareFormat = this.getContext().getResources().getString(R.string.sharemsg2);
//	}
//
//	public void set(int mAid, int mUserid, String mUsername, String mPhotoPath,
//			String mActivityinfo, String m_weburl) {
//		aid = mAid;
//		username = mUsername;
//		imgurl = mPhotoPath;
//		userid = mUserid;
//		activityinfo = mActivityinfo;
//		weburl = m_weburl;
//	}
//
//	@Override
//	public void onClick(View v) {
//		if (userid == 0) {
//			menuWindow = new DosnapPopupWindow(this.getContext(),popupClickListener,R.layout.popup_photo_share,R.string.shareactivity);
////			menuWindow.mMenuView.findViewById(R.id.report).setVisibility(View.GONE);
//			menuWindow.mMenuView.findViewById(R.id.ll_info).setVisibility(View.GONE);
//		} else {
//			menuWindow = new DosnapPopupWindow(this.getContext(),popupClickListener,R.layout.popup_photo_share,0);
//			if (userid == DosnapApp.userid) {
//				menuWindow.mMenuView.findViewById(R.id.report).setVisibility(View.GONE);
//				menuWindow.mMenuView.findViewById(R.id.blacklist).setVisibility(View.GONE);
//				menuWindow.mMenuView.findViewById(R.id.del).setVisibility(View.VISIBLE);
//			}
//		}
//		menuWindow.showAtLocation((View) v.getParent(), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
//	}
//
//	private OnClickListener popupClickListener = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			if (v.getId() == R.id.weiboshare) {
//				menuWindow.dismiss();
//				ImageView tmpView = new ImageView(ShareView.this.getContext());
//				DosnapApp.aq.id(tmpView).image(imgurl,true,true,120,R.drawable.loadno,new BitmapAjaxCallback(){
//					@Override
//					public void callback(String url, ImageView iv, Bitmap bm, com.androidquery.callback.AjaxStatus status){
//						iv.setImageResource(R.drawable.icon_home_more);
//						share2Sina(bm);
//					}
//				});
//			} else if (v.getId() == R.id.wechatshare) {
//				menuWindow.dismiss();
//				if (userid == 0) {
//					ImageView tmpView = new ImageView(ShareView.this.getContext());
//					DosnapApp.aq.id(tmpView).image(imgurl,true,true,120,R.drawable.loadno,new BitmapAjaxCallback(){
//						@Override
//						public void callback(String url, ImageView iv, Bitmap bm, com.androidquery.callback.AjaxStatus status){
//							iv.setImageResource(R.drawable.icon_home_more);
//							share2wechat(SendMessageToWX.Req.WXSceneSession,bm);
//						}
//					});
//				} else {
////					sharewechat(SendMessageToWX.Req.WXSceneSession, sharePhoto());
//					sharePhoto(SendMessageToWX.Req.WXSceneSession);
//				}
////				share2wechat(SendMessageToWX.Req.WXSceneSession,sharePhoto());
//			} else if (v.getId() == R.id.friendshare) {
//				menuWindow.dismiss();
//				if (userid == 0) {
//					ImageView tmpView = new ImageView(ShareView.this.getContext());
//					DosnapApp.aq.id(tmpView).image(imgurl,true,true,120,R.drawable.loadno,new BitmapAjaxCallback(){
//						@Override
//						public void callback(String url, ImageView iv, Bitmap bm, com.androidquery.callback.AjaxStatus status){
//							iv.setImageResource(R.drawable.icon_home_more);
//							share2wechat(SendMessageToWX.Req.WXSceneTimeline,bm);
//						}
//					});
//				} else {
////					sharewechat(SendMessageToWX.Req.WXSceneTimeline, sharePhoto());
//					sharePhoto(SendMessageToWX.Req.WXSceneTimeline);
//				}
//			} else if (v.getId() == R.id.report) {
//				menuWindow.dismiss();
//				try {
//					PhotoProtocol.reportPhoto(reportCallback, aid);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			} else if (v.getId() == R.id.del) {
//				menuWindow.dismiss();
//				try {
//					PhotoProtocol.delPhoto(delCallback, aid);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			} else if (v.getId() == R.id.blacklist) {
//				menuWindow.dismiss();
//				UserInfoProtocol.addtoBlacklist(new StringCallback() {
//					@Override
//					public void onError(Call call, Exception e, int id) {
//						e.printStackTrace();
//					}
//
//					@Override
//					public void onResponse(String response, int id) {
//						try {
//							JSONObject obj = new JSONObject(response);
//							String code = obj.getString("code");
//							if (code.equals("S001")) {
//								ToastUtils.showSafeToast(obj.getString("ErrorMsg"));
//							} else if (code.startsWith("E")) {
//								ToastUtils.showSafeToast(obj.getString("ErrorMsg"));
//							} else {
//								ToastUtils.showSafeToast(R.string.operate_failed);
//							}
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//					}
//				}, userid);
//			}
//		}
//	};
//
//	private void sharePhoto(int type) {
//		view = new ShareLayout(mContext);
//		view.setData(aid, userid, username, imgurl, type);
//		/*//启用绘图缓存
//		view.setDrawingCacheEnabled(true);
//		//调用下面这个方法非常重要，如果没有调用这个方法，得到的itmap为null
//		DisplayMetrics dm =getResources().getDisplayMetrics();
//		int w_screen = dm.widthPixels;
//		int h_screen = dm.heightPixels;
//		Rect frame = new Rect();//创建一个空的矩形对象
//		((Activity) mContext).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);//获得顶层窗口的装饰视图，即状态栏，然后把状态栏显示的框架填充给刚刚我们创建的矩形对象，再通过矩形对象来获取状态栏高度
//		int statusBarHeight = frame.top;// 获取状态栏高度：frame.top
//		view.measure(MeasureSpec.makeMeasureSpec(w_screen, MeasureSpec.EXACTLY),
//				MeasureSpec.makeMeasureSpec(h_screen - statusBarHeight, MeasureSpec.EXACTLY));
//		//这个方法也非常重要，设置布局的尺寸和位置
//		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//		//获得绘图缓存中的Bitmap
//		view.buildDrawingCache();*/
////		return view.getDrawingCache();
//	}
//
//	private StringCallback delCallback = new StringCallback() {
//		@Override
//		public void onError(Call call, Exception e, int id) {
//			Log.e("error", e.getMessage());
//		}
//
//		@Override
//		public void onResponse(String response, int id) {
//			try {
//				JSONObject resobj = new JSONObject(response);
//				String code = resobj.getString("code");
//				if (code.startsWith("S00")) {
//					reFresh();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	};
//
//	private StringCallback reportCallback = new StringCallback() {
//		@Override
//		public void onError(Call call, Exception e, int id) {
//			e.printStackTrace();
//		}
//
//		@Override
//		public void onResponse(String response, int id) {
//			try {
//				JSONObject resobj = new JSONObject(response);
//				String code = resobj.getString("code");
//				if (code.startsWith("S00")) {
//					Toast.makeText(ShareView.this.getContext(), R.string.reportsuc, Toast.LENGTH_LONG).show();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	};
//
//	private void share2Sina(Bitmap shareBitmap) {
//		if (DosnapApp.mAccessToken == null || !DosnapApp.mAccessToken.isSessionValid()) {
//			Intent intent = new Intent(this.getContext(), WeiboAuthActivity.class);
//			this.getContext().startActivity(intent);
//		}
//		WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
//		TextObject textObject = new TextObject();
//		if (userid == 0) {
//			textObject.text = activityinfo;
//		} else {
//			textObject.text = String.format(shareFormat, username, aid+"");
//		}
//		weiboMessage.textObject = textObject;
//		if(shareBitmap!=null) {
//			ImageObject imageObject = new ImageObject();
//			imageObject.setImageObject(shareBitmap);
//			weiboMessage.imageObject = imageObject;
//		}
//		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
//		request.transaction = String.valueOf(System.currentTimeMillis());
//		request.multiMessage = weiboMessage;
//		AuthInfo authInfo = new AuthInfo(this.getContext(), Constants.APP_KEY,
//				Constants.REDIRECT_URL, Constants.SCOPE);
//		IWeiboShareAPI mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(
//				this.getContext(), Constants.APP_KEY);
//		mWeiboShareAPI.registerApp();
//		mWeiboShareAPI.sendRequest((Activity) getContext(), request, authInfo,
//				DosnapApp.mAccessToken.getToken(), new WeiboAuthListener() {
//
//					@Override
//					public void onWeiboException(WeiboException arg0) {
//					}
//
//					@Override
//					public void onComplete(Bundle bundle) {
//					}
//
//					@Override
//					public void onCancel() {
//					}
//				});
//	}
//
//	// wechat
//	private void sharewechat(int flag,Bitmap bitmap) {
//		if (!DosnapApp.mWeixinAPI.isWXAppInstalled()) {
//			Toast.makeText(this.getContext(), R.string.nowechat, Toast.LENGTH_LONG).show();
//			return;
//		}
//		WXImageObject imgObj = new WXImageObject(bitmap);
//		WXMediaMessage msg = new WXMediaMessage();
//		msg.mediaObject = imgObj;
//		// 设置缩略图
//		int THUMB_SIZE = 120;
//		int inSampleSize = (int) Math.min(((float) bitmap.getWidth() / THUMB_SIZE),
//				((float) bitmap.getHeight() / THUMB_SIZE));
//		Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/inSampleSize,
//				bitmap.getHeight()/inSampleSize, true);
//		bitmap.recycle();
//
//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
//		thumbBmp.compress(Bitmap.CompressFormat.JPEG, 85, stream);
//
//		msg.thumbData = stream.toByteArray();
//		SendMessageToWX.Req req = new SendMessageToWX.Req();
//		req.transaction = "img" + String.valueOf(System.currentTimeMillis());
//		req.message = msg;
//		req.scene = flag;
//		DosnapApp.mWeixinAPI.sendReq(req);
//	}
//
//	// wechat
//	private void share2wechat(int flag,Bitmap shareBitmap) {
//		if (!DosnapApp.mWeixinAPI.isWXAppInstalled()) {
//			Toast.makeText(this.getContext(), R.string.nowechat, Toast.LENGTH_LONG).show();
//			return;
//		}
//
//		WXWebpageObject webpage = new WXWebpageObject();
//		webpage.webpageUrl = weburl ;
//		Log.e("Wechat", webpage.webpageUrl);
//		WXMediaMessage msg = new WXMediaMessage(webpage);
//		if (flag == SendMessageToWX.Req.WXSceneSession) {
//			if (userid == 0) {
//				msg.title = username;
//				msg.description = activityinfo;
//			} else {
//				msg.title = getResources().getString(R.string.app_name);
//				msg.description = String.format(shareFormat, username, aid+"");
//			}
//		} else if (flag == SendMessageToWX.Req.WXSceneTimeline) {
//			if (userid == 0) {
//				msg.title = username;
//			} else {
//				msg.title = String.format(shareFormat, username, aid+"");
//			}
//		}
//		if(shareBitmap!=null) {
//			msg.setThumbImage(shareBitmap);
//		}
//		SendMessageToWX.Req req = new SendMessageToWX.Req();
//		req.transaction = String.valueOf(System.currentTimeMillis());
//		req.message = msg;
//		req.scene = flag;
//		DosnapApp.mWeixinAPI.sendReq(req);
//	}
//
//	private void reFresh() {
//		String ac = this.getContext().getClass().getSimpleName();
//		switch (ac) {
//			case "PhotoActivity":
//				((PhotoActivity) this.getContext()).del();
//				break;
//			case "MainActivity":
//				((MainActivity) this.getContext()).reFresh();
//				break;
//			case "UserIndexActivity":
//				((UserIndexActivity) this.getContext()).refresh();
//				break;
//		}
//	}
//
//}
