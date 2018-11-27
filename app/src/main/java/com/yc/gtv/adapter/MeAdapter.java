package com.yc.gtv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseRecyclerviewAdapter;
import com.yc.gtv.bean.DataBean;

import java.util.List;

/**
 * Created by edison on 2018/5/5.
 */

public class MeAdapter extends BaseRecyclerviewAdapter<DataBean> {

    private boolean isImg = false;

    public MeAdapter(Context act, List listBean) {
        super(act, listBean);
    }
    public MeAdapter(Context act, List listBean, boolean isImg) {
        super(act, listBean);
        this.isImg = isImg;
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final DataBean bean = listBean.get(position);
        if (bean.getImg() < -1){
            viewHolder.ivImg.setVisibility(View.VISIBLE);
            viewHolder.ivImg.setImageResource(bean.getImg());
        }
        viewHolder.tvName.setText(bean.getName());
        viewHolder.ivBack.setVisibility(isImg == true ? View.VISIBLE : View.GONE);

        if (listBean.size() - 1 == position){
            viewHolder.view.setVisibility(View.GONE);
        }else {
            viewHolder.view.setVisibility(View.VISIBLE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click != null){
                    click.onClick(position, bean.getName());
                }
            }
        });
    }

    private OnClick click;
    public void setClick(OnClick click){
        this.click = click;
    }
    public interface OnClick{
        void onClick(int position, String text);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_list, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivImg;
        TextView tvName;
        View view;
        View ivBack;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            tvName = itemView.findViewById(R.id.tv_name);
            view = itemView.findViewById(R.id.view);
            ivBack = itemView.findViewById(R.id.iv_back);
        }
    }

}
