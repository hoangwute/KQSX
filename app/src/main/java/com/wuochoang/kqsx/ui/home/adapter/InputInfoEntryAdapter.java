package com.wuochoang.kqsx.ui.home.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.olddog.common.KeyboardUtils;
import com.wuochoang.kqsx.R;
import com.wuochoang.kqsx.common.Constant;
import com.wuochoang.kqsx.model.InputInfoEntry;
import com.wuochoang.kqsx.utility.NumberUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 08-Mar-18.
 */

public class InputInfoEntryAdapter extends RecyclerView.Adapter<InputInfoEntryAdapter.MyViewHolder> {

    private Activity context;
    private List<InputInfoEntry> inputInfoEntryList;
    private Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date = (DatePicker view, int year, int monthOfYear, int dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    };

    public InputInfoEntryAdapter(Activity context, List<InputInfoEntry> inputInfoEntryList) {
        this.context = context;
        this.inputInfoEntryList = inputInfoEntryList;
    }

    public List<InputInfoEntry> getEntryList() {
        return inputInfoEntryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_input_info_entry, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InputInfoEntry entry = inputInfoEntryList.get(position);
        holder.bind(entry);
    }

    @Override
    public int getItemCount() {
        return inputInfoEntryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtEntryId)
        TextView txtEntryId;
        @BindView(R.id.txtEntryDate)
        EditText txtEntryDate;
        @BindView(R.id.txtEntryCode)
        EditText txtEntryCode;
        @BindView(R.id.txtEntryNumber)
        TextView txtEntryNumber;
        @BindView(R.id.txtEntryDigit)
        TextView txtEntryDigit;
        @BindView(R.id.imgArrow)
        ImageView imgArrow;
        @BindView(R.id.imgDeleteEntry)
        ImageView imgDeleteEntry;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.imgDeleteEntry)
        public void deleteEntry() {
            txtEntryCode.getText().clear();
            txtEntryDate.getText().clear();
            notifyDataSetChanged();
        }

        @OnClick(R.id.txtEntryDate)
        public void enterDate() {
            new DatePickerDialog(itemView.getContext(), (datePicker, year, monthOfYear, dayOfMonth) -> {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(Constant.SENT_DATE_FORMAT, Locale.US);
                txtEntryDate.setText(sdf.format(myCalendar.getTime()));
                KeyboardUtils.hideSoftInput((Activity) itemView.getContext());
            }, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }

        public void bind(InputInfoEntry entry) {
            txtEntryId.setText(String.valueOf(entry.getId()));
            txtEntryCode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    Log.d("Track", "Entry code triggered");
                    if(!txtEntryDate.getText().toString().isEmpty()) {
                        txtEntryNumber.setText(NumberUtils.convertCodeToNumber(txtEntryDate.getText().toString(), editable.toString()));
                        entry.setCode(txtEntryCode.getText().toString());
                        if(NumberUtils.getDigitFromFullNumber(editable.toString(), txtEntryNumber.getText().toString()) != null) {
                            int position = Integer.parseInt(NumberUtils.getDigitFromFullNumber(editable.toString(), txtEntryNumber.getText().toString()));
                            String digit = String.valueOf(txtEntryNumber.getText().toString().charAt(position));
                            txtEntryDigit.setText(digit);
                            entry.setDigit(digit);
                            if(!txtEntryNumber.getText().toString().isEmpty()) {
                                imgArrow.setVisibility(View.VISIBLE);
                            }
                            else
                                imgArrow.setVisibility(View.GONE);
                        }
                        else {
                            imgArrow.setVisibility(View.GONE);
                            txtEntryDigit.setText("");
                            entry.setDigit("");
                        }
                    }
                }
            });

            txtEntryDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    Log.d("Track", "Entry date triggered");
                    entry.setDate(editable.toString());
                    if(!txtEntryCode.getText().toString().isEmpty()) {
                        txtEntryNumber.setText(NumberUtils.convertCodeToNumber(editable.toString(), txtEntryCode.getText().toString()));
                        entry.setCode(txtEntryCode.getText().toString());
                        if(NumberUtils.getDigitFromFullNumber(txtEntryCode.getText().toString(), txtEntryNumber.getText().toString()) != null) {
                            int position = Integer.parseInt(NumberUtils.getDigitFromFullNumber(txtEntryCode.getText().toString(), txtEntryNumber.getText().toString()));
                            String digit = String.valueOf(txtEntryNumber.getText().toString().charAt(position));
                            txtEntryDigit.setText(digit);
                            entry.setDigit(digit);
                            if(!txtEntryNumber.getText().toString().isEmpty()) {
                                imgArrow.setVisibility(View.VISIBLE);
                            }
                            else
                                imgArrow.setVisibility(View.GONE);
                        }
                        else {
                            imgArrow.setVisibility(View.GONE);
                            txtEntryDigit.setText("");
                            entry.setDigit("");
                        }
                    }
                }
            });
        }
    }
}
