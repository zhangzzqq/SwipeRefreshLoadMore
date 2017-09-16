package com.example.admin.recyclerviewtest.refresh;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.recyclerviewtest.R;

/**
 * Created by tangyangkai on 2017/1/4.
 */

public class MyRefreshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private RecyclerView.Adapter adapter;
    private View footerView;
    public static final int NORMAL_VIEW_TYPE = 1;
    public static final int FOOTER_VIEW_TYPE = 2;

    public MyRefreshAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return FOOTER_VIEW_TYPE;
        }
        return NORMAL_VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_VIEW_TYPE) {
            return new RecyclerView.ViewHolder(footerView) {};
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot, parent,false);
//            return new FootViewHolder(view);

        } else {
            return adapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            return;
        } else {
            adapter.onBindViewHolder(holder, position);
        }

    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount() + 1;
    }

    public void addFooterView(View footerView) {
        this.footerView = footerView;
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;

        public FootViewHolder(View view) {
            super(view);
            tv =  view.findViewById(R.id.tv_load);

        }
    }

}
