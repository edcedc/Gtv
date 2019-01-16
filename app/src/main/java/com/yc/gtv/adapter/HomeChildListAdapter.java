package com.yc.gtv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseRecyclerviewAdapter;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.utils.GlideLoadingUtils;
import com.yc.gtv.weight.RoundImageView;

import java.util.List;

/**
 * Created by edison on 2018/11/16.
 */

public class HomeChildListAdapter extends BaseRecyclerviewAdapter<DataBean>{

    public HomeChildListAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final DataBean bean = listBean.get(position);
        GlideLoadingUtils.load(act, bean.getCover(), viewHolder.ivImg);
        viewHolder.tvText.setText(bean.getTitle());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.startVideoDescAct(bean.getId());
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_home_list, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        RoundImageView ivImg;
        TextView tvText;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }

}
