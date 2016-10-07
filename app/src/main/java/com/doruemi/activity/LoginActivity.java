package com.doruemi.activity;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.doruemi.DosnapApp;
import com.doruemi.R;
import com.doruemi.util.LogUtil;
import com.tencent.mm.sdk.modelmsg.SendAuth;

import butterknife.Bind;
import butterknife.OnClick;

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
    @OnClick(R.id.tv_login)

    public void loginwechatClick(View v){
        if(!DosnapApp.mWeixinAPI.isWXAppInstalled()){
            Toast.makeText(this,"微信未安装",Toast.LENGTH_SHORT).show();
            return;
        }
        LogUtil.e("wechat");
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo"; //// TODO: 2016-10-06 这个是注册后的域吗  微信拉不起来  是不是要签名一致？
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
