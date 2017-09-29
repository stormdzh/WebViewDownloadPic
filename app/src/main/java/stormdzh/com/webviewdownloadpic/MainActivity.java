package stormdzh.com.webviewdownloadpic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {

    private WebView webView;
    protected ArrayList<String> mImgList;
    private String imgurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.mWebView);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setUseWideViewPort(false);
        webView.getSettings().setLoadWithOverviewMode(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                addImageListner(webView);
            }
        });
        webView.addJavascriptInterface(new ImgJavascriptInterface(this), "imagelistener");
        webView.loadUrl("http://gywb.cn/content/2017-09/29/content_5608958.htm");
        registerForContextMenu(webView);
    }


    protected void addImageListner(WebView webView) {
        if (webView == null) return;
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\");" +
                "var imgs=\"\";" +
                "for(var i=0;i<objs.length;i++){" +
                "imgs=imgs+objs[i].src+\";\";" +
                "objs[i].onclick=function(){" +
                "window.imagelistener.clickImage(this.src);" +
                "}" +
                "}" +
                "window.imagelistener.getImageUrl(imgs);" +
                "})()");
//        "window.imagelistener.getImageUrl(objs[i].src);" +
    }

    protected class ImgJavascriptInterface {
        private Context context;

        public ImgJavascriptInterface(Context context) {
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void getImageUrl(String imgs) {     //把所有图片的url保存在ArrayList<String>中
            if (mImgList == null)
                mImgList = new ArrayList<>();
            mImgList.clear();
            if (TextUtils.isEmpty(imgs) || !imgs.contains(";"))
                return;
            String[] split = imgs.split(";");
            for (String img : split) {
                if (TextUtils.isEmpty(img)) continue;
                mImgList.add(img);
            }
        }

        @android.webkit.JavascriptInterface  //对于targetSdkVersion>=17的，要加这个声明
        public void clickImage(String clickimg) {
            int index = 0;
            for (String url : mImgList)
                if (url.equals(clickimg))
                    index = mImgList.indexOf(clickimg);//获取点击图片在整个页面图片中的位置
            Toast.makeText(MainActivity.this, "可以浏览图片了！！！", Toast.LENGTH_SHORT).show();

            MainActivity.this.startActivity(
                    new Intent(MainActivity.this, ImgsActivity.class).putExtra("index", index).putStringArrayListExtra("mImgList", mImgList));
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (!(v instanceof WebView)) return;
        WebView.HitTestResult result = ((WebView) v).getHitTestResult();
        if (result == null) return;
        int type = result.getType();
        if (type == WebView.HitTestResult.IMAGE_TYPE || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
            imgurl = result.getExtra();
            menu.setHeaderTitle("下载提示");
            menu.add(0, v.getId(), 0, "保存图片到手机").setOnMenuItemClickListener(this);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(this, "请开始你的下载！！！", Toast.LENGTH_SHORT).show();
        return true;
    }
}
