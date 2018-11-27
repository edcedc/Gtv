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
        DataBean bean = listBean.get(position);
        GlideLoadingUtils.load(act, "http://ww3.sinaimg.cn/large/0073tLPGgy1fx9o3gj1z7j30hs0hstat.jpg", viewHolder.ivImg);
        viewHolder.tvText.setText("视频标题最多显示一行一行一行一行一行一行一行");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.startVideoDescAct();
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
