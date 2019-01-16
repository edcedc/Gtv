package com.yc.gtv.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.library.AutoFlowLayout;
import com.example.library.FlowAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.gtv.R;
import com.yc.gtv.adapter.VideoDescListAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.bean.SaveSearchListBean;
import com.yc.gtv.databinding.FSearchBinding;
import com.yc.gtv.presenter.SearchPresenter;
import com.yc.gtv.view.impl.SearchContract;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/18.
 *  搜索
 */

public class SearchFrg extends BaseFragment<SearchPresenter, FSearchBinding> implements SearchContract.View, OnClickListener{

    private EditText etSearch;
    private List<DataBean> listBean = new ArrayList<>();
    private VideoDescListAdapter adapter;
    private AppCompatTextView tvCancel;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_search;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(View view) {
        etSearch = view.findViewById(R.id.et_searh);
        View fyClose = view.findViewById(R.id.fy_close);
        fyClose.setVisibility(View.VISIBLE);
        fyClose.setOnClickListener(this);
        tvCancel = view.findViewById(R.id.tv_cancel);
//        tvCancel.setVisibility(View.VISIBLE);
        tvCancel.setOnClickListener(this);
        view.findViewById(R.id.iv_search).setOnClickListener(this);
        mB.fyDel.setOnClickListener(this);
        setSearchData();
        if (adapter == null){
            adapter = new VideoDescListAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        mPresenter.onRequest(1);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onSearch(etSearch.getText().toString().trim(), pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onSearch(etSearch.getText().toString().trim(), pagerNumber += 1);
            }
        });

        mB.rvHistoricalRecords.setMultiChecked(true);
        mB.rvHotSearch.setMultiChecked(true);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //判断是否是“完成”键
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //隐藏软键盘
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    etSearch.setText(etSearch.getText().toString().trim());
                    mPresenter.onSaveHistory(etSearch.getText().toString().trim(), mB.gpList, mB.recyclerView, mB.refreshLayout, pagerNumber = 1);

                    return true;
                }
                return false;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (editable.length() == 0){
                            tvCancel.setVisibility(View.GONE);
                        }else {
                            tvCancel.setVisibility(View.VISIBLE);
                        }
                    }
                }, 300);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fy_close:
                act.onBackPressed();
                break;
            case R.id.tv_cancel:
                etSearch.setText("");
                tvCancel.setVisibility(View.GONE);
                mB.gpList.setVisibility(View.VISIBLE);
                mB.recyclerView.setVisibility(View.GONE);
                break;
            case R.id.iv_search:
                mPresenter.onSaveHistory(etSearch.getText().toString().trim(), mB.gpList, mB.recyclerView, mB.refreshLayout, pagerNumber = 1);
                break;
            case R.id.fy_del:
                LitePal.deleteAll(SaveSearchListBean.class);
                mB.rvHistoricalRecords.removeAllViews();
                break;
        }
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
        super.setRefreshLayoutMode(listBean.size(), totalRow, mB.refreshLayout);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    @Override
    public void setData(Object data) {
        final List<DataBean> list = (List<DataBean>) data;
        mB.rvHotSearch.removeAllViews();
        mB.rvHotSearch.setAdapter(new FlowAdapter(list) {
            @Override
            public View getView(final int i) {
                View view = View.inflate(act, R.layout.i_search_label, null);
                TextView tvText = view.findViewById(R.id.tv_text);
                DataBean bean = list.get(i);
                tvText.setText(bean.getName());
                return view;
            }
        });
        mB.rvHotSearch.setOnItemClickListener(new AutoFlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int i, View view) {
                etSearch.setText(list.get(i).getName());
                mPresenter.onSaveHistory(list.get(i).getName(), mB.gpList, mB.recyclerView, mB.refreshLayout, pagerNumber = 1);
            }
        });

    }

    @Override
    public void setSearchData() {
        final List<SaveSearchListBean> all = LitePal.findAll(SaveSearchListBean.class);
        if (all != null && all.size() != 0){
            mB.rvHistoricalRecords.removeAllViews();
            mB.rvHistoricalRecords.setAdapter(new FlowAdapter(all) {
                @Override
                public View getView(final int i) {
                    View view = View.inflate(act, R.layout.i_search_label, null);
                    TextView tvText = view.findViewById(R.id.tv_text);
                    SaveSearchListBean bean = all.get(i);
                    tvText.setText(bean.getContent());
                    return view;
                }
            });
            mB.rvHistoricalRecords.setOnItemClickListener(new AutoFlowLayout.OnItemClickListener() {
                @Override
                public void onItemClick(int i, View view) {
                    etSearch.setText(all.get(i).getContent());
                    mPresenter.onSaveHistory(all.get(i).getContent(), mB.gpList, mB.recyclerView, mB.refreshLayout, pagerNumber = 1);
                }
            });
        }
    }

    @Override
    public void setSearchList(List<DataBean> list) {
        if (pagerNumber == 1) {
            listBean.clear();
            mB.refreshLayout.finishRefreshing();
        } else {
            mB.refreshLayout.finishLoadmore();
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
