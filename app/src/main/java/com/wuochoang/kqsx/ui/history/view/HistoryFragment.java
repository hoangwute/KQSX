package com.wuochoang.kqsx.ui.history.view;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.olddog.common.ToastUtils;
import com.wuochoang.kqsx.R;
import com.wuochoang.kqsx.base.BaseFragment;
import com.wuochoang.kqsx.base.BasePresenter;
import com.wuochoang.kqsx.manager.database.RealmHelper;
import com.wuochoang.kqsx.model.History;
import com.wuochoang.kqsx.model.InputInfoEntry;
import com.wuochoang.kqsx.ui.history.adapter.HistoryAdapter;
import com.wuochoang.kqsx.ui.history.dialog.ConfirmDeleteDialog;
import com.wuochoang.kqsx.ui.result.view.ResultFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import io.realm.RealmList;

/**
 * Created by admin on 16-Mar-18.
 */

public class HistoryFragment extends BaseFragment implements ResultFragment.OnSaveResult {
    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;

    private HistoryAdapter historyAdapter;
    private List<History> historyList = new ArrayList<>();

    @Override
    public void injectDependence() {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_history;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        historyList = RealmHelper.findAll(History.class);
        historyAdapter = new HistoryAdapter(historyList, getActivity(), id -> {
            ConfirmDeleteDialog dialog = ConfirmDeleteDialog.getInstance(getContext(), () -> {
                RealmHelper.deleteWhere(History.class, query -> query.equalTo("id", id));
                List<History> updatedList = RealmHelper.findAll(History.class);
                historyAdapter.updateResult(updatedList);
//                this.getActivity().onBackPressed();
            });
            dialog.show(getFragmentManager(), "confirm dialog");
        });
        rvHistory.setAdapter(historyAdapter);
        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        ResultFragment fragment = ResultFragment.getInstance();
        fragment.setOnSaveResultListener(this);
    }

    @Override
    public void onSave() {
        historyList = RealmHelper.findAll(History.class);
        historyAdapter.updateResult(historyList);
    }
}
