package com.wuochoang.kqsx.ui.result.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.olddog.common.KeyboardUtils;
import com.olddog.common.ToastUtils;
import com.wuochoang.kqsx.App;
import com.wuochoang.kqsx.R;
import com.wuochoang.kqsx.base.BaseFragment;
import com.wuochoang.kqsx.base.BasePresenter;
import com.wuochoang.kqsx.common.Constant;
import com.wuochoang.kqsx.manager.database.RealmHelper;
import com.wuochoang.kqsx.model.History;
import com.wuochoang.kqsx.model.InputInfoEntry;
import com.wuochoang.kqsx.model.LotteryResult;
import com.wuochoang.kqsx.network.ApiService;
import com.wuochoang.kqsx.ui.result.adapter.InputInfoEntryAdapter;
import com.wuochoang.kqsx.ui.result.presenter.ResultPresenter;
import com.wuochoang.kqsx.utility.DateUtils;
import com.wuochoang.kqsx.utility.NumberUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.RealmList;

import static com.wuochoang.kqsx.manager.database.RealmHelper.exportDatabase;

/**
 * Created by admin on 05-Mar-18.
 */

public class ResultFragment extends BaseFragment implements ResultsView {

    @Inject
    ApiService apiService;

    @BindView(R.id.txtInfoFrom)
    EditText txtInfoFrom;
    @BindView(R.id.txtInfoTo)
    EditText txtInfoTo;
    @BindView(R.id.txtInfoStatus)
    TextView txtInfoStatus;

    @BindView(R.id.txtComparedCode)
    EditText txtComparedCode;
    @BindView(R.id.txtComparedDate)
    EditText txtComparedDate;
    @BindView(R.id.txtComparedNumber)
    TextView txtComparedNumber;
    @BindView(R.id.txtComparedDigit)
    TextView txtComparedDigit;
    @BindView(R.id.imgArrow)
    ImageView imgArrow;

    @BindView(R.id.txtRunTimes)
    EditText txtRunTimes;
    @BindView(R.id.txtResults)
    TextView txtResults;
    @BindView(R.id.txtResultsInPercentage)
    TextView txtResultsInPercentage;
    @BindView(R.id.txtMaxRunnableTime)
    TextView txtMaxRunnableTime;
    @BindView(R.id.btnSaveResult)
    Button btnSaveResult;

    @BindView(R.id.rvInfoEntry)
    RecyclerView rvInfoEntry;

    private ResultPresenter presenter;
    private InputInfoEntryAdapter inputInfoEntryAdapter;
    private List<InputInfoEntry> entryList = new ArrayList<>();
    private List<InputInfoEntry> savedEntryList = new ArrayList<>();
    private int result;

    private Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date = (DatePicker view, int year, int monthOfYear, int dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    };

    public interface OnSaveResult {
        void onSave();
    }

    public static OnSaveResult onSaveResultListener;

    public void setOnSaveResultListener(OnSaveResult onSaveResultListener) {
        this.onSaveResultListener = onSaveResultListener;
    }

    private void updateFieldLabel(EditText editText, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void injectDependence() {
        component.inject(this);
        presenter = new ResultPresenter(apiService);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_result;
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    public static ResultFragment getInstance() {
        ResultFragment fragment = new ResultFragment();
        return fragment;
    }

    @OnClick(R.id.btnTest)
    public void test() {
        txtResultsInPercentage.setText("");
        txtResults.setText("");
        btnSaveResult.setVisibility(View.GONE);
        if(txtComparedDate.getText().toString().isEmpty()) {
            ToastUtils.show("Vui lòng nhập ngày so sánh");
            return;
        }
        if(txtComparedCode.getText().toString().isEmpty()) {
            ToastUtils.show("Vui lòng nhập vị trí chạy");
            return;
        }
        if(txtRunTimes.getText().toString().isEmpty()) {
            ToastUtils.show("Vui lòng nhập số lần chạy");
            return;
        }
        if(Integer.parseInt(txtRunTimes.getText().toString()) > Integer.parseInt(txtMaxRunnableTime.getText().toString())) {
            ToastUtils.show("Số lần chạy vượt mức tối đa. Vui lòng giảm số lần chạy");
            return;
        }
        if(Integer.parseInt(txtMaxRunnableTime.getText().toString()) == 0) {
            ToastUtils.show("Số lần chạy phải lớn hơn 0");
            return;
        }
        List<LotteryResult> lotteryResultList = RealmHelper.findAll(LotteryResult.class,"date");
        String endRunningDateStringCompare = DateUtils.getNextDate(txtComparedDate.getText().toString(), Integer.parseInt(txtRunTimes.getText().toString()));
        String lastSavedDateString = DateUtils.formatReceivedDateToSentDate(lotteryResultList.get(0).getDate());
        String firstSavedDateString = DateUtils.formatReceivedDateToSentDate(lotteryResultList.get(lotteryResultList.size()-1).getDate());
        if(DateUtils.isExceedThreshold(lastSavedDateString, endRunningDateStringCompare)) {
            txtResults.setText("Số lần chạy vượt quá ngày lấy dữ liệu. Vui lòng giảm số lần chạy hoặc chọn ngày so sánh thấp hơn ngày " + txtComparedDate.getText().toString());
            return;
        }
        List<InputInfoEntry> entryListUnfiltered = inputInfoEntryAdapter.getEntryList();
        List<InputInfoEntry> entryListFiltered = new ArrayList<>();
        for(InputInfoEntry entry : entryListUnfiltered) {
            if(entry.getDate() != null && !entry.getDate().isEmpty()) {
                entryListFiltered.add(entry);
            }
        }
        if(entryListFiltered.isEmpty()) {
            txtResults.setText("Vui lòng nhập ít nhất 1 vị trí ngày");
            return;
        }
        for(InputInfoEntry entry : entryListFiltered) {
            Log.d("DateEntry", entry.getDate());
            String endRunningDateString = DateUtils.getNextDate(entry.getDate(), Integer.parseInt(txtRunTimes.getText().toString()));
            if(entry.getCode() == null || entry.getCode().isEmpty()) {
                txtResults.setText("Vui lòng nhập vị trí cho ngày " + entry.getDate());
                return;
            }
            if(DateUtils.isExceedThreshold(lastSavedDateString, endRunningDateString)) {
                txtResults.setText("Số lần chạy vượt quá ngày lấy dữ liệu. Vui lòng giảm số lần chạy hoặc chọn ngày thấp hơn ngày " + entry.getDate());
                return;
            }
            if(DateUtils.isEarlierDate(firstSavedDateString, entry.getDate())) {
                txtResults.setText("Chưa lấy được dữ liệu cho ngày " + entry.getDate());
                return;
            }
        }
        savedEntryList = entryListFiltered;
        presenter.getCalculationResult(entryListFiltered, Integer.parseInt(txtRunTimes.getText().toString()),
                txtComparedDate.getText().toString(), txtComparedCode.getText().toString());

    }

    @OnClick(R.id.btnGetLotteryInfo)
    public void getLotteryInfo() {
        if(txtInfoFrom.getText().toString().isEmpty() || txtInfoTo.getText().toString().isEmpty()) {
            ToastUtils.show("Vui lòng nhập dữ liệu ngày");
        }
        else
            presenter.getListLottoResult(Constant.PROVINCE_HANOI, String.format("%s - %s", txtInfoFrom.getText().toString(), txtInfoTo.getText().toString()));
    }

    @OnClick(R.id.txtInfoFrom)
    public void enterInfoFrom() {
        new DatePickerDialog(getActivity(), (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateFieldLabel(txtInfoFrom, Constant.SENT_DATE_FORMAT);
            KeyboardUtils.hideSoftInput(getActivity());
        }, myCalendar

                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.txtInfoTo)
    public void enterInfoTo() {
        new DatePickerDialog(getActivity(), (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateFieldLabel(txtInfoTo, Constant.SENT_DATE_FORMAT);
            KeyboardUtils.hideSoftInput(getActivity());
        }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.txtComparedDate)
    public void enterComparedDate() {
        new DatePickerDialog(getActivity(), (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateFieldLabel(txtComparedDate, Constant.SENT_DATE_FORMAT);
            KeyboardUtils.hideSoftInput(getActivity());
        }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.btnSaveResult)
    public void saveResult() {
        RealmList<InputInfoEntry> savedRealmList = new RealmList<>();
        for(int i = 0; i < savedEntryList.size(); i++) {
            Log.d("Saved", savedEntryList.get(i).getDate() + savedEntryList.get(i).getCode());
            savedRealmList.add(savedEntryList.get(i));
        }
        for(int i = 0; i < savedRealmList.size(); i++) {
            Log.d("Saved realm", savedRealmList.get(i).getDate() + savedRealmList.get(i).getCode());
        }
        InputInfoEntry comparedEntry = new InputInfoEntry(txtComparedCode.getText().toString(), txtComparedDate.getText().toString());
        History savedResult = new History(savedRealmList, result, Integer.parseInt(txtRunTimes.getText().toString()), comparedEntry);
        RealmHelper.save(savedResult);
        onSaveResultListener.onSave();
    }

    @Override
    public void initView() {
        Collections.addAll(entryList, new InputInfoEntry(1), new InputInfoEntry(2), new InputInfoEntry(3), new InputInfoEntry(4), new InputInfoEntry(5));
        inputInfoEntryAdapter = new InputInfoEntryAdapter(getActivity(), entryList);
        rvInfoEntry.setAdapter(inputInfoEntryAdapter);
        rvInfoEntry.setLayoutManager(new LinearLayoutManager(getContext()));
        addTextChangedListener();
        updateInfoStatus();
    }

    private void addTextChangedListener() {
        txtComparedCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!txtComparedDate.getText().toString().isEmpty()) {
                    txtComparedNumber.setText(NumberUtils.convertCodeToNumber(txtComparedDate.getText().toString(), editable.toString()));
                    if(NumberUtils.getDigitFromFullNumber(editable.toString(), txtComparedNumber.getText().toString()) != null) {
                        int position = Integer.parseInt(NumberUtils.getDigitFromFullNumber(editable.toString(), txtComparedNumber.getText().toString()));
                        String digit = String.valueOf(txtComparedNumber.getText().toString().charAt(position));
                        txtComparedDigit.setText(digit);
                        if(!txtComparedNumber.getText().toString().isEmpty()) {
                            imgArrow.setVisibility(View.VISIBLE);
                        }
                        else
                            imgArrow.setVisibility(View.GONE);
                    }
                    else if (editable.toString().startsWith("T") && editable.toString().length() == 2 && !txtComparedNumber.getText().toString().isEmpty()) { //case T1 + T2
                        String digit = txtComparedNumber.getText().toString().substring(txtComparedNumber.getText().toString().length() - 1);
                        txtComparedDigit.setText(digit);
                        if(!txtComparedNumber.getText().toString().isEmpty()) {
                            imgArrow.setVisibility(View.VISIBLE);
                        }
                        else
                            imgArrow.setVisibility(View.GONE);
                    }
                    else {
                        imgArrow.setVisibility(View.GONE);
                        txtComparedDigit.setText("");
                    }
                }
            }
        });

        txtComparedDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!txtComparedCode.getText().toString().isEmpty()) {
                    txtComparedNumber.setText(NumberUtils.convertCodeToNumber(editable.toString(), txtComparedCode.getText().toString()));
                    if(NumberUtils.getDigitFromFullNumber(txtComparedCode.getText().toString(), txtComparedNumber.getText().toString()) != null) {
                        int position = Integer.parseInt(NumberUtils.getDigitFromFullNumber(txtComparedCode.getText().toString(), txtComparedNumber.getText().toString()));
                        String digit = String.valueOf(txtComparedNumber.getText().toString().charAt(position));
                        txtComparedDigit.setText(digit);
                        if(!txtComparedNumber.getText().toString().isEmpty()) {
                            imgArrow.setVisibility(View.VISIBLE);
                        }
                        else
                            imgArrow.setVisibility(View.GONE);
                    }
                    else if (txtComparedCode.getText().toString().startsWith("T") && txtComparedCode.getText().toString().length() == 2 && !txtComparedNumber.getText().toString().isEmpty()) { //case T1 + T2
                        String digit = txtComparedNumber.getText().toString().substring(txtComparedNumber.getText().toString().length() - 1);
                        txtComparedDigit.setText(digit);
                        if(!txtComparedNumber.getText().toString().isEmpty()) {
                            imgArrow.setVisibility(View.VISIBLE);
                        }
                        else
                            imgArrow.setVisibility(View.GONE);
                    }
                    else {
                        imgArrow.setVisibility(View.GONE);
                        txtComparedDigit.setText("");
                    }
                }
                List<LotteryResult> lotteryResultList = RealmHelper.findAll(LotteryResult.class, "date");
                txtMaxRunnableTime.setText(String.valueOf(NumberUtils.getMaximumRunnableTimes(editable.toString(),
                        DateUtils.formatReceivedDateToSentDate(lotteryResultList.get(0).getDate()))));
                txtRunTimes.setText(String.valueOf(NumberUtils.getMaximumRunnableTimes(editable.toString(),
                        DateUtils.formatReceivedDateToSentDate(lotteryResultList.get(0).getDate()))));
                txtComparedCode.requestFocus();
            }
        });
    }

    @Override
    public void initData() {
        updateInfoStatus();
    }

    @Override
    public void getLotteryResult(List<LotteryResult> lotteryResults) {
        RealmHelper.saveAll(lotteryResults);
        updateInfoStatus();
    }

    @Override
    public void getCalculationResult(int result) {
        this.result = result;
        txtResults.setText("Số lần trùng: " + result);
        txtResultsInPercentage.setText( "Phần trăm: " + result + "/" + txtRunTimes.getText().toString() + " = " + (result * 100 / Integer.parseInt(txtRunTimes.getText().toString())) + "%");
        btnSaveResult.setVisibility(View.VISIBLE);
    }

    public void updateInfoStatus() {
        if(RealmHelper.findFirst(LotteryResult.class) == null) {
            txtInfoStatus.setText("Chưa lấy dữ liệu");
            txtInfoStatus.setTextColor(getResources().getColor(R.color.red));
        }
        else {
            List<LotteryResult> lotteryResultList = RealmHelper.findAll(LotteryResult.class, "date");
            txtInfoStatus.setText(String.format("Đã lấy từ %s - %s", DateUtils.formatReceivedDateToSentDate(lotteryResultList.get(lotteryResultList.size()-1).getDate()),
                    DateUtils.formatReceivedDateToSentDate(lotteryResultList.get(0).getDate())));
            txtInfoStatus.setTextColor(getResources().getColor(R.color.green));
        }
    }

}
