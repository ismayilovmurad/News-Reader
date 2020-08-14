// Generated code from Butter Knife. Do not modify!
package com.martiandeveloper.newsreader.activity;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.martiandeveloper.newsreader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(MainActivity target, View source) {
    this.target = target;

    target.mainConstraintLayout = Utils.findRequiredViewAsType(source, R.id.mainConstraintLayout, "field 'mainConstraintLayout'", ConstraintLayout.class);
    target.mainRecyclerView = Utils.findRequiredViewAsType(source, R.id.mainRecyclerView, "field 'mainRecyclerView'", RecyclerView.class);
    target.mainProgressbar = Utils.findRequiredViewAsType(source, R.id.mainProgressbar, "field 'mainProgressbar'", ProgressBar.class);
    target.mainToolbar = Utils.findRequiredViewAsType(source, R.id.mainToolbar, "field 'mainToolbar'", Toolbar.class);
    target.mainSwipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.mainSwipeRefreshLayout, "field 'mainSwipeRefreshLayout'", SwipeRefreshLayout.class);
    target.mainFrameLayout = Utils.findRequiredViewAsType(source, R.id.mainFrameLayout, "field 'mainFrameLayout'", FrameLayout.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.error = res.getString(R.string.error);
    target.went_wrong = res.getString(R.string.went_wrong);
    target.api_key = res.getString(R.string.api_key);
    target.app_name = res.getString(R.string.app_name);
    target.main_banner = res.getString(R.string.main_banner);
    target.news_api_org = res.getString(R.string.news_api_org);
    target.powered_by = res.getString(R.string.powered_by);
    target.visit = res.getString(R.string.visit);
    target.press_back_again = res.getString(R.string.press_back_again);
    target.no_internet_connection = res.getString(R.string.no_internet_connection);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mainConstraintLayout = null;
    target.mainRecyclerView = null;
    target.mainProgressbar = null;
    target.mainToolbar = null;
    target.mainSwipeRefreshLayout = null;
    target.mainFrameLayout = null;
  }
}
