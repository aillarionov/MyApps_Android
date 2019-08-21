package ru.gastroafisha.MyApps.Activity.Single;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.gastroafisha.MyApps.Activity.CommonActivity;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.ImageModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogNewsFavoriteModel;
import ru.gastroafisha.MyApps.Model.Org.TicketModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Utils.ImageUtils;
import ru.gastroafisha.MyApps.Utils.StringUtils;

public class AboutActivity extends CommonActivity {

    private Integer catalogId;

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_catalog_news_show;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Map<String, Object> params = (Map<String, Object>)getIntent().getSerializableExtra("params");
        try {
            catalogId = Integer.parseInt((String)params.get("catalog"));

            getFormDisposable().add(((MainApplication) getApplication()).getLocalDataStorage().getCatalogNews().list(getOrgId(), catalogId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(items -> {
                        if (!items.isEmpty()) {
                            this.fillItem(items.get(0));
                        }
                    }));
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    protected void fillItem(CatalogNewsFavoriteModel item) {
        // Image
        ImageView image = (ImageView) findViewById(R.id.item_photo);
        getFormDisposable().add(ImageUtils.setTemporaryImage(getOrgId(), item.getItem().getPhoto(), image));

        // Text
        TextView text = (TextView) findViewById(R.id.item_text);
        text.setText(StringUtils.textToHtml(item.getItem().getText()));
    }
}
