package com.yc.gtv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseListViewAdapter;
import com.yc.gtv.bean.DataBean;

import java.util.List;

/**
 * Created by edison on 2018/11/21.
 */

public class OpinionAdapter extends BaseListViewAdapter<DataBean>{

    public OpinionAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    private int mPosition = -1;

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    @Override
    protected View getCreateVieww(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_opinion, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DataBean bean = listBean.get(position);
        viewHolder.tvText.setText(bean.getName());
        RoundViewDelegate delegate = viewHolder.tvText.getDelegate();
        if (mPosition == position){
            viewHolder.tvText.setTextColor(act.getResources().getColor(R.color.white));
            delegate.setBackgroundColor(act.getResources().getColor(R.color.reb_FC739D));
        }else {
            viewHolder.tvText.setTextColor(act.getResources().getColor(R.color.black_666666));
            delegate.setBackgroundColor(act.getResources().getColor(R.color.gray_F0F0F0));
        }
        return convertView;
    }

    class ViewHolder{

        RoundTextView tvText;

        public ViewHolder(View convertView) {
            tvText = convertView.findViewById(R.id.tv_text);
        }
    }

}
