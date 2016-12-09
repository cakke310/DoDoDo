package com.doruemi.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by c_xuwei-010 on 2016/12/8.
 */
public class Hutils {

    private static ProgressDialog progressDialog;

    public static void showProgressDialog(Context context, String text){
        cancelProgressDialog();
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        if(text != null && text.toString().trim().length()>0){
            progressDialog.setMessage(text);
        }
        progressDialog.show();
    }

    public static void cancelProgressDialog(){
        if(progressDialog!=null && progressDialog.isShowing()){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
