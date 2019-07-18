package com.wuochoang.kqsx.ui.excel.view;

import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.olddog.common.ToastUtils;
import com.wuochoang.kqsx.App;
import com.wuochoang.kqsx.R;
import com.wuochoang.kqsx.base.BaseFragment;
import com.wuochoang.kqsx.base.BasePresenter;
import com.wuochoang.kqsx.common.Constant;
import com.wuochoang.kqsx.manager.database.RealmHelper;
import com.wuochoang.kqsx.model.LotteryResult;
import com.wuochoang.kqsx.network.ApiService;
import com.wuochoang.kqsx.utility.DateUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Created by HoangNQ on 11,August,2018
 */
public class ExportExcelFragment extends BaseFragment {

    @Inject
    ApiService apiService;

    @BindView(R.id.txtInfoStatus)
    TextView txtInfoStatus;

    @Override
    public void injectDependence() {
        component.inject(this);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_export_excel;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void initView() {
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

    @OnClick(R.id.btnGetExcel)
    public void exportExcel() {
        String cacheDir = "";
        File externalFileDir = App.get().getExternalFilesDir(null);
        if (externalFileDir != null) {
            cacheDir = externalFileDir.getAbsolutePath() + "/cacheUpload/";
            File cacheFileDir = new File(cacheDir);
            if (!cacheFileDir.exists())
                cacheFileDir.mkdirs();

        }
        File fileCache = new File(cacheDir + "myData.xls");

        try {

            //file path

            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(fileCache, wbSettings);
            WritableSheet sheet = workbook.createSheet("Giai Nhat", 0);
            // column and row
            sheet.addCell(new Label(0, 0, "UserName"));
            sheet.addCell(new Label(1, 0, "PhoneNumber"));

            List<LotteryResult> lotteryResultList = RealmHelper.findAll(LotteryResult.class, "date", 1, -1, null);

            for(int i = 0; i < lotteryResultList.size(); i++) {
                sheet.addCell(new Label(0, i, lotteryResultList.get(i).getG0()));
                sheet.addCell(new Label(1, i, lotteryResultList.get(i).getDate()));
                Log.d("Excel", lotteryResultList.get(i).getG0());
            }
            workbook.write();
            workbook.close();
            ToastUtils.show("Export to Excel done!");

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {

    }
}
