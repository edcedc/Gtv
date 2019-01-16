package com.yc.gtv.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseListViewAdapter;
import com.yc.gtv.bean.DataBean;

import java.util.List;

/**
 * 作者：yc on 2018/8/29.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class ClassLeftAdapter extends BaseListViewAdapter<DataBean> {


    private int mPosition = -1;

    public ClassLeftAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected View getCreateVieww(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_classify_left, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DataBean bean = listBean.get(position);
        RoundViewDelegate delegate = viewHolder.tvName.getDelegate();
        viewHolder.tvName.setText(bean.getClassify());
        if (mPosition == position){
            viewHolder.tvName.setTextColor(act.getResources().getColor(R.color.white));
            delegate.setBackgroundColor(act.getResources().getColor(R.color.reb_FC739D));
            delegate.setStrokeColor(act.getResources().getColor(R.color.reb_FC739D));
        }else {
            viewHolder.tvName.setTextColor(act.getResources().getColor(R.color.black_333333));
            delegate.setBackgroundColor(act.getResources().getColor(R.color.white));
            delegate.setStrokeColor(act.getResources().getColor(R.color.black_eeeeee));
        }
        return convertView;
    }

    class ViewHolder{

        RoundTextView tvName;

        public ViewHolder(View convertView) {
            tvName = convertView.findViewById(R.id.tv_text);
        }
    }

}
