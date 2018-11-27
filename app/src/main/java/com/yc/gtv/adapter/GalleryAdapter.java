package com.yc.gtv.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BaseRecyclerviewAdapter;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.utils.GlideLoadingUtils;
import com.yc.gtv.weight.PictureSelectorTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/16.
 */

public class GalleryAdapter extends BaseRecyclerviewAdapter<DataBean>{
    public GalleryAdapter(Context act, BaseFragment root, List<DataBean> listBean, boolean isGallery) {
        super(act, root, listBean);
        this.isGallery = isGallery;
    }

    private boolean isGallery = false;

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        GlideLoadingUtils.load(act, "http://ws2.sinaimg.cn/large/82e98952ly1fxaah3fs3vj20ku0kujur.jpg", viewHolder.ivImg);
        if (isGallery){
            viewHolder.layout.setVisibility(View.VISIBLE);
            viewHolder.tvContent.setText("图片集名称名称名称最多显示一行");
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UIHelper.startGalleryDescFrg(root);
                }
            });
            viewHolder.tvCollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewHolder.tvCollection.setText("已收藏");
                }
            });
        }else {
            viewHolder.layout.setVisibility(View.GONE);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<LocalMedia> localMediaList = new ArrayList<>();
                    LocalMedia media = new LocalMedia();
                    media.setMimeType(PictureConfig.TYPE_IMAGE);
                    media.setPictureType("image/jpeg");
                    media.setPath("http://ws2.sinaimg.cn/large/82e98952ly1fxaah3fs3vj20ku0kujur.jpg");
                    localMediaList.add(media);
                    localMediaList.add(media);
                    localMediaList.add(media);
                    localMediaList.add(media);
                    PictureSelectorTool.PictureMediaType((Activity) act, localMediaList, 0);
                }
            });
        }
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_gallery, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivImg;
        TextView tvContent;
        TextView tvCollection;
        TextView tvDownload;
        TextView tvShare;
        View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvCollection = itemView.findViewById(R.id.tv_collection);
            tvDownload = itemView.findViewById(R.id.tv_download);
            tvShare = itemView.findViewById(R.id.tv_share);
            layout = itemView.findViewById(R.id.layout);
        }
    }

}
