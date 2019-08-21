package ru.gastroafisha.MyApps.Activity.Single;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.gastroafisha.MyApps.Activity.CommonActivity;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.ImageModel;
import ru.gastroafisha.MyApps.Model.Org.TicketModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Utils.ImageUtils;
import ru.gastroafisha.MyApps.Utils.StringUtils;

public class UrlActivity extends CommonActivity {


    @Override
    protected Integer getLayoutId() {
        return R.layout.content_url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Map<String, Object> params = (Map<String, Object>)getIntent().getSerializableExtra("params");
        try {
            String url = (String)params.get("url");

            WebView webview = (WebView) findViewById(R.id.web_view);
            webview.setWebViewClient(new WebViewClient());
            webview.loadUrl(url);
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }


}
