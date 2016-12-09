package com.doruemi.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.doruemi.DosnapApp;
import com.doruemi.R;
import com.doruemi.protocol.LoginProtocol;
import com.doruemi.util.Hutils;
import com.doruemi.util.LogUtil;
import com.doruemi.util.SPUtils;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2016-09-28.
 */
public class LoginActivity extends BaseActivity{
    @Bind(R.id.imgBtn_Loginshow)
    ImageButton imgBtn_Loginshow;
    @Bind(R.id.imgBtn_Loginclean)
    ImageButton imgBtn_Loginclean;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.username)
    EditText username;
    private String user;

    @OnClick(R.id.tv_login) void click(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        Hutils.showProgressDialog(this,"登录中");
        user = username.getText()+"";
        LoginProtocol.loginWithPhonenum(phoneLoginCallback,username.getText()+"",password.getText()+"");
    }

    private StringCallback phoneLoginCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            SPUtils.saveString(LoginActivity.this, "phone", user);
            userSubmit(response);
            LogUtil.e("phoneLoginCallback="+response);
        }
    };

    private void userSubmit(String jsonStr) {
        try {
            JSONObject resobj = new JSONObject(jsonStr);
            String code = resobj.getString("code");
            if (code.startsWith("S")) {
                DosnapApp.token = resobj.getString("token");
                DosnapApp.identifier = resobj.getString("identifier");
                DosnapApp.timeout = resobj.getInt("timeout");
                JSONObject response = resobj.getJSONObject("userinfo");
                DosnapApp.userid = response.getInt("userid");
                DosnapApp.username = response.getString("username");
                try {
                    DosnapApp.usertext = response.getString("usertext")==null?"":response.getString("usertext");
                    DosnapApp.gender = response.getInt("gender");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Hutils.cancelProgressDialog();
                SharedPreferences sp = getApplication().getSharedPreferences("user_info", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("token",DosnapApp.token);
                edit.putString("identifier",DosnapApp.identifier);
                edit.putInt("userid",DosnapApp.userid);
                edit.putString("username",DosnapApp.username);
                this.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginwechatClick(View v){
        if(!DosnapApp.mWeixinAPI.isWXAppInstalled()){
            Toast.makeText(this,"微信未安装",Toast.LENGTH_SHORT).show();
            return;
        }
        LogUtil.e("loginwechatClick");
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "dosnap_wechat_login";
        DosnapApp.mWeixinAPI.sendReq(req);
    }



    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                username.addTextChangedListener(userTextWatcher);
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                password.addTextChangedListener(userTextWatcher);
            }
        });
    }

    @Override
    public int setContentViewId() {
        return R.layout.layout_viewpliper_login;
    }

    private TextWatcher userTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(charSequence.length()>0){
                imgBtn_Loginclean.setVisibility(View.VISIBLE);
                imgBtn_Loginshow.setVisibility(View.VISIBLE);
            }else {
                imgBtn_Loginclean.setVisibility(View.INVISIBLE);
                imgBtn_Loginshow.setVisibility(View.INVISIBLE);
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };


    /** 检查密码 */
    public boolean checkPassword(String password) {
        if (password.equals("")) {
            Toast.makeText(LoginActivity.this, R.string.inputpass, Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.trim().length() < 6) {
            Toast.makeText(LoginActivity.this, R.string.passtooshort, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /** 检查邮箱 */
    private boolean checkMail(String theuser) {
        if (!theuser.matches("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$")) {
            Toast.makeText(LoginActivity.this, R.string.emailerror, Toast.LENGTH_SHORT).show();
            return false;
        } else if (theuser.indexOf('@') < 0) {
            Toast.makeText(LoginActivity.this, R.string.emailerror, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /** 检查电话号码 */
    private boolean checkPhone(String theuser) {
        if (!theuser.matches("\\d*") || theuser.length() != 11) {
            Toast.makeText(LoginActivity.this, R.string.phoneerror, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
