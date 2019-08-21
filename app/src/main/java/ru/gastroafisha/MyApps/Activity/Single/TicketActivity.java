package ru.gastroafisha.MyApps.Activity.Single;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.crashlytics.android.Crashlytics;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.gastroafisha.MyApps.Activity.CommonActivity;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.ImageModel;
import ru.gastroafisha.MyApps.Model.Org.TicketModel;
import ru.gastroafisha.MyApps.Model.Org.TicketType;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Storage.Image.LocalImageStore;
import ru.gastroafisha.MyApps.Utils.ImageUtils;
import ru.gastroafisha.MyApps.Utils.StringUtils;

public class TicketActivity extends CommonActivity {


    @Override
    protected Integer getLayoutId() {
        return R.layout.content_ticket;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFormDisposable().add(((MainApplication) getApplication()).getLocalDataStorage().getTickets().get(getOrgId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ticket -> {
                    switch (ticket.getType()) {
                        case Url:
                            this.fillTicket(ticket);
                            //this.showUrl(ticket);
                            break;

                        case Simple:
                            this.fillTicket(ticket);
                            break;
                    }
                }));
    }

    /*
    private void showUrl(TicketModel ticket ) {
        LinearLayout block = (LinearLayout) findViewById(R.id.load_block);
        SubsamplingScaleImageView image = (SubsamplingScaleImageView) findViewById(R.id.ticket_image);
        WebView webview = (WebView) findViewById(R.id.web_view);

        block.setVisibility(View.INVISIBLE);
        image.setVisibility(View.INVISIBLE);
        webview.setVisibility(View.VISIBLE);

        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(ticket.getSource());
    }
    */

    private void fillTicket(TicketModel ticket) {
        WebView webview = (WebView) findViewById(R.id.web_view);
        webview.setVisibility(View.INVISIBLE);

        if (((MainApplication) getApplication()).getLocalImageStore().isTemporaryFileExists(getOrgId(), ticket.getSource())) {
            this.setImage(ticket.getSource());
        } else {
            TextView text = (TextView) findViewById(R.id.load_text);
            if (ticket.getText() != null) {
                text.setText(StringUtils.textToHtml(ticket.getText()));
                text.setVisibility(View.VISIBLE);
            } else {
                text.setVisibility(View.INVISIBLE);
            }

            Button button = (Button) findViewById(R.id.load_button);
            if (ticket.getButton() != null) {
                button.setOnClickListener(v -> setImage(ticket.getSource()));
                button.setText(ticket.getButton());
                button.setVisibility(View.VISIBLE);
            } else {
                button.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void setImage(String url) {
        LinearLayout block = (LinearLayout) findViewById(R.id.load_block);
        SubsamplingScaleImageView image = (SubsamplingScaleImageView) findViewById(R.id.ticket_image);

        ImageModel img = new ImageModel();
        img.setUrl(url);

        getFormDisposable().add(ImageUtils.setTemporaryImage(getOrgId(), img, image));

        block.setVisibility(View.INVISIBLE);
        image.setVisibility(View.VISIBLE);
    }

}
