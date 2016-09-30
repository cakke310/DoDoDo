package com.doruemi.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.doruemi.R;
import com.doruemi.bean.SearchBean;
import com.doruemi.configs.ConfigConstants;
import com.doruemi.util.LogUtil;
import com.doruemi.util.NetworkUtils;
import com.doruemi.util.UIUtils;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016-08-20.
 */
public class GalleryFragment extends BaseFragment {

    private PullToRefreshListView list;
    private int page = 1;
    private ViewHolder holder;
    private SearchListAdapter searchListAdapter;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        getHttp();

    }



    @Override
    protected void initView() {
        list = (PullToRefreshListView) currentView.findViewById(R.id.list);

    }

    @Override
    public int setContentViewId() {
        return R.layout.fragment_search;
    }

    private void getHttp() {
        // http://app.dosnap.com/api/home/searcher
        String url = NetworkUtils.apiHost+NetworkUtils.apiDir+"home/searcher";
        LogUtil.e("url--"+url);
        OkHttpUtils.get().url(url).build().execute(new Callback<SearchBean>() {
            @Override
            public SearchBean parseNetworkResponse(Response response, int id) throws Exception {
                String string = response.body().string();
                SearchBean searchBean = new Gson().fromJson(string, SearchBean.class);
                return searchBean;
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtil.e("---"+e.toString());
            }

            @Override
            public void onResponse(SearchBean searchBean, int id) {
                LogUtil.e("searchBean---"+ searchBean.getCode());
                for (int i=0; i<searchBean.getList().size(); i++){
                    if(i%3==2){
                        LogUtil.e(i+"");
                    }
                }

//                SearchListAdapter searchListAdapter =  new SearchListAdapter(searchBean.getList(), getActivity().getApplicationContext());
//                list.setAdapter(searchListAdapter);
            }
        });
//        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                LogUtil.e("----------"+e.toString());
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                                SearchListAdapter searchListAdapter =  new SearchListAdapter(searchBean.getList(), getActivity().getApplicationContext());
//                list.setAdapter(searchListAdapter);
//                LogUtil.e("----------"+response.toString());
//            }
//        });
    }

    public class SearchListAdapter extends BaseAdapter{
        private List<SearchBean.ListBean> list;
        private Context context;

        public SearchListAdapter(List<SearchBean.ListBean> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            if(convertView == null){
                holder = new ViewHolder();
                convertView = View.inflate(UIUtils.getContext(), R.layout.layout_three,null);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            SimpleDraweeView photoView1 = (SimpleDraweeView) convertView.findViewById(R.id.photo1);
            SimpleDraweeView photoView2 = (SimpleDraweeView) convertView.findViewById(R.id.photo2);
            SimpleDraweeView photoView3 = (SimpleDraweeView) convertView.findViewById(R.id.photo3);
            for (int i=0; i<list.size(); i++){
                String imgurl = NetworkUtils.apiHost +"crop_240x240/"+ list.get(i).getImgurl()
                        .replaceAll("crop_\\d+x\\d+/", "");
                LogUtil.e("imgUrl==="+imgurl);
                switch (i){
                    case 0:
                        settings(photoView1, list.toString(), imgurl, photoListener, View.VISIBLE);
                        break;
                    case 1:
                        settings(photoView2, list.toString(), imgurl, photoListener, View.VISIBLE);
                        break;
                    default:
                        settings(photoView3, list.toString(), imgurl, photoListener, View.VISIBLE);
                        break;
                }
            }


            return convertView;
        }
    }

    class ViewHolder{
        private SimpleDraweeView photo1;
        private SimpleDraweeView photo2;
        private SimpleDraweeView photo3;
    }

    private void settings(SimpleDraweeView photoView, String tag,
                          String imgurl, View.OnClickListener photoListener, int visible) {
        photoView.setTag(tag);
        DraweeController controller = ConfigConstants.getDraweeController(
                ConfigConstants.getImageRequest(photoView, imgurl), photoView);
        photoView.setController(controller);
        photoView.setOnClickListener(photoListener);
        photoView.setVisibility(View.VISIBLE);;
    }

    private View.OnClickListener photoListener = new View.OnClickListener() {
        public void onClick(View v) {
            try {
//                Intent intent = new Intent(v.getContext(),
//                        PhotoActivity.class);
//                Bundle b = new Bundle();
//                b.putString("json", v.getTag().toString());
//                intent.putExtras(b);
//                ((Activity) v.getContext()).startActivityForResult(intent,
//                        DosnapApp.ACTIVITY_PHOTO);
            } catch (Exception e) {
            }
        }
    };


}
