package com.doruemi.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.doruemi.DosnapApp;
import com.doruemi.util.LogUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

/**
 * Created by Administrator on 2016-10-06.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DosnapApp.mWeixinAPI.handleIntent(getIntent(),this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtil.e(baseResp.errCode+"");
        switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_OK:
                LogUtil.e("ERR_OK");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                LogUtil.e("ERR_AUTH_DENIED");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                LogUtil.e("ERR_USER_CANCEL");
                break;
        }
    }
}
