package ru.gastroafisha.MyApps.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import ru.gastroafisha.MyApps.Activity.CommonActivity;

/**
 * Created by alex on 22.12.2017.
 */

public abstract class CommonAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private CompositeDisposable mDisposable;
    private CommonActivity activity;
    private List<T> items;

    protected abstract VH getNewViewHolder(View v);
    protected abstract void fillViewHolder(VH holder, T item);
    protected abstract Integer getLayoutId();

    public CommonAdapter(CommonActivity activity, List<T> items, CompositeDisposable disposable) {
        this.activity = activity;
        this.mDisposable = disposable;
        this.items = items;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return getNewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        fillViewHolder(holder, items.get(position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public CompositeDisposable getFormDisposable() {
        return mDisposable;
    }



    public void showProgressDialog(String title, String message, Action onCancel) {
        if (getActivity() != null) {
            getActivity().showProgressDialog(title, message, onCancel);
        }
    }

    public void hideProgressDialog() {
        if (getActivity() != null) {
            getActivity().hideProgressDialog();
        }
    }


    public Context getContext() {
        return activity;
    }

    public CommonActivity getActivity() {
        return activity;
    }
}