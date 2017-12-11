package com.sjl.gank.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sjl.gank.R;
import com.sjl.gank.view.MenuPopWindow;
import com.sjl.platform.base.BaseActivity;
import com.sjl.platform.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class WebActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "WebActivity";
    public static final String TITLE = "title";
    public static final String URL = "url";
    private String title;
    private String url;

    public static Intent newIntent(Context context, String title, String link) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(URL, link);
        return intent;
    }

    private void parseIntent() {
        title = getIntent().getStringExtra(TITLE);
        url = getIntent().getStringExtra(URL);
    }

    private ImageView ivBack;
    private ImageView ivSetting;
    private TextView tvTtile;
    private ProgressBar pbProgress;
    private LinearLayout llWebView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        parseIntent();
        initView();
    }

    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        ivSetting = findViewById(R.id.ivSetting);
        tvTtile = findViewById(R.id.tvTitle);
        pbProgress = findViewById(R.id.pbProgress);
        llWebView = findViewById(R.id.llWebView);
        initWebView();

        ivBack.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        tvTtile.setText(title);
        tvTtile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    tvTtile.requestFocus();
                }
            }
        });
        initMenu();
    }

    private void initWebView() {
        webView = new WebView(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(layoutParams);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pbProgress.setProgress(newProgress);
                pbProgress.setVisibility(newProgress == 100 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                tvTtile.setText(title);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (request.getUrl() != null) {
                    view.loadUrl(request.getUrl().toString(), request.getRequestHeaders());
                }
                return true;
            }
        });
        initWebViewSetting(webView);
        webView.loadUrl(url);
        llWebView.addView(webView);
    }

    // 缓存路径
    private final String appCachePath = "/webcache";

    /**
     * 初始化webview设置
     */
    private void initWebViewSetting(WebView webView) {
        //设置缩放
        webView.getSettings().setSupportZoom(false);
        //设置允许访问文件
        webView.getSettings().setAllowFileAccess(true);
        //设置保存表单数据
        webView.getSettings().setSaveFormData(true);
        //设置是否使用viewport
        webView.getSettings().setUseWideViewPort(false);
        //设置WebView标准字体库字体，默认"sans-serif"
        webView.getSettings().setStandardFontFamily("sans-serif");
        //设置WebView固定的字体库字体，默认"monospace"
        webView.getSettings().setFixedFontFamily("monospace");
        //设置WebView是否以http、https方式访问从网络加载图片资源，默认false
        webView.getSettings().setBlockNetworkImage(false);
        //设置WebView是否从网络加载资源，Application需要设置访问网络权限，否则报异常
        webView.getSettings().setBlockNetworkLoads(false);
        //设置WebView是否允许执行JavaScript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //设置WebView运行中的脚本可以是否访问任何原始起点内容，默认true
        webView.getSettings().setAllowUniversalAccessFromFileURLs(false);
        //设置Application缓存API是否开启，默认false
        webView.getSettings().setAppCacheEnabled(true);
        //设置当前Application缓存文件路径
        webView.getSettings().setAppCachePath(appCachePath);
        //设置是否开启数据库存储API权限
        webView.getSettings().setDatabaseEnabled(true);
        //设置是否开启DOM存储API权限
        webView.getSettings().setDomStorageEnabled(true);
        //设置是否开启定位功能，默认true，开启定位
        webView.getSettings().setGeolocationEnabled(true);
        //设置脚本是否允许自动打开弹窗，默认false，不允许
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        //设置WebView加载页面文本内容的编码，默认“UTF-8”。
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        // 设置缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        //设置WebView是否自动加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webView.getSettings().setLoadsImagesAutomatically(false);
        }
        //硬件加速器的使用
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.stopLoading();
            webView.destroy();
            webView = null;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ivBack) {
            finish();
        } else if (id == R.id.ivSetting) {
            menuPopWindow.show();
        }
    }
    MenuPopWindow menuPopWindow;
    private void initMenu() {
        List<String> list = new ArrayList<>();
        list.add("刷新");
        list.add("复制");
        list.add("分享");
        menuPopWindow = new MenuPopWindow(mContext,ivSetting, list, new MenuPopWindow.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(position==0){
                    webView.reload();
                }else if(position==1){
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    clipboardManager.setPrimaryClip(ClipData.newPlainText("text", webView.getUrl()));
                    toast("链接已复制");
                }else if(position==2){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }
}
