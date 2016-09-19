/*
 * 常用静态方法
 */

package com.doruemi.util;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.doruemi.DosnapApp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	public static void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
		ListAdapter listAdapter = gridView.getAdapter(); 
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        if(items==0)
        	return;
        int rows = 0;

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if( items > columns ){
            x = items/columns;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
	}

	public static String getDataColumn(Context context, Uri uri,
			String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri
				.getAuthority());
	}

	/**
	 * <br>
	 * 功能简述:4.4及以上获取图片的方法 <br>
	 * 功能详细描述: <br>
	 * 注意:
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/"
							+ split[1];
				}
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection,
						selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	public static String md5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(
					string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static Bitmap decodeFile(String mFilePath, int pixs) {
		if (pixs == 0)
			pixs = 1960 * 1960;
		Bitmap bitmap = null;
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mFilePath, opt);
		opt.inSampleSize = Utility.computeSampleSize(opt, -1, pixs);
		opt.inJustDecodeBounds = false;
		try {
			bitmap = BitmapFactory.decodeFile(mFilePath, opt);

		} catch (OutOfMemoryError err) {
			err.printStackTrace();
		}
		return bitmap;
	}
	
	public static SpannableStringBuilder text2Span(String text) {
		SpannableStringBuilder res = new SpannableStringBuilder(text);
		Pattern p = Pattern.compile("(@|#|~)([^ :：\t\r\n]*)");   
		Matcher m = p.matcher(text);
		int cur = 0;
		while(m.find()){
		    MatchResult mr=m.toMatchResult();   
		    String v2=mr.group(1);   
		    String v3=mr.group(2);
		    if(v2.equals("#")) {
//		    	res.setSpan(new LabelSpan(v3,DosnapApp.atcolor),mr.start(),mr.end(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		    } else if(v2.equals("@")) {
//		    	res.setSpan(new AtSpan(v3, DosnapApp.atcolor),mr.start(),mr.end(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		    }
		}
		return res;
	}

	public static SpannableStringBuilder comment2Span(String text) {
		SpannableStringBuilder res = new SpannableStringBuilder(text);
		Pattern p = Pattern.compile("(@|#|~)([^ :：\t\r\n]*)");
		Matcher m = p.matcher(text);
		int cur = 0;
		while(m.find()){
			MatchResult mr=m.toMatchResult();
			String v2=mr.group(1);
			String v3=mr.group(2);
			if(v2.equals("#")) {
//		    	res.setSpan(new LabelSpan(v3,DosnapApp.atcolor),mr.start(),mr.end(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			} else if(v2.equals("@")) {
//				res.setSpan(new AtSpan(v3, DosnapApp.atcolor),mr.start(),mr.end(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			} else if(v2.equals("~")) {
//				res.setSpan(new AtSpan(v3,DosnapApp.atcolor),mr.start()+1-cur,mr.end()-cur,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				res.delete(mr.start()-cur, mr.start()+1-cur);
				cur++;
			}
		}
		return res;
	}
	
	public static List<String> text2TagList(String text) {
		List<String> tags = new ArrayList<String>();
		Pattern p = Pattern.compile("(#)([^# :：\t\r\n]*)");   
		Matcher m = p.matcher(text);
		while(m.find()){
		    MatchResult mr=m.toMatchResult();
		    String v3=mr.group(2);
		    tags.add(v3);
		}
		return tags;
	}
	
	public static HashMap<String, List<String>> text2TagMap(String str) {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> tagsReplace = new ArrayList<String>();
		List<String> tags = new ArrayList<String>();
		List<String> tagsText = new ArrayList<String>();
		Pattern p = Pattern.compile("([\n]*#)([^# :：\t\r\n]*)([ :：\t\r\n]*)");   
		Matcher m = p.matcher(str);
		while(m.find()){
		    MatchResult mr = m.toMatchResult();
		    String complete = mr.group(0);
		    String v3=mr.group(2);
		    tags.add(complete);
		    if (!(v3 + mr.group(3).trim()).equals("")) {				
		    	tagsText.add(v3.trim());
			}
		}
		String tag= null;
		for (int i = 0; i < tags.size(); i++) {
			tag = tags.get(i);
			if (str.startsWith(tag)) {
				tagsReplace.add(tags.get(i));
				str = str.substring(str.indexOf(tag)+tag.length(), str.length());
			} else {
				break;
			}
		}
		if (!str.equals("")) {			
			for (int i = tags.size()-1; i > -1; i--) {
				tag = tags.get(i);
				if (str.endsWith(tag)) {
					tagsReplace.add(tags.get(i));
					str = str.substring(0, str.lastIndexOf(tag));
				} else {
					break;
				}
			}
		}
		map.put("replace", tagsReplace);
//		map.put("all", tags);
		map.put("text", tagsText);
		return map;
	}
	
	public static String getTime() {
		return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA)
				.format(new Date());
	}

	public static int Dp2Px(Context context, float dp) {  
	    final float scale = context.getResources().getDisplayMetrics().density;  
	    return (int) (dp * scale + 0.5f);  
	}  
	  
	public static int Px2Dp(Context context, float px) {  
	    final float scale = context.getResources().getDisplayMetrics().density;  
	    return (int) (px / scale + 0.5f);  
	}  
	
	public static int getIds(JSONArray obj)
	{
		int out=0;
		try {
			for(int i=0;i<obj.length();i++)
			{
				out+=obj.getJSONObject(i).getInt("id");
			}
		} catch (Exception e) {
		}
		return out;
	}
	
	public static JSONArray remove(final int idx, final JSONArray from) {
	    final List<JSONObject> objs = asList(from);
	    objs.remove(idx);
	 
	    final JSONArray ja = new JSONArray();
	    for (final JSONObject obj : objs) {
	        ja.put(obj);
	    }
	 
	    return ja;
	}
	
	public static JSONArray insert(final int idx, final JSONArray from,final JSONObject jobj) {
	    final List<JSONObject> objs = asList(from);
	    objs.add(idx, jobj);;
	    final JSONArray ja = new JSONArray();
	    for (final JSONObject obj : objs) {
	        ja.put(obj);
	    }
	    return ja;
	}
	 
	public static List<JSONObject> asList(final JSONArray ja) {
	    final int len = ja.length();
	    final ArrayList<JSONObject> result = new ArrayList<JSONObject>(len);
	    for (int i = 0; i < len; i++) {
	        final JSONObject obj = ja.optJSONObject(i);
	        if (obj != null) {
	            result.add(obj);
	        }
	    }
	    return result;
	}
}
