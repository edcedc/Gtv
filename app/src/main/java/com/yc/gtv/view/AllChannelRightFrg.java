package com.yc.gtv.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.library.AutoFlowLayout;
import com.example.library.FlowAdapter;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.databinding.FAllChannelRightBinding;
import com.yc.gtv.event.SelectedLabelEvent;
import com.yc.gtv.presenter.AllChannelRightPresenter;
import com.yc.gtv.view.impl.AllChannelRightContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/19.
 */

public class AllChannelRightFrg extends BaseFragment<AllChannelRightPresenter, FAllChannelRightBinding> implements AllChannelRightContract.View{

    private boolean isRefresh = false;
    private List<DataBean> listBean = new ArrayList<>();

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (!isRefresh){
            isRefresh = true;
            mPresenter.onRequest();
        }
    }

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_all_channel_right;
    }

    @Override
    protected void initView(View view) {
        mB.rvLabel.setMultiChecked(true);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onSelectedLabelMainInEvent(SelectedLabelEvent event){
        String id = event.id;
        for (DataBean bean : listBean){
            if (bean.getId().equals(id)){
                bean.setSelect(false);
                break;
            }
        }
        setRyLabel();
    }

    @Override
    public void setData(final List<DataBean> list) {
        listBean.addAll(list);
        setRyLabel();
    }

    private void setRyLabel(){
        mB.rvLabel.clearViews();
        mB.rvLabel.setAdapter(new FlowAdapter(listBean) {
            @Override
            public View getView(final int i) {
                View view = View.inflate(act, R.layout.i_channel_label, null);
                TextView tvText = view.findViewById(R.id.tv_text);
                DataBean bean = listBean.get(i);
                tvText.setText("标签" + i);
                int[][] states = new int[][]{
                        new int[]{-android.R.attr.state_selected}, // unchecked
                        new int[]{android.R.attr.state_selected}  // checked
                };
                int[] colors = new int[]{
                        Color.parseColor("#666666"),
                        Color.parseColor("#ffffff")
                };
                tvText.setTextColor(new ColorStateList(states, colors));
                tvText.setSelected(bean.isSelect());
                return view;
            }
        });
        mB.rvLabel.setOnItemClickListener(new AutoFlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int i, View view) {
                DataBean bean = listBean.get(i);
                if (!bean.isSelect()) {
                    bean.setSelect(true);
                } else {
                    bean.setSelect(false);
                }
                EventBus.getDefault().post(new SelectedLabelEvent(i + "", "添加" + i,  bean.isSelect()));
            }
        });
    }

}
