// Generated code from Butter Knife. Do not modify!
package com.martiandeveloper.newsreader.activity;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.martiandeveloper.newsreader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailActivity_ViewBinding implements Unbinder {
  private DetailActivity target;

  @UiThread
  public DetailActivity_ViewBinding(DetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DetailActivity_ViewBinding(DetailActivity target, View source) {
    this.target = target;

    target.detailConstraintLayout = Utils.findRequiredViewAsType(source, R.id.detailConstraintLayout, "field 'detailConstraintLayout'", ConstraintLayout.class);
    target.detailWebView = Utils.findRequiredViewAsType(source, R.id.detailWebView, "field 'detailWebView'", WebView.class);
    target.detailProgressbar = Utils.findRequiredViewAsType(source, R.id.detailProgressbar, "field 'detailProgressbar'", ProgressBar.class);
    target.detailToolbar = Utils.findRequiredViewAsType(source, R.id.detailToolbar, "field 'detailToolbar'", Toolbar.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.error = res.getString(R.string.error);
    target.went_wrong = res.getString(R.string.went_wrong);
    target.news_api_org = res.getString(R.string.news_api_org);
    target.powered_by = res.getString(R.string.powered_by);
    target.visit = res.getString(R.string.visit);
    target.no_internet_connection = res.getString(R.string.no_internet_connection);
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.detailConstraintLayout = null;
    target.detailWebView = null;
    target.detailProgressbar = null;
    target.detailToolbar = null;
  }
}
