package com.wuochoang.kqsx.ui.insert;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.olddog.common.KeyboardUtils;
import com.olddog.common.ToastUtils;
import com.wuochoang.kqsx.R;
import com.wuochoang.kqsx.base.BaseFragment;
import com.wuochoang.kqsx.base.BasePresenter;
import com.wuochoang.kqsx.common.Constant;
import com.wuochoang.kqsx.manager.database.RealmHelper;
import com.wuochoang.kqsx.model.LotteryResult;
import com.wuochoang.kqsx.utility.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by admin on 20-Mar-18.
 */

public class InsertInformationFragment extends BaseFragment {
    @BindView(R.id.txtDay)
    EditText txtDay;
    @BindView(R.id.txtDB)
    EditText txtDB;
    @BindView(R.id.txtGN1)
    EditText txtGN1;
    @BindView(R.id.txtG21)
    EditText txtG21;
    @BindView(R.id.txtG22)
    EditText txtG22;
    @BindView(R.id.txtG31)
    EditText txtG31;
    @BindView(R.id.txtG32)
    EditText txtG32;
    @BindView(R.id.txtG33)
    EditText txtG33;
    @BindView(R.id.txtG34)
    EditText txtG34;
    @BindView(R.id.txtG35)
    EditText txtG35;
    @BindView(R.id.txtG36)
    EditText txtG36;
    @BindView(R.id.txtG41)
    EditText txtG41;
    @BindView(R.id.txtG42)
    EditText txtG42;
    @BindView(R.id.txtG43)
    EditText txtG43;
    @BindView(R.id.txtG44)
    EditText txtG44;
    @BindView(R.id.txtG51)
    EditText txtG51;
    @BindView(R.id.txtG52)
    EditText txtG52;
    @BindView(R.id.txtG53)
    EditText txtG53;
    @BindView(R.id.txtG54)
    EditText txtG54;
    @BindView(R.id.txtG55)
    EditText txtG55;
    @BindView(R.id.txtG56)
    EditText txtG56;
    @BindView(R.id.txtG61)
    EditText txtG61;
    @BindView(R.id.txtG62)
    EditText txtG62;
    @BindView(R.id.txtG63)
    EditText txtG63;
    @BindView(R.id.txtG71)
    EditText txtG71;
    @BindView(R.id.txtG72)
    EditText txtG72;
    @BindView(R.id.txtG73)
    EditText txtG73;
    @BindView(R.id.txtG74)
    EditText txtG74;

    @BindViews({R.id.txtG21, R.id.txtG22})
    List<EditText> g2List;
    @BindViews({R.id.txtG31, R.id.txtG32, R.id.txtG33, R.id.txtG34, R.id.txtG35, R.id.txtG36})
    List<EditText> g3List;
    @BindViews({R.id.txtG41, R.id.txtG42, R.id.txtG43, R.id.txtG44})
    List<EditText> g4List;
    @BindViews({R.id.txtG51, R.id.txtG52, R.id.txtG53, R.id.txtG54, R.id.txtG55, R.id.txtG56})
    List<EditText> g5List;
    @BindViews({R.id.txtG61, R.id.txtG62, R.id.txtG63})
    List<EditText> g6List;
    @BindViews({R.id.txtG71, R.id.txtG72, R.id.txtG73, R.id.txtG74})
    List<EditText> g7List;

    private Calendar myCalendar = Calendar.getInstance();

    private DatePickerDialog.OnDateSetListener date = (DatePicker view, int year, int monthOfYear, int dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    };

    @Override
    public void injectDependence() {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_insert_info;
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

    }

    @OnClick(R.id.txtDay)
    public void enterInsertDay() {
        new DatePickerDialog(getActivity(), (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateFieldLabel(txtDay, Constant.SENT_DATE_FORMAT);
            KeyboardUtils.hideSoftInput(getActivity());
        }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateFieldLabel(EditText editText, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
    }

    @OnClick(R.id.btnInsertInfo)
    public void insertInfo() {
        LotteryResult lotteryResult = new LotteryResult();
        if(txtDay.getText().toString().isEmpty()) {
            ToastUtils.show("Vui lòng chọn ngày cập nhật");
            return;
        }
        else
            lotteryResult.setDate(DateUtils.formatSentDateToReceivedDate(txtDay.getText().toString()));
        if(txtDB.getText().toString().isEmpty() || txtGN1.getText().toString().isEmpty() || txtG21.getText().toString().isEmpty() || txtG22.getText().toString().isEmpty()
            || txtG31.getText().toString().isEmpty() || txtG32.getText().toString().isEmpty() || txtG33.getText().toString().isEmpty() || txtG34.getText().toString().isEmpty()
                || txtG35.getText().toString().isEmpty() || txtG36.getText().toString().isEmpty() || txtG41.getText().toString().isEmpty() || txtG42.getText().toString().isEmpty()
                || txtG43.getText().toString().isEmpty() || txtG44.getText().toString().isEmpty() || txtG51.getText().toString().isEmpty() || txtG52.getText().toString().isEmpty()
                || txtG53.getText().toString().isEmpty() || txtG54.getText().toString().isEmpty() || txtG55.getText().toString().isEmpty() || txtG56.getText().toString().isEmpty()
                || txtG61.getText().toString().isEmpty() || txtG62.getText().toString().isEmpty() || txtG63.getText().toString().isEmpty() || txtG71.getText().toString().isEmpty()
                || txtG72.getText().toString().isEmpty() || txtG73.getText().toString().isEmpty() || txtG74.getText().toString().isEmpty()) {
            ToastUtils.show("Vui lòng điền đầy đủ thông tin các giải");
            return;
        }
        lotteryResult.setG0(txtDB.getText().toString());
        lotteryResult.setG1(txtGN1.getText().toString());
        lotteryResult.setG2(convertEditTextListToString(g2List));
        lotteryResult.setG3(convertEditTextListToString(g3List));
        lotteryResult.setG4(convertEditTextListToString(g4List));
        lotteryResult.setG5(convertEditTextListToString(g5List));
        lotteryResult.setG6(convertEditTextListToString(g6List));
        lotteryResult.setG7(convertEditTextListToString(g7List));
        RealmHelper.save(lotteryResult);
        ToastUtils.show("Dữ liệu cập nhật thành công!");
    }

    public String convertEditTextListToString(List<EditText> editTextList) {
        String lotteryResult = "";
        for(int i = 0; i < editTextList.size(); i++) {
            lotteryResult += i != editTextList.size() - 1 ? editTextList.get(i).getText().toString() + "-" : editTextList.get(i).getText().toString();
        }
        return lotteryResult;
    }


}
