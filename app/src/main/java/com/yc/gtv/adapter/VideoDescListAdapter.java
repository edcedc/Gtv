package com.yc.gtv.adapter;

import android.content.Context;
import android.support.constraint.Group;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.example.library.AutoFlowLayout;
import com.example.library.FlowAdapter;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseRecyclerviewAdapter;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.utils.Constants;
import com.yc.gtv.utils.GlideLoadingUtils;
import com.yc.gtv.weight.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/18.
 */

public class VideoDescListAdapter extends BaseRecyclerviewAdapter<DataBean>{

    public VideoDescListAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }
    public VideoDescListAdapter(Context act, List<DataBean> listBean, int type) {
        super(act, listBean);
        this.type = type;
    }

    private int type;

    private boolean isEdit = false;
    private boolean isAllSelect = false;

    public void setAllSelect(boolean allSelect) {
        isAllSelect = allSelect;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final DataBean bean = listBean.get(position);

        GlideLoadingUtils.load(act, "http://ww3.sinaimg.cn/large/0073tLPGgy1fxc6g6jpitj30ku0z6e81.jpg", viewHolder.ivImg);
        viewHolder.tvTitle.setText("1234567891011121314151617181920");
        viewHolder.tvNum.setText("57,4 万次播放" + position);

        viewHolder.cb.setVisibility(isEdit == true ? View.VISIBLE : View.GONE);
        viewHolder.cb.setChecked(bean.isSelect());
        viewHolder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bean.setSelect(viewHolder.cb.isChecked());
//                if (listener != null)listener.onClick(position, viewHolder.cb.isChecked());
            }
        });
        //图片页面进来不需要显示
        viewHolder.gpImg.setVisibility(type == Constants.HISTORICAL_CHILD_IMG ? View.INVISIBLE :View.VISIBLE);

        if (isAllSelect){
            for (int i = 0;i < listBean.size();i++){
                DataBean bean1 = listBean.get(i);
                bean1.setSelect(true);
                viewHolder.cb.setChecked(true);
                LogUtils.e(bean1.isSelect(), position, i);
            }
        }else {
            for (DataBean bean1 : listBean){
                bean1.setSelect(false);
                viewHolder.cb.setChecked(false);
            }
        }

        final List<DataBean> list = new ArrayList<>();
        list.add(new DataBean());
        list.add(new DataBean());
        viewHolder.rvLabel.removeAllViews();
        viewHolder.rvLabel.setMultiChecked(true);
        viewHolder.rvLabel.setAdapter(new FlowAdapter(list) {
            @Override
            public View getView(final int i) {
                View view = View.inflate(act, R.layout.i_video_label, null);
                TextView tvText = view.findViewById(R.id.tv_text);
                DataBean bean = list.get(i);
                tvText.setText("标签" + i);
                return view;
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.startVideoDescAct();
            }
        });
    }

    private onClickListener listener;
    public void setOnClickListener(onClickListener listener){
        this.listener = listener;
    }
    public interface onClickListener{
        void onClick(int position, boolean isChecked);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_video_desc_list, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        RoundImageView ivImg;
        AppCompatTextView tvTitle;
        AppCompatTextView tvNum;
        AutoFlowLayout rvLabel;
        CheckBox cb;
        Group gpImg;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            tvTitle = itemView.findViewById(R.id.tv_title);
            rvLabel = itemView.findViewById(R.id.rv_label);
            tvNum = itemView.findViewById(R.id.tv_num);
            cb = itemView.findViewById(R.id.cb);
            gpImg = itemView.findViewById(R.id.gp_img);
        }
    }

}
