package com.example.admin.recyclerviewtest.refresh;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.recyclerviewtest.R;

/**
 * Created by tangyangkai on 2017/1/4.
 */

public class MyRefreshRecyclerView extends RecyclerView {

    private MyRefreshAdapter myRefreshAdapter;
    private MyLoadListener myLoadListener;
    private boolean isLoadMore = true; //默认是可以向上滑动加载
    private TextView tvLoad;
    private View footView;
    private ProgressBar progressBar;
    private boolean isLoadComplete =false;


    public void setMyLoadListener(MyLoadListener myLoadListener) {
        this.myLoadListener = myLoadListener;
    }

    public MyRefreshRecyclerView(Context context) {
        super(context);
        init();
    }

    public MyRefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init() {
        this.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //停止滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示Item的Position
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    // 判断是否滚动到底部，并且不在加载状态

                    if (lastVisibleItem == (totalItemCount - 1)&&isLoadMore) {
                        isLoadMore = false;
                        tvLoad.setText("正在加载...");
                        progressBar.setVisibility(VISIBLE);
                        footView.setVisibility(VISIBLE);
                        myLoadListener.onLoadMore();
                    }
                }
            }

            /**
             * 避免没有占满全屏，就显示加载
             * dy >0 向上滚动
             * dy<0 向下滚动
             * dy==0 一般是列表数据没有占满全屏
             * @param recyclerView
             * @param dx
             * @param dy
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    isLoadMore = true;
                } else if (dy <= 0) {
                    isLoadMore = false;
                }
                Log.d("testtest", "dx==" + dx + "dy==" + dy);
            }
        });
    }

    @Override
    public void setAdapter(Adapter adapter) {

        LinearLayout footerLayout = new LinearLayout(getContext());
        footerLayout.setGravity(Gravity.CENTER);
        footerLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 160));
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        View  view = mInflater.inflate(R.layout.item_foot, null);
        tvLoad = (TextView) view.findViewById(R.id.tv_load);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        footerLayout.addView(view);
        footView = footerLayout;
        footView.setVisibility(GONE);
        myRefreshAdapter = new MyRefreshAdapter(adapter);
        myRefreshAdapter.addFooterView(footView);
        super.setAdapter(myRefreshAdapter);

    }

    public interface MyLoadListener {
        void onLoadMore();
    }

    public void setLoadMore(boolean complete) {
        if (complete) {
            footView.setVisibility(GONE);
        } else {
            tvLoad.setText("已经全部加载完啦!");
            progressBar.setVisibility(GONE);
            myRefreshAdapter.notifyItemRemoved(myRefreshAdapter.getItemCount()+1);
        }
        isLoadComplete = complete;
    }

}
