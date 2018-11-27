package com.yc.gtv.presenter;

import com.blankj.utilcode.util.StringUtils;
import com.yc.gtv.R;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.view.impl.OpinionContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/21.
 */

public class OpinionPresenter extends OpinionContract.Presenter{

    @Override
    public void onLebel() {
        String[] array = act.getResources().getStringArray(R.array.opinion_list);
        List<DataBean> listStr = new ArrayList<>();
        for (String str : array){
            DataBean bean = new DataBean();
            bean.setName(str);
            listStr.add(bean);
        }
        mView.setLabel(listStr);
    }

    @Override
    public void onSubmit(String s) {
        if (StringUtils.isEmpty(s)){
            showToast(act.getString(R.string.error_));
            return;
        }

    }

}
