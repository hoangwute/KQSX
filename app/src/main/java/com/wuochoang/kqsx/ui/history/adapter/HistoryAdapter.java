package com.wuochoang.kqsx.ui.history.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuochoang.kqsx.App;
import com.wuochoang.kqsx.R;
import com.wuochoang.kqsx.manager.database.RealmHelper;
import com.wuochoang.kqsx.model.History;
import com.wuochoang.kqsx.model.InputInfoEntry;
import com.wuochoang.kqsx.ui.history.dialog.ConfirmDeleteDialog;
import com.wuochoang.kqsx.ui.result.adapter.InputInfoEntryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 19-Mar-18.
 */

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<History> historyList;
    private Activity context;
    private OnDeleteHistoryListener listener;

    public interface OnDeleteHistoryListener {
        void onDelete(int id);
    }

    public HistoryAdapter(List<History> historyList, Activity context, OnDeleteHistoryListener listener) {
        this.historyList = historyList;
        this.context = context;
        this.listener = listener;
    }

    public void updateResult(List<History> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new EntryHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        History history = historyList.get(position);
        ((EntryHistoryHolder) holder).bind(history);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class EntryHistoryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtHistoryRuntimes)
        TextView txtHistoryRunTimes;
        @BindView(R.id.txtHistoryResults)
        TextView txtHistoryResults;
        @BindView(R.id.txtComparedHistoryDate)
        TextView txtComparedHistoryDate;
        @BindView(R.id.txtComparedHistoryCode)
        TextView txtComparedHistoryCode;
        @BindView(R.id.txtHistoryResultsPercentage)
        TextView txtHistoryResultsPercentage;
        @BindView(R.id.rvHistoryEntry)
        RecyclerView rvHistoryEntry;
        @BindView(R.id.btnDeleteHistory)
        ImageView imgDeleteHistory;

        private HistoryEntryAdapter adapter;
        private List<InputInfoEntry> entryList = new ArrayList<>();

        public EntryHistoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bind(History history) {
            txtHistoryRunTimes.setText(String.valueOf(history.getRunTimes()));
            txtHistoryResults.setText(String.valueOf(history.getResult()));
            txtHistoryResultsPercentage.setText(txtHistoryResults.getText().toString() + "/" + txtHistoryRunTimes.getText().toString() + " = " + (history.getResult() * 100 / Integer.parseInt(txtHistoryRunTimes.getText().toString())) + "%");
            if(history.getComparedEntry() != null) {
                txtComparedHistoryDate.setText(history.getComparedEntry().getDate());
                txtComparedHistoryCode.setText(history.getComparedEntry().getCode());
            }
            entryList = history.getEntryList();
            adapter = new HistoryEntryAdapter(entryList);
            rvHistoryEntry.setAdapter(adapter);
            rvHistoryEntry.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            imgDeleteHistory.setOnClickListener(view -> listener.onDelete(history.getId()));
        }
    }

}
