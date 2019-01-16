package com.yc.gtv.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BaseRecyclerviewAdapter;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.controller.UIHelper;

import java.util.List;

/**
 * Created by edison on 2018/11/21.
 */

public class NotificationAdapter extends BaseRecyclerviewAdapter<DataBean>{

    public NotificationAdapter(Context act, BaseFragment root, List listBean) {
        super(act, root, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final DataBean bean = listBean.get(position);
        viewHolder.tvTitle.setText(bean.getTitle());
        String[] split = bean.getCreateTime().split(" ");
        viewHolder.tvTime.setText(split[0]);
        bean.setCreateTime(split[0]);
        viewHolder.tvContent.setText(bean.getContext());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.startNotificationDescFrg(root, bean);
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_msg, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tvContent;
        AppCompatTextView tvTitle;
        AppCompatTextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }

}
