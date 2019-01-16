package com.yc.gtv.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.yc.gtv.R;
import com.yc.gtv.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：yc on 2018/8/29.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class PerformanceAdapter extends BaseExpandableListAdapter {

    private Context act;
    private List<DataBean> listBean = new ArrayList<>();

    public PerformanceAdapter(Context act, List listBean) {
        this.act = act;
        this.listBean = listBean;
    }

    //        获取分组的个数
    @Override
    public int getGroupCount() {
        return listBean.size();
    }

    //        获取指定分组中的子选项的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    //        获取指定的分组数据
    @Override
    public Object getGroup(int groupPosition) {
        return listBean.get(groupPosition);
    }

    //        获取指定分组中的指定子选项数据
    @Override
        public Object getChild(int groupPosition, int childPosition) {
        return listBean.get(groupPosition).getDetail();
    }

    //        获取指定分组的ID, 这个ID必须是唯一的
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //        获取子选项的ID, 这个ID必须是唯一的
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //        分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们。
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_performance_group, null);
            viewHolder = new GroupViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag();
        }
        final DataBean bean = listBean.get(groupPosition);
        viewHolder.tvTitle.setText(bean.getDate());
        //判断isExpanded就可以控制是按下还是关闭，同时更换图片
        if(isExpanded){
            viewHolder.tvTitle.setCompoundDrawablesWithIntrinsicBounds(null,
                    null, act.getResources().getDrawable(R.mipmap.yjmx_more, null), null);
        }else{
            viewHolder.tvTitle.setCompoundDrawablesWithIntrinsicBounds(null,
                    null, act.getResources().getDrawable(R.mipmap.yjmx_more, null), null);           }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_performance_child, null);
            viewHolder = new ChildViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }
        final DataBean bean = listBean.get(groupPosition);
        DataBean.InfoBean childBean = bean.getInfo();
        viewHolder.tvNoVipStat.setText(childBean.getNoVipStat() + "人");
        viewHolder.tvVipStat.setText(childBean.getVipStat() + "人");
        viewHolder.tvPrice.setText(childBean.getTotalPrice() + "人");
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    class GroupViewHolder{

        AppCompatTextView tvTitle;

        public GroupViewHolder(View itemView) {
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }

    class ChildViewHolder{

        AppCompatTextView tvNoVipStat;
        AppCompatTextView tvVipStat;
        AppCompatTextView tvPrice;

        public ChildViewHolder(View itemView) {
            tvNoVipStat = itemView.findViewById(R.id.tv_noVipStat);
            tvVipStat = itemView.findViewById(R.id.tv_vipStat);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }

}
