package com.wuochoang.kqsx.ui.result.view;

import com.wuochoang.kqsx.base.BaseView;
import com.wuochoang.kqsx.model.LotteryResult;

import java.util.List;

/**
 * Created by admin on 06-Mar-18.
 */

public interface ResultsView extends BaseView {
    void getLotteryResult(List<LotteryResult> lotteryResults);
    void getCalculationResult(int result);
}
