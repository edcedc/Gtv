package com.yc.gtv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseListViewAdapter;
import com.yc.gtv.bean.DataBean;

import java.util.List;

/**
 * Created by edison on 2018/11/16.
 */

public class BannerGridAdapter extends BaseListViewAdapter<DataBean>{

    public BannerGridAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    private int checked = -1;

    @Override
    protected View getCreateVieww(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_circle, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DataBean bean = listBean.get(position);

        LogUtils.e(position);
        if (checked == position) {
            viewHolder.ivImg.setImageDrawable(act.getDrawable(R.drawable.circle_red));
        } else {
            viewHolder.ivImg.setImageDrawable(act.getDrawable(R.drawable.circle_gray));
        }

        return convertView;
    }

    class ViewHolder {

        ImageView ivImg;

        public ViewHolder(View convertView) {
            ivImg = convertView.findViewById(R.id.iv_img);
        }
    }

}
