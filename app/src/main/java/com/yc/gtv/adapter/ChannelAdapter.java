package com.yc.gtv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BaseRecyclerviewAdapter;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.weight.WithScrollGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/16.
 */

public class ChannelAdapter extends BaseRecyclerviewAdapter<DataBean>{

    public ChannelAdapter(Context act, BaseFragment root, List<DataBean> listBean) {
        super(act, root, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        viewHolder.tvTitle.setText("标题");
        List<DataBean> listBean = new ArrayList<>();
        for (int i =0;i < 4;i++){
            listBean.add(new DataBean());
        }
        ChannelChildAdapter adapter = new ChannelChildAdapter(act, listBean);
        viewHolder.gridView.setAdapter(adapter);
        viewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UIHelper.startChannelNameFrg(root);
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_channel, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        WithScrollGridView gridView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            gridView = itemView.findViewById(R.id.gridView);
        }
    }

}
