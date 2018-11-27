package com.yc.gtv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseListViewAdapter;
import com.yc.gtv.bean.DataBean;

import java.util.List;

/**
 * Created by edison on 2018/11/17.
 */

public class LabelAdapter extends BaseListViewAdapter<DataBean>{
    public LabelAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected View getCreateVieww(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_label, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DataBean bean = listBean.get(position);
        viewHolder.tvName.setText(bean.getName());
        viewHolder.tvName.setCompoundDrawablesWithIntrinsicBounds(null,
               act.getResources().getDrawable(bean.getImg(), null), null, null);
        return convertView;
    }

    class ViewHolder {

        TextView tvName;

        public ViewHolder(View convertView) {
            tvName = convertView.findViewById(R.id.tv_name);
        }
    }

}
