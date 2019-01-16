package com.yc.gtv.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.library.AutoFlowLayout;
import com.example.library.FlowAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yc.gtv.R;
import com.yc.gtv.adapter.ClassLeftAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.databinding.FAllChannelBinding;
import com.yc.gtv.event.SelectedLabelEvent;
import com.yc.gtv.presenter.AllChannelPresenter;
import com.yc.gtv.view.impl.AllChannelContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/19.
 *  全部频道
 */

public class AllChannelFrg extends BaseFragment<AllChannelPresenter, FAllChannelBinding> implements AllChannelContract.View{

    private List<DataBean> listLeft = new ArrayList<>();
    private ClassLeftAdapter leftAdapter;

    private List<Fragment> fragments = new ArrayList<>();
    private List<DataBean> listSelected = new ArrayList<>();

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_all_channel;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.all_channel), getString(R.string.sure));
        if (leftAdapter == null){
            leftAdapter = new ClassLeftAdapter(act, listLeft);
        }
        mB.lvLeft.setAdapter(leftAdapter);
        mB.lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                leftAdapter.setmPosition(i);
                leftAdapter.notifyDataSetChanged();
                SwitchTo(i);
            }
        });
        mPresenter.onLeftRequest();
        EventBus.getDefault().register(this);

        mB.rvLabel.setOnItemClickListener(new AutoFlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int i, View view) {
                DataBean bean = listSelected.get(i);
                listSelected.remove(i);
                initRvLabel();
                EventBus.getDefault().post(new SelectedLabelEvent(bean, 1));
            }
        });
    }

    private void initRvLabel(){
        mB.rvLabel.clearViews();
        mB.rvLabel.setAdapter(new FlowAdapter(listSelected) {
            @Override
            public View getView(final int i) {
                View view = View.inflate(act, R.layout.i_selected_label, null);
                TextView tvText = view.findViewById(R.id.tv_text);
                DataBean bean = listSelected.get(i);
                tvText.setText(bean.getTagName());
                return view;
            }
        });
    }

    /**
     *  添加或删除  到已选标签里面
     * @param event
     */
    @Subscribe
    public void onSelectedLabelMainInEvent(SelectedLabelEvent event){
        if (event.type != 0)return;
        DataBean bean1 = event.bean;
        if (event.select){
            listSelected.add(bean1);
        }else {
            for (DataBean bean : listSelected){
                if (bean.getTagId().equals(bean1.getTagId())){
                    listSelected.remove(bean);
                    break;
                }
            }
        }
        initRvLabel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        if (listSelected.size() == 0){
            showToast(getString(R.string.error_label));
            return;
        }
        UIHelper.startLabelScreeningFrg(this, listSelected);
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        for (Fragment f : fragments){
            transaction.hide(f);
        }
        transaction.show(fragments.get(position));
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void setData(List<DataBean> list) {
        listLeft.addAll(list);
        leftAdapter.setmPosition(0);
        leftAdapter.notifyDataSetChanged();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                DataBean bean = list.get(i);
                fragments.add(createListFragments(i, bean.getList()));
            }
            for (Fragment f : fragments) {
                transaction.add(R.id.fl_container, f);
            }
            transaction.commit();
            SwitchTo(0);
        }
    }

    private AllChannelRightFrg createListFragments(int position, List<DataBean> list) {
        AllChannelRightFrg fragment = new AllChannelRightFrg();
        Bundle bundle = new Bundle();
        bundle.putString("list", new Gson().toJson(list, new TypeToken<ArrayList<DataBean>>() {}.getType()));
        fragment.setArguments(bundle);
        return fragment;
    }

}
