package com.wuochoang.kqsx.ui.history.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuochoang.kqsx.App;
import com.wuochoang.kqsx.R;
import com.wuochoang.kqsx.model.History;
import com.wuochoang.kqsx.model.InputInfoEntry;
import com.wuochoang.kqsx.ui.result.adapter.InputInfoEntryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 20-Mar-18.
 */

public class HistoryEntryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<InputInfoEntry> historyEntryList;

    public HistoryEntryAdapter(List<InputInfoEntry> historyEntryList) {
        this.historyEntryList = historyEntryList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entry_history, parent, false);
        return new HistoryEntryHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        InputInfoEntry entry = historyEntryList.get(position);
        ((HistoryEntryHolder) holder).bind(entry, position);
    }

    @Override
    public int getItemCount() {
        return historyEntryList.size();
    }

    public class HistoryEntryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtEntryId)
        TextView txtEntryId;
        @BindView(R.id.txtEntryDate)
        TextView txtEntryDate;
        @BindView(R.id.txtEntryCode)
        TextView txtEntryCode;


        public HistoryEntryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(InputInfoEntry entry, int position) {
            txtEntryId.setText(String.valueOf(position+1) + ")");
            txtEntryDate.setText(entry.getDate());
            txtEntryCode.setText(entry.getCode());
        }
    }
}
