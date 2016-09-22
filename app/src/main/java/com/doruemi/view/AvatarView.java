package com.doruemi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.doruemi.DosnapApp;
import com.doruemi.R;
import com.doruemi.configs.ConfigConstants;
import com.doruemi.util.Utility;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * ���޵��Զ���view
 *
 * @author JiangHanQiao
 * @data: 2015��3��19�� ����2:02:55
 * @version: V1.0
 */
public class AvatarView extends FrameLayout implements OnClickListener {

    private SimpleDraweeView avatarView;
    private ImageView darenView;
    private int userid = 0;
    private Context context;

    public AvatarView(Context context) {
        this(context, null);
    }

    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.layout_avatar, this);
        avatarView = (SimpleDraweeView) findViewById(R.id.avatar);
        darenView = (ImageView) findViewById(R.id.daren);
    }

    public void set(int muid, int isdaren, boolean clickable) {
        userid = muid;
        String avatarurl = "";
//		if (DosnapApp.newAvatar.equals("") || userid != DosnapApp.userid)
        avatarurl = DosnapApp.apiHost + "upload/"
                + (int) Math.floor(userid / 1000) + "/" + userid + "/"
                + Utility.md5(userid + "") + "_small.jpg";
//		else {
//			avatarurl = DosnapApp.newAvatar;
//		}
        DraweeController controller = ConfigConstants.getDraweeController(
                ConfigConstants.getImageRequest(avatarView, avatarurl),
                avatarView);
        avatarView.setController(controller);
        if (isdaren == 1)
            darenView.setVisibility(View.VISIBLE);
        else {
            darenView.setVisibility(View.INVISIBLE);
        }
        if (clickable)
            setOnClickListener(this);

    }

    public void set(Context context, int muid, int isdaren, boolean clickable) {
        this.context = context;
        userid = muid;
        String avatarurl = "";
//		if (DosnapApp.newAvatar.equals("") || userid != DosnapApp.userid)
        avatarurl = DosnapApp.apiHost + "upload/"
                + (int) Math.floor(userid / 1000) + "/" + userid + "/"
                + Utility.md5(userid + "") + "_small.jpg";
//		else {
//			avatarurl = DosnapApp.newAvatar;
//		}
        DraweeController controller = ConfigConstants.getDraweeController(
                ConfigConstants.getImageRequest(avatarView, avatarurl),
                avatarView);
        avatarView.setController(controller);
        if (isdaren == 1)
            darenView.setVisibility(View.VISIBLE);
        else {
            darenView.setVisibility(View.INVISIBLE);
        }
        if (clickable)
            setOnClickListener(this);
    }

    public void setphi(int imgid) {
        try {
            GenericDraweeHierarchy hierarchy = avatarView.getHierarchy();
            hierarchy.setPlaceholderImage(imgid);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
//		AvatarView.this.setClickable(false);
//		Intent intent = new Intent(v.getContext(), UserIndexActivity.class);
//		Bundle b = new Bundle();
//		b.putInt("userid", userid);
//		b.putString("username", "");
//		intent.putExtras(b);
//		((Activity)context).startActivityForResult(intent,
//				DosnapApp.ACTIVITY_USER);
//		AvatarView.this.setClickable(true);
    }
}
