package ru.gastroafisha.MyApps.Activity.Form;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.gastroafisha.MyApps.Activity.CommonActivity;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Form.FormItemModel;
import ru.gastroafisha.MyApps.Model.Form.FormModel;
import ru.gastroafisha.MyApps.Proxy.FormDataProxy;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Service.RemoteService;
import ru.gastroafisha.MyApps.Utils.Classes.DisposableHolder;
import ru.gastroafisha.MyApps.Utils.FormUtils;
import ru.gastroafisha.MyApps.Utils.StringUtils;

public class SimpleFormActivity extends CommonActivity {

    private DisposableHolder mFormDisposable = new DisposableHolder();
    private DisposableHolder mListDisposable = new DisposableHolder();

    private Integer formId;

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_simple_form;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Map<String, String> params = (Map<String, String>)getIntent().getSerializableExtra("params");
        formId = Integer.parseInt(params.get("form"));

        Button button = (Button) findViewById(R.id.button_send);
        button.setOnClickListener(v -> sendData());

        mListDisposable.setDisposable(((MainApplication) getApplication()).getLocalDataStorage().getFormItems().list(getOrgId(), formId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_items -> {
                    fillItems(_items);
                    mListDisposable.dispose();
                }));

        getFormDisposable().add(mListDisposable);
    }

    private void fillItems(List<FormItemModel> items) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.form_items);
        layout.removeAllViews();

        for (FormItemModel formItemModel : items) {
            View child = FormUtils.generateElement(this, formItemModel);
            if (child != null) {
                layout.addView(child);
            }
        }
    }

    private void sendData(){
        List<Map<String, String>> data = new ArrayList<>();

        LinearLayout layout = (LinearLayout) findViewById(R.id.form_items);
        Integer childCount = layout.getChildCount();

        for (Integer i = 0; i < childCount; i++) {
            View element = layout.getChildAt(i);

            FormItemModel formItemModel = (FormItemModel)element.getTag();

            String value = FormUtils.getElementValue(element, formItemModel);

            if (value != null && !value.trim().isEmpty()) {
                Map<String, String> item = new HashMap<>();
                item.put("name", formItemModel.getName());
                item.put("value", value.trim());

                data.add(item);
            } else {
                if (formItemModel.getRequired()) {
                    Toast.makeText(this, StringUtils.textFormat(R.string.error_field_is_required, formItemModel.getTitle()), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        Button button = (Button) findViewById(R.id.button_send);
        button.setEnabled(false);

        RemoteService.sendForm(new FormDataProxy(formId, data)).observeOn(AndroidSchedulers.mainThread()).subscribe(() -> {
            Toast.makeText(this, R.string.message_form_sent, Toast.LENGTH_SHORT).show();
            button.setEnabled(true);
        }, throwable -> {
            Crashlytics.logException(throwable);
            button.setEnabled(true);
        });
    }
}
