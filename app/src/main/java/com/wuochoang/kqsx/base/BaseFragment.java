package com.wuochoang.kqsx.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuochoang.kqsx.App;
import com.wuochoang.kqsx.MainActivity;
import com.wuochoang.kqsx.common.utils.Utils;
import com.wuochoang.kqsx.di.component.ActivityComponent;
import com.wuochoang.kqsx.di.module.ActivityModule;
import com.olddog.common.KeyboardUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.wuochoang.kqsx.R;

/**
 * Created by quyenlx on 8/8/2017.
 */

public abstract class BaseFragment extends Fragment implements BaseView {
    public abstract void injectDependence();

    public abstract int getLayoutRes();

    public int getTitle() {
        // TODO: 8/9/2017 Override Here
        return R.string.app_name;
    }

    public abstract BasePresenter getPresenter(); //Nếu sử dụng Presenter thì phải return (BasePresenter)Presenter

    public void initBundle(Bundle bundle) {
        // TODO: 8/9/2017  Override Here
    }

    public abstract void initView();

    public abstract void initData();


    protected BaseActivity mActivity;
    protected View rootView;
    protected Unbinder unbinder;
    protected ActivityComponent component;

    private BasePresenter presenter;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof BaseActivity)) {
            new Throwable("Lỗi k overide BaseActivity");
        }
        mActivity = (BaseActivity) getActivity();
        component = App.get().getComponent().plus(new ActivityModule(mActivity));
        injectDependence();
        initBundle(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutRes(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = getPresenter();
        if (presenter != null) presenter.onAttachView(this);
        initView();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (presenter != null) presenter.onDetach();
    }

    // Chỉ sử dụng trong main fragment
    @Override
    public void addFragment(BaseFragment fragment) {
        if (mActivity instanceof MainActivity) {
            FragmentManager frm = ((MainActivity) mActivity).mainFragment.getCurrentFragmentMgr();
            frm.beginTransaction()
                    .add(R.id.tab_container, fragment)
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
            ((MainActivity) mActivity).changeFitWindown();    //adding fragment to backstack purpose
        }
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = Utils.showLoadingDialog(getContext());
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void hideKeyboard() {
        if (mActivity != null) {
            KeyboardUtils.hideSoftInput(mActivity);
        }
    }

    public boolean isShowFitWindows() {
        return true;
    }

    @Override
    public void onRequestFailure(String message) {
//        OmiAlertDialog dialog = OmiAlertDialog.newInstanceOne(getContext(), "Error", message, "Ok");
//        dialog.show(getFragmentManager(), null);
    }

    protected void setTitle(@StringRes int title) {
//        if (mActivity instanceof MainActivity) {
//            ((MainActivity) mActivity).mainFragment.setTitle(title);
//        }
    }

    @Override
    public void onNetworkError() {
//        // Show dialog thong báo
//        OmiAlertDialog dialog = OmiAlertDialog.newInstanceOne(getContext(), "Error", "Lỗi mạng", "Ok");
//        dialog.show(getFragmentManager(), null);
    }
}
