package stormdzh.com.webviewdownloadpic;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/29.
 *
 * @author Administrator.
 */

public class ImgsActivity extends Activity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager mViewPager;
    private TextView tv_index;
    private String imgurl;

    protected ArrayList<String> mImgList;
    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgs);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        tv_index = (TextView) findViewById(R.id.tv_index);
        mImgList = getIntent().getStringArrayListExtra("mImgList");
        index = getIntent().getIntExtra("index", 0);
        mViewPager.setAdapter(new MyAdapter(this));
        mViewPager.setCurrentItem(index);
        mViewPager.addOnPageChangeListener(this);
        findViewById(R.id.iv_download_pic).setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tv_index.setText(position + "/" + mImgList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_download_pic:
                Toast.makeText(this, "请开始你的下载！！！", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    class MyAdapter extends PagerAdapter {

        private Activity mContext;

        public MyAdapter(Activity context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return mImgList == null ? 0 : mImgList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.pager_photo, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_pic);

            imgurl = mImgList.get(position);

            Glide.with(mContext)
                    .load(imgurl)
                    .into(imageView);
//            imageView.setOnLongClickListener(ImgsActivity.this);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
}
