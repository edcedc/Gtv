package com.yc.gtv.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.Group;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BaseRecyclerviewAdapter;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.utils.Constants;
import com.yc.gtv.utils.GlideLoadingUtils;
import com.yc.gtv.weight.PictureSelectorTool;
import com.yc.gtv.weight.RoundImageView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

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
    public VideoDescListAdapter(Context act, BaseFragment root, List<DataBean> listBean, int type) {
        super(act, root, listBean);
        this.type = type;
    }

    private int type = -1;

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

        if (type <= 3){
            viewHolder.tvNum.setText(bean.getViewCount() + "次播放");
        }else {
            viewHolder.tvNum.setText(bean.getViewCount());
        }
        if (type == Constants.HISTORICAL_IMG){
            GlideLoadingUtils.load(act, bean.getUrl(), viewHolder.ivImg);
            viewHolder.tvTitle.setText(bean.getName());
        }else {
            GlideLoadingUtils.load(act, bean.getCover(), viewHolder.ivImg);
            viewHolder.tvTitle.setText(bean.getTitle());
        }

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
//        viewHolder.gpImg.setVisibility(type == Constants.HISTORICAL_CHILD_IMG ? View.INVISIBLE :View.VISIBLE);

        if (isAllSelect){
            for (int i = 0;i < listBean.size();i++){
                DataBean bean1 = listBean.get(i);
                bean1.setSelect(true);
                viewHolder.cb.setChecked(true);
            }
        }else {
            for (DataBean bean1 : listBean){
                bean1.setSelect(false);
                viewHolder.cb.setChecked(false);
            }
        }

        final List<DataBean> tags = bean.getTags();
        if (tags != null && tags.size() != 0){
            viewHolder.rvLabel.setVisibility(View.VISIBLE);
            viewHolder.rvLabel.removeAllViews();
            viewHolder.rvLabel.setAdapter(new TagAdapter<DataBean>(tags){
                @Override
                public View getView(FlowLayout parent, int position, DataBean dataBean) {
                    View view = View.inflate(act, R.layout.i_video_label, null);
                    TextView tvText = view.findViewById(R.id.tv_text);
                    DataBean bean = tags.get(position);
                    tvText.setText(bean.getTagName());
                    return view;
                }
            });
        }else {
            viewHolder.rvLabel.setVisibility(View.INVISIBLE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type){
                    case Constants.HISTORICAL_IMG:
                        UIHelper.startGalleryDescAct(bean.getId(), -1);
                        break;
                    case Constants.HISTORICAL_VIDEO:
                    case Constants.COLLECTION_VIDEO:
                        UIHelper.startVideoDescAct(bean.getId());
                        break;
                    case Constants.COLLECTION_IMG:
                        UIHelper.startGalleryDescAct(bean.getId(), -1);
                        break;
                    case Constants.CACHE_VIDEO:
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        String path = bean.getCover();//该路径可以自定义
                        LogUtils.e(path);
                        File file = new File(path);
                        Uri uri = Uri.fromFile(file);
                        intent.setDataAndType(uri, "video/*");
                        startActivity(intent);
                        break;
                    case Constants.CACHE_IMG:
                        List<LocalMedia> localMediaList = new ArrayList<>();
                        for (DataBean bean1 : listBean){
                            LocalMedia media = new LocalMedia();
                            media.setMimeType(PictureConfig.TYPE_IMAGE);
                            media.setPictureType("image/jpeg");
                            media.setPath(bean1.getCover());
                            localMediaList.add(media);
                        }
                        PictureSelectorTool.PictureMediaType((Activity) act, localMediaList, position);
                        break;
                    default:
                        UIHelper.startVideoDescAct(bean.getId());
                        break;
                }


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
        TagFlowLayout rvLabel;
        CheckBox cb;
        Group gpImg;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            tvTitle = itemView.findViewById(R.id.tv_title);
            rvLabel = itemView.findViewById(R.id.rv_label);
            tvNum = itemView.findViewById(R.id.tv_num);
            cb = itemView.findViewById(R.id.cb);
//            gpImg = itemView.findViewById(R.id.gp_img);
        }
    }

}
