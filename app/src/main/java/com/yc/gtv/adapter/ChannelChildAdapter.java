package com.yc.gtv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseListViewAdapter;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.utils.GlideLoadingUtils;
import com.yc.gtv.weight.RoundImageView;

import java.util.List;

/**
 * Created by edison on 2018/11/16.
 */

public class ChannelChildAdapter extends BaseListViewAdapter<DataBean>{

    public ChannelChildAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected View getCreateVieww(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_channel_child, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DataBean bean = listBean.get(position);
        GlideLoadingUtils.load(act, "http://ww3.sinaimg.cn/large/0073tLPGgy1fx9mrev13xj30go0oj0y4.jpg", viewHolder.ivImg);
        viewHolder.tvText.setText("标签名称");

        return convertView;
    }

    class ViewHolder {

        RoundImageView ivImg;
        TextView tvText;

        public ViewHolder(View convertView) {
            ivImg = convertView.findViewById(R.id.iv_img);
            tvText = convertView.findViewById(R.id.tv_text);
        }
    }

}
