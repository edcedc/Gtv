package com.yc.gtv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BaseListViewAdapter;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.utils.GlideLoadingUtils;
import com.yc.gtv.weight.RoundImageView;

import java.util.List;

/**
 * Created by edison on 2018/11/16.
 */

public class ChannelChildAdapter extends BaseListViewAdapter<DataBean>{

    public ChannelChildAdapter(Context act, BaseFragment root, List<DataBean> listBean) {
        super(act, root, listBean);
    }

    @Override
    protected View getCreateVieww(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_channel_child, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DataBean bean = listBean.get(position);
        String tagImage = bean.getTagImage();
        if (StringUtils.isEmpty(tagImage)){
            GlideLoadingUtils.load(act, bean.getCover(), viewHolder.ivImg);
        }else {
            GlideLoadingUtils.load(act, tagImage, viewHolder.ivImg);
        }
        String tagName = bean.getTagName();
        if (StringUtils.isEmpty(tagName)){
            viewHolder.tvText.setText(bean.getTitle());
        }else {
            viewHolder.tvText.setText(tagName);
        }
        /*viewHolder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataBean bean1 = dataList.get(i);
                UIHelper.startChannelNameFrg(root, bean1.getChannelId());
            }
        });*/
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String channelId = bean.getChannelId();
                if (StringUtils.isEmpty(channelId)){
                    UIHelper.startVideoDescAct(bean.getId());
                }else {
                    UIHelper.startChannelNameFrg(root, channelId);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {

        RoundImageView ivImg;
        TextView tvText;

        public ViewHolder(View convertView) {
            ivImg = convertView.findViewById(R.id.iv_img);
            tvText = convertView.findViewById(R.id.tv_text);
        }
    }

}
