package com.martiandeveloper.newsreader.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.snackbar.Snackbar;
import com.martiandeveloper.newsreader.R;
import com.martiandeveloper.newsreader.model.Article;
import com.martiandeveloper.newsreader.model.Main;
import com.martiandeveloper.newsreader.recyclerview.MainAdapter;
import com.martiandeveloper.newsreader.tools.Client;
import com.martiandeveloper.newsreader.tools.Service;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    // UI Components
    // ConstraintLayout
    @BindView(R.id.mainConstraintLayout)
    ConstraintLayout mainConstraintLayout;
    // RecyclerView
    @BindView(R.id.mainRecyclerView)
    RecyclerView mainRecyclerView;
    // Progressbar
    @BindView(R.id.mainProgressbar)
    ProgressBar mainProgressbar;
    // Toolbar
    @BindView(R.id.mainToolbar)
    Toolbar mainToolbar;
    // SwipeRefreshLayout
    @BindView(R.id.mainSwipeRefreshLayout)
    SwipeRefreshLayout mainSwipeRefreshLayout;
    // FrameLayout
    @BindView(R.id.mainFrameLayout)
    FrameLayout mainFrameLayout;

    // String
    @BindString(R.string.error)
    String error;
    @BindString(R.string.went_wrong)
    String went_wrong;
    @BindString(R.string.api_key)
    String api_key;
    @BindString(R.string.app_name)
    String app_name;
    @BindString(R.string.main_banner)
    String main_banner;
    @BindString(R.string.news_api_org)
    String news_api_org;
    @BindString(R.string.powered_by)
    String powered_by;
    @BindString(R.string.visit)
    String visit;
    @BindString(R.string.press_back_again)
    String press_back_again;
    @BindString(R.string.no_internet_connection)
    String no_internet_connection;

    // Variables
    // List
    private List<Article> articleList;
    // LayoutManager
    private RecyclerView.LayoutManager mLayoutManager;
    // Adapter
    private RecyclerView.Adapter mAdapter;
    // Service
    private Service service;
    // Final
    private final String LOG = "MartianDeveloper";
    // Integer
    private int page;
    // String
    private String searchQuery, content, category;
    // Boolean
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialFunctions();
    }

    private void initialFunctions() {
        declareVariables();
        setRecyclerView();
        setListeners();
        setToolbar();
        setSwipeRefreshLayout();
        setAds();
        checkInternetConnection();
    }

    private void declareVariables() {
        // List
        articleList = new ArrayList<>();
        // LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        // Adapter
        mAdapter = new MainAdapter(this, articleList);
        // Client
        Client client = new Client();
        // Service
        service = client.getRetrofit().create(Service.class);
        // Integer
        page = 1;
        // String
        searchQuery = "";
        content = "main";
        category = "";
        // Boolean
        doubleBackToExitPressedOnce = false;
    }

    private void setRecyclerView() {
        mainRecyclerView.setLayoutManager(mLayoutManager);
        mainRecyclerView.setAdapter(mAdapter);
    }

    private void setListeners() {
        mainRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!mainRecyclerView.canScrollVertically(1)) {
                    switch (content) {
                        case "main":
                            getMoreNews();
                            break;
                        case "search":
                            getMoreSearchNews();
                            break;
                        case "category":
                            getMoreCategoryNews(category);
                            break;
                    }
                }
            }
        });

        mainSwipeRefreshLayout.setOnRefreshListener(() -> {
            content = "main";
            getNews();
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (mainSwipeRefreshLayout.isRefreshing()) {
                    mainSwipeRefreshLayout.setRefreshing(false);
                }
            }, 1000);
        });
    }

    private void setToolbar() {
        setSupportActionBar(mainToolbar);
        MainActivity.this.setTitle(app_name);
    }

    private void setSwipeRefreshLayout() {
        mainSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorOne));
        mainSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    private void setAds() {
        MobileAds.initialize(this);
        AdView adView = new AdView(this);
        adView.setAdUnitId(main_banner);
        mainFrameLayout.addView(adView);

        AdRequest bannerAdRequest = new AdRequest.Builder().build();
        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);
        adView.loadAd(bannerAdRequest);
    }

    private AdSize getAdSize() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    @SuppressWarnings({"deprecation", "ConstantConditions"})
    private void checkInternetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            getNews();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_LONG).show();
        }
    }

    private void getNews() {
        showProgress();
        page = 1;

        Call<Main> call = service.getNews("en", "publishedAt", api_key, page);
        call.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(@NonNull Call<Main> call, @NonNull Response<Main> response) {
                hideProgress();
                showSnackbar();

                if (response.body() != null) {
                    if (response.body().getArticles() != null) {
                        try {

                            articleList = response.body().getArticles();

                            mAdapter = new MainAdapter(MainActivity.this, articleList);
                            mainRecyclerView.swapAdapter(mAdapter, true);

                        } catch (Exception e) {
                            if (e.getLocalizedMessage() != null) {
                                Log.d(LOG, e.getLocalizedMessage());
                            }
                            Toast.makeText(MainActivity.this, error + went_wrong, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Main> call, @NonNull Throwable t) {
                hideProgress();

                if (t.getLocalizedMessage() != null) {
                    Log.d(LOG, t.getLocalizedMessage());
                }
                Toast.makeText(MainActivity.this, error + went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMoreNews() {
        showProgress();
        page++;

        Call<Main> call = service.getNews("en", "publishedAt", api_key, page);
        call.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(@NonNull Call<Main> call, @NonNull Response<Main> response) {
                hideProgress();

                if (response.body() != null) {
                    if (response.body().getArticles() != null) {
                        try {

                            articleList.addAll(response.body().getArticles());
                            mAdapter.notifyItemRangeInserted(articleList.size(), articleList.size());

                        } catch (Exception e) {
                            if (e.getLocalizedMessage() != null) {
                                Log.d(LOG, e.getLocalizedMessage());
                            }
                            Toast.makeText(MainActivity.this, error + went_wrong, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Main> call, @NonNull Throwable t) {
                hideProgress();

                if (t.getLocalizedMessage() != null) {
                    Log.d(LOG, t.getLocalizedMessage());
                }
                Toast.makeText(MainActivity.this, error + went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgress() {
        mainConstraintLayout.setAlpha(0.5f);
        mainProgressbar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        mainConstraintLayout.setAlpha(1.0f);
        mainProgressbar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search.getActionView();

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        content = "search";
        searchQuery = query;
        getSearchNews();
        return false;
    }

    private void getSearchNews() {
        showProgress();
        page = 1;

        Call<Main> call = service.getSearchNews(searchQuery, "publishedAt", api_key, page);
        call.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(@NonNull Call<Main> call, @NonNull Response<Main> response) {
                hideProgress();

                if (response.body() != null) {
                    if (response.body().getArticles() != null) {
                        try {

                            articleList = response.body().getArticles();

                            mAdapter = new MainAdapter(MainActivity.this, articleList);
                            mainRecyclerView.swapAdapter(mAdapter, true);

                        } catch (Exception e) {
                            if (e.getLocalizedMessage() != null) {
                                Log.d(LOG, e.getLocalizedMessage());
                            }
                            Toast.makeText(MainActivity.this, error + went_wrong, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Main> call, @NonNull Throwable t) {
                hideProgress();

                if (t.getLocalizedMessage() != null) {
                    Log.d(LOG, t.getLocalizedMessage());
                }
                Toast.makeText(MainActivity.this, error + went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMoreSearchNews() {
        showProgress();
        page++;

        Call<Main> call = service.getSearchNews(searchQuery, "publishedAt", api_key, page);
        call.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(@NonNull Call<Main> call, @NonNull Response<Main> response) {
                hideProgress();

                if (response.body() != null) {
                    if (response.body().getArticles() != null) {
                        try {

                            articleList.addAll(response.body().getArticles());
                            mAdapter.notifyItemRangeInserted(articleList.size(), articleList.size());

                        } catch (Exception e) {
                            if (e.getLocalizedMessage() != null) {
                                Log.d(LOG, e.getLocalizedMessage());
                            }
                            Toast.makeText(MainActivity.this, error + went_wrong, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Main> call, @NonNull Throwable t) {
                hideProgress();

                if (t.getLocalizedMessage() != null) {
                    Log.d(LOG, t.getLocalizedMessage());
                }
                Toast.makeText(MainActivity.this, error + went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_business:
                content = "category";
                category = "business";
                getCategoryNews(category);
                break;
            case R.id.action_entertainment:
                content = "category";
                category = "entertainment";
                getCategoryNews(category);
                break;
            case R.id.action_health:
                content = "category";
                category = "health";
                getCategoryNews(category);
                break;
            case R.id.action_science:
                content = "category";
                category = "science";
                getCategoryNews(category);
                break;
            case R.id.action_sports:
                content = "category";
                category = "sports";
                getCategoryNews(category);
                break;
            case R.id.action_technology:
                content = "category";
                category = "technology";
                getCategoryNews(category);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getCategoryNews(String category) {
        showProgress();
        page = 1;

        Call<Main> call = service.getCategoryNews(category, "publishedAt", api_key, page);
        call.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(@NonNull Call<Main> call, @NonNull Response<Main> response) {
                hideProgress();

                if (response.body() != null) {
                    if (response.body().getArticles() != null) {
                        try {

                            articleList = response.body().getArticles();

                            mAdapter = new MainAdapter(MainActivity.this, articleList);
                            mainRecyclerView.swapAdapter(mAdapter, true);

                        } catch (Exception e) {
                            if (e.getLocalizedMessage() != null) {
                                Log.d(LOG, e.getLocalizedMessage());
                            }
                            Toast.makeText(MainActivity.this, error + went_wrong, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Main> call, @NonNull Throwable t) {
                hideProgress();

                if (t.getLocalizedMessage() != null) {
                    Log.d(LOG, t.getLocalizedMessage());
                }
                Toast.makeText(MainActivity.this, error + went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMoreCategoryNews(String category) {
        showProgress();
        page++;

        Call<Main> call = service.getCategoryNews(category, "publishedAt", api_key, page);
        call.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(@NonNull Call<Main> call, @NonNull Response<Main> response) {
                hideProgress();

                if (response.body() != null) {
                    if (response.body().getArticles() != null) {
                        try {

                            articleList.addAll(response.body().getArticles());
                            mAdapter.notifyItemRangeInserted(articleList.size(), articleList.size());

                        } catch (Exception e) {
                            if (e.getLocalizedMessage() != null) {
                                Log.d(LOG, e.getLocalizedMessage());
                            }
                            Toast.makeText(MainActivity.this, error + went_wrong, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Main> call, @NonNull Throwable t) {
                hideProgress();

                if (t.getLocalizedMessage() != null) {
                    Log.d(LOG, t.getLocalizedMessage());
                }
                Toast.makeText(MainActivity.this, error + went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSnackbar() {
        Snackbar.make(mainConstraintLayout, powered_by, Snackbar.LENGTH_LONG)
                .setAction(visit, v -> goToTheWebsite()).show();
    }

    private void goToTheWebsite() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news_api_org));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        } else {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, press_back_again, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
        }
    }
}
