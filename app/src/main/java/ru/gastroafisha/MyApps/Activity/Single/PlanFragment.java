package ru.gastroafisha.MyApps.Activity.Single;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import ru.gastroafisha.MyApps.Model.ImageModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Utils.Classes.DisposableHolder;
import ru.gastroafisha.MyApps.Utils.ImageUtils;
import ru.gastroafisha.MyApps.Utils.TouchImageView;

public class PlanFragment extends Fragment {
    private DisposableHolder mDisposable = new DisposableHolder();

    private Integer orgId;
    private String name;
    private String imageUrl;

    public PlanFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orgId = getArguments().getInt("orgId");
            name = getArguments().getString("name");
            imageUrl = getArguments().getString("imageUrl");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SubsamplingScaleImageView image = (SubsamplingScaleImageView) getView().findViewById(R.id.image);

        ImageModel imageModel = new ImageModel();
        imageModel.setUrl(imageUrl);

        mDisposable.setDisposable(ImageUtils.setTemporaryImage(orgId, imageModel, image));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        mDisposable.dispose();
    }
}
