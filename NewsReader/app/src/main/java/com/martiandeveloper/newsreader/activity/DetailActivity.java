package com.martiandeveloper.newsreader.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.martiandeveloper.newsreader.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    // UI Components
    // ConstraintLayout
    @BindView(R.id.detailConstraintLayout)
    ConstraintLayout detailConstraintLayout;
    // WebView
    @BindView(R.id.detailWebView)
    WebView detailWebView;
    // Progressbar
    @BindView(R.id.detailProgressbar)
    ProgressBar detailProgressbar;
    // Toolbar
    @BindView(R.id.detailToolbar)
    Toolbar detailToolbar;

    // String
    @BindString(R.string.error)
    String error;
    @BindString(R.string.went_wrong)
    String went_wrong;
    @BindString(R.string.news_api_org)
    String news_api_org;
    @BindString(R.string.powered_by)
    String powered_by;
    @BindString(R.string.visit)
    String visit;
    @BindString(R.string.no_internet_connection)
    String no_internet_connection;

    // Variables
    private String webUrl, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        initialFunctions();
    }

    private void initialFunctions() {
        declareVariables();
        setToolbar();
        checkInternetConnection();
    }

    private void declareVariables() {
        webUrl = getIntent().getStringExtra("webUrl");
        title = getIntent().getStringExtra("title");

        Log.d("MartianDeveloper", webUrl);
    }

    private void setToolbar() {
        setSupportActionBar(detailToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        DetailActivity.this.setTitle(title);
    }

    @SuppressWarnings({"deprecation", "ConstantConditions"})
    private void checkInternetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            setWebView();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView() {
        detailWebView.getSettings().setJavaScriptEnabled(true);
        detailWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showProgress();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideProgress();
                showSnackbar();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                hideProgress();
                Toast.makeText(DetailActivity.this, error + went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
        detailWebView.loadUrl(webUrl);
    }

    private void showProgress() {
        detailConstraintLayout.setAlpha(0.5f);
        detailProgressbar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        detailConstraintLayout.setAlpha(1.0f);
        detailProgressbar.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSnackbar() {
        Snackbar.make(detailConstraintLayout, powered_by, Snackbar.LENGTH_LONG)
                .setAction(visit, v -> goToTheWebsite()).show();
    }

    private void goToTheWebsite() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news_api_org));
        startActivity(intent);
    }
}
