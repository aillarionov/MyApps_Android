package ru.gastroafisha.MyApps.Activity.Single;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;

import com.crashlytics.android.Crashlytics;

import java.util.HashMap;
import java.util.Map;

import ru.gastroafisha.MyApps.Activity.CommonActivity;
import ru.gastroafisha.MyApps.R;

public class PlanActivity extends CommonActivity {

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_plan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Map<String, String> images = new HashMap<>();

        Map<String, Object> params = (Map<String, Object>)getIntent().getSerializableExtra("params");
        try {
            String plans = (String)params.get("plans");

            for (String plan : plans.split("\\|")) {
                String[] p = plan.split("=");
                images.put(p[0], p[1]);
            }
        } catch  (Exception e) {
            Crashlytics.logException(e);
        }

        if (images.size() > 0) {
            FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.tab_host);
            tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

            for (Map.Entry<String, String> e : images.entrySet()) {
                FragmentTabHost.TabSpec tabSpec = tabHost.newTabSpec(e.getKey());
                tabSpec.setIndicator(e.getKey());

                Bundle bundle = new Bundle();
                bundle.putInt("orgId", getOrgId());
                bundle.putString("name", e.getKey());
                bundle.putString("imageUrl", e.getValue());

                tabHost.addTab(tabSpec, PlanFragment.class, bundle);
            }
        }
    }



}
