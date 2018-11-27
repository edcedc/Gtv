package com.yc.gtv.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.example.library.FlowAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.yc.gtv.R;
import com.yc.gtv.adapter.VideoDescListAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.databinding.FVideoDescBinding;
import com.yc.gtv.presenter.VideoDescPresenter;
import com.yc.gtv.utils.GlideLoadingUtils;
import com.yc.gtv.utils.PopupWindowTool;
import com.yc.gtv.view.impl.VideoDescContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/18.
 *  影片详情
 */

public class VideoDescFrg extends BaseFragment<VideoDescPresenter, FVideoDescBinding> implements VideoDescContract.View, View.OnClickListener{

    public static VideoDescFrg newInstance() {
        Bundle args = new Bundle();
        VideoDescFrg fragment = new VideoDescFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> listBean = new ArrayList<>();
    private VideoDescListAdapter adapter;

    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_video_desc;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.video_play));
        mB.tvCollection.setOnClickListener(this);
        mB.tvShare.setOnClickListener(this);
        mB.tvDownload.setOnClickListener(this);
        if (adapter == null){
            adapter = new VideoDescListAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        mB.refreshLayout.setEnableRefresh(false);
        mB.refreshLayout.setEnableLoadmore(false);
        mPresenter.onLabel();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onLabel();
            }
        });
        setSwipeBackEnable(false);
        mB.rvLabel.setMultiChecked(true);


        mB.tvTitle.setText("影片标题标题标题标题标题");
        mB.tvTime.setText("2018-09-07");
        mB.tvContent.setText("影片简介影片简介影片简介影片简介影片简介影片简介影片简介影片简介影片简介影片简介影片简介影片简介影片简介影片简介影片简介影片简");


        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(act, mB.videoPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        videoPlay();
    }

    private void videoPlay(){
        //增加封面
        ImageView imageView = new ImageView(act);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideLoadingUtils.load(act, "http://ww3.sinaimg.cn/large/0073tLPGgy1fxek3b6seej30sg0sg42r.jpg", imageView);
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setThumbImageView(imageView)
                .setLooping(true)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4")
                .setCacheWithPlay(false)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        LogUtils.e("onPrepared", objects[0], objects[1]);
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;

                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        LogUtils.e("onEnterFullscreen", objects[0], objects[1]);
                    }

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                        LogUtils.e("onAutoComplete", url);
                    }

                    @Override
                    public void onClickStartError(String url, Object... objects) {
                        super.onClickStartError(url, objects);
                        LogUtils.e("onClickStartError", url);
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        LogUtils.e("onQuitFullscreen", objects[0], objects[1]);
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                })
                .setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        if (orientationUtils != null) {
                            //配合下方的onConfigurationChanged
                            orientationUtils.setEnable(!lock);
                        }
                    }
                })
                .setGSYVideoProgressListener(new GSYVideoProgressListener() {
                    @Override
                    public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
//                        LogUtils.e(" progress " + progress + " secProgress " + secProgress + " currentPosition " + currentPosition + " duration " + duration);
                    }
                })
                .build(mB.videoPlayer);

        mB.videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                mB.videoPlayer.startWindowFullscreen(act, true, true);
            }
        });
    }

    @Override
    public void setLabel(final List<DataBean> list) {
        mB.rvLabel.removeAllViews();
        mB.rvLabel.setAdapter(new FlowAdapter(list) {
            @Override
            public View getView(final int i) {
                View view = View.inflate(act, R.layout.i_video_label, null);
                TextView tvText = view.findViewById(R.id.tv_text);
                DataBean bean = list.get(i);
                tvText.setText("标签" + i);

                return view;
            }
        });


        if (pagerNumber == 1) {
            listBean.clear();
            mB.refreshLayout.finishRefreshing();
        } else {
            mB.refreshLayout.finishLoadmore();
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private boolean isCollection = true;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_collection:
                if (isCollection){
                    mB.tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                            act.getResources().getDrawable(R.mipmap.home_xq_sc_nor, null), null, null);
                    mB.tvCollection.setText("已收藏");

                }else {
                    mB.tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                            act.getResources().getDrawable(R.mipmap.home_xq_sc_nor, null), null, null);
                    mB.tvCollection.setText("收藏");
                }
                isCollection = !isCollection;
                break;
            case R.id.tv_download:
                PopupWindowTool.showDialog(act, PopupWindowTool.notMember, new PopupWindowTool.DialogListener() {
                    @Override
                    public void onClick() {

                    }
                });
                break;
            case R.id.tv_share:
                PopupWindowTool.showDialog(act, PopupWindowTool.notLogin, new PopupWindowTool.DialogListener() {
                    @Override
                    public void onClick() {

                    }
                });
                break;
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(act)) {
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    public void onResume() {
        getCurPlay().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            getCurPlay().release();
        }
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            mB.videoPlayer.onConfigurationChanged(act, newConfig, orientationUtils, true, true);
        }
    }

    private GSYVideoPlayer getCurPlay() {
        if (mB.videoPlayer.getFullWindowPlayer() != null) {
            return  mB.videoPlayer.getFullWindowPlayer();
        }
        return mB.videoPlayer;
    }

}
