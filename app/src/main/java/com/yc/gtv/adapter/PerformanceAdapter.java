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
        return listBean.get(groupPosition).getProd().size();
    }

    //        获取指定的分组数据
    @Override
    public Object getGroup(int groupPosition) {
        return listBean.get(groupPosition);
    }

    //        获取指定分组中的指定子选项数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listBean.get(groupPosition).getProd();
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
        viewHolder.tvTitle.setText("2018-06   " + groupPosition);
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
        final DataBean childBean = bean.getProd().get(childPosition);

        viewHolder.tvName.setText("推广会员人数");
        viewHolder.tvNum.setText("8人");
        if (childPosition == bean.getProd().size() - 1){
            viewHolder.view.setVisibility(View.GONE);
        }
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

        AppCompatTextView tvName;
        AppCompatTextView tvNum;
        View view;

        public ChildViewHolder(View itemView) {
            tvName = itemView.findViewById(R.id.tv_name);
            tvNum = itemView.findViewById(R.id.tv_num);
            view = itemView.findViewById(R.id.view);
        }
    }

}
