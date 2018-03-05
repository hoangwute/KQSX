package com.wuochoang.kqsx.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by quyenlx on 8/9/2017.
 */


public abstract class BaseViewHolder<T extends BaseModel> extends RecyclerView.ViewHolder {
    protected Context mContext;

    public BaseViewHolder(View view) {
        super(view);
        mContext = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public abstract void bindData(T t);
}
