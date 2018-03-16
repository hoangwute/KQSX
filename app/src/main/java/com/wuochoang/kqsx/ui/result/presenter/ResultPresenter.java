package com.wuochoang.kqsx.ui.result.presenter;

import android.util.Log;

import com.olddog.common.ToastUtils;
import com.wuochoang.kqsx.base.BasePresenter;
import com.wuochoang.kqsx.common.Constant;
import com.wuochoang.kqsx.model.ApiResult;
import com.wuochoang.kqsx.model.InputInfoEntry;
import com.wuochoang.kqsx.network.ApiService;
import com.wuochoang.kqsx.ui.result.view.ResultsView;
import com.wuochoang.kqsx.utility.DateUtils;
import com.wuochoang.kqsx.utility.NumberUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.wuochoang.kqsx.utility.NumberUtils.convertCodeToNumber;

/**
 * Created by admin on 06-Mar-18.
 */

public class ResultPresenter extends BasePresenter<ResultsView> {

    public ResultPresenter(ApiService apiService) {
        super(apiService);
    }

    public void getListLottoResult(int provinceId, String range) {
        if (getView() == null) return;
        showLoading();
        apiService.searchLotteryResult(provinceId, range)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ApiResult apiResult) {
                        if(apiResult != null) {
                            getView().getLotteryResult(apiResult.getLotteryResults());
                            ToastUtils.show("Lấy dữ liệu thành công!");
                            hideLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(e.getMessage());
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });

    }

    public void getCalculationResult(List<InputInfoEntry> entryList, int runTimes, String comparedDate, String comparedCode) {
        Observable<Integer> resultObservable = Observable.create(e -> {
            int counter = 0;
            for(int i = 0; i < runTimes; i++) {
                Log.d("Result counter", String.valueOf(counter));
                int totalDigitSum = 0;
                boolean modified = false;
                for(InputInfoEntry entry : entryList) {
                    String fullNumber = convertCodeToNumber(DateUtils.getNextDate(entry.getDate(), i), entry.getCode());
                    if(!fullNumber.isEmpty()) {
                        Log.d("Result", fullNumber);
                        int position = Integer.parseInt(NumberUtils.getDigitFromFullNumber(entry.getCode(), fullNumber));
                        String digit = String.valueOf(fullNumber.charAt(position));
                        totalDigitSum += Integer.parseInt(digit);
                        modified = true;
                    }
                }
                if(modified == false) { //all entries has invalid date (Tet holiday, etc)
                    continue;
                }
                String totalDigitSumString = String.valueOf(totalDigitSum);
                String fullNumber = convertCodeToNumber(DateUtils.getNextDate(comparedDate, i), comparedCode);
                if(!fullNumber.isEmpty()) {
                    int position = Integer.parseInt(NumberUtils.getDigitFromFullNumber(comparedCode, fullNumber));
                    String comparedDigit = String.valueOf(fullNumber.charAt(position));
                    Log.d("Result", totalDigitSumString.substring(totalDigitSumString.length() - 1) + " and " + comparedDigit);
                    if (Integer.parseInt(totalDigitSumString.substring(totalDigitSumString.length() - 1)) == Integer.parseInt(comparedDigit)) {
                        counter++;
                    }
                }
            }
            e.onNext(counter);
            e.onComplete();
        });
        showLoading();
        resultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        getView().getCalculationResult(integer);
                        hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });

    }
}
