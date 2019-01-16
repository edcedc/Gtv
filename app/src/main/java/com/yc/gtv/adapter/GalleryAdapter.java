package com.yc.gtv.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseActivity;
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
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final DataBean bean = listBean.get(position);
        if (isGallery){
            Object[] imageUrls = bean.getImageUrls();
            if (imageUrls.length != 0){
                GlideLoadingUtils.load(act, imageUrls[0], viewHolder.ivImg);
            }
            viewHolder.layout.setVisibility(View.VISIBLE);
            viewHolder.tvContent.setText(bean.getName());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UIHelper.startGalleryDescAct(bean.getId(), position);
                }
            });
            viewHolder.tvCollection.setText(bean.isCollected() == true ? "已收藏" : "收藏");
        }else {
            GlideLoadingUtils.load(act, bean.getUrl(), viewHolder.ivImg);
            viewHolder.layout.setVisibility(View.GONE);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<LocalMedia> localMediaList = new ArrayList<>();
                    LocalMedia media = new LocalMedia();
                    media.setMimeType(PictureConfig.TYPE_IMAGE);
                    media.setPictureType("image/jpeg");
                    media.setPath(bean.getUrl());
                    localMediaList.add(media);
                    PictureSelectorTool.PictureMediaType((Activity) act, localMediaList, 0);
                }
            });
        }
        viewHolder.tvCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((BaseActivity)act).isLogin())return;
                if (listener != null)listener.collection(position, 1, bean.getId(), bean.isCollected());
            }
        });
        viewHolder.tvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((BaseActivity)act).isLogin())return;
                if (!((BaseActivity)act).isMember())return;
                Object[] imageUrls = bean.getImageUrls();
                if (imageUrls.length != 0){
                    ToastUtils.showLong("正在下载，请稍后...");
                    for (int i = 0; i < imageUrls.length;i++){
                        if (imageUrls.length > 1){
                            GlideLoadingUtils.saveImage(act, (String) imageUrls[i], bean.getName() + i);
                        }else {
                            GlideLoadingUtils.saveImage(act, (String) imageUrls[i], bean.getName());
                        }
                    }
                }
            }
        });
        viewHolder.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((BaseActivity)act).isLogin())return;
                if (listener != null)listener.share();
            }
        });
    }

    private OnClickListener listener;
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }
    public interface OnClickListener{
        void collection(int position, int type, String id, boolean collected);
        void share();
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
