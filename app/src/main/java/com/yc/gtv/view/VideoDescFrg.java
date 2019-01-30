package com.yc.gtv.view;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import com.umeng.socialize.ShareAction;
import com.yc.gtv.R;
import com.yc.gtv.adapter.VideoDescListAdapter;
import com.yc.gtv.base.BaseActivity;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.User;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.bean.SwitchVideoModel;
import com.yc.gtv.databinding.FVideoDescBinding;
import com.yc.gtv.event.LoginInEvent;
import com.yc.gtv.presenter.VideoDescPresenter;
import com.yc.gtv.utils.GlideLoadingUtils;
import com.yc.gtv.utils.PopupWindowTool;
import com.yc.gtv.utils.ShareTool;
import com.yc.gtv.view.impl.VideoDescContract;
import com.yc.gtv.weight.CountDownTimer;
import com.yc.gtv.weight.SwitchVideoTypeDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by edison on 2018/11/18.
 * 影片详情
 */

public class VideoDescFrg extends BaseFragment<VideoDescPresenter, FVideoDescBinding> implements VideoDescContract.View, View.OnClickListener {

    private String id;
    private TextView tvResolvingPower;
    private int[] claritys;
    private GSYVideoOptionBuilder gsyVideoOption;
    private ShareAction shareAction;
    private int residualViewCount;

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
    private boolean isCollection;
    private String videoUrl;
    private boolean isOnePlay = true;//只记录一次播放

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        id = bundle.getString("id");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_video_desc;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.video_play));
        shareAction = ShareTool.getInstance().shareAction(act, "https://www.baidu.com/");
        mB.tvCollection.setOnClickListener(this);
        mB.tvShare.setOnClickListener(this);
        mB.tvDownload.setOnClickListener(this);
        tvResolvingPower = mB.videoPlayer.findViewById(R.id.tv_resolving_power);
        tvResolvingPower.setOnClickListener(this);
        tvResolvingPower.setText("480P");
        if (adapter == null) {
            adapter = new VideoDescListAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        showLoadDataing();
        mB.refreshLayout.startRefresh();
        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onVideoDeatil(id);
            }
        });
        setSwipeBackEnable(false);
        mB.rvLabel.setMultiChecked(true);
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(act, mB.videoPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
    }

    private void setVideoPlay(String imgUrl, String videoUrl) {
        this.videoUrl = videoUrl;
        //增加封面
        ImageView imageView = new ImageView(act);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideLoadingUtils.load(act, imgUrl, imageView);
        gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setThumbImageView(imageView)
                .setLooping(false)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(videoUrl)
                .setCacheWithPlay(true)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        LogUtils.e("onPrepared", objects[0], objects[1]);
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;
                        EventBus.getDefault().post(new LoginInEvent());
                        JSONObject userInfo = User.getInstance().getUserInfo();
                        if (isOnePlay){
                            isOnePlay = false;
                            mPresenter.onVideoRecordViewTimes();
                        }
                        if (userInfo != null && !userInfo.optBoolean("vip") && residualViewCount <= 0){
                            downTimer.start();
                        }else if (userInfo == null && residualViewCount <= 0){
                            downTimer.start();
                        }
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

    private void setCollection(boolean isCollection) {
        if (isCollection) {
            mB.tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                    act.getResources().getDrawable(R.mipmap.home_xq_sc_nor, null), null, null);
            mB.tvCollection.setText("已收藏");

        } else {
            mB.tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                    act.getResources().getDrawable(R.mipmap.home_xq_sc_nor, null), null, null);
            mB.tvCollection.setText("收藏");
        }
//        this.isCollection = !isCollection;
    }

    @Override
    public void onClick(View view) {
        if (!((BaseActivity)act).isLogin())return;
        switch (view.getId()) {
            case R.id.tv_collection:
                mPresenter.onCommonCollect(id, isCollection);
                break;
            case R.id.tv_download:
                if (!((BaseActivity)act).isMember())return;
                mPresenter.onVideoDownload(videoUrl, mB.tvTitle.getText().toString());
                break;
            case R.id.tv_share:
                shareAction.open();
                break;
            case R.id.tv_resolving_power://选择分辨率
                showSwitchDialog();
                break;
        }
    }

    /**
     * 弹出切换清晰度
     */
    private int mSourcePosition = 1;
    private void showSwitchDialog() {
        if (claritys.length == 0)return;
        final List<SwitchVideoModel> list = new ArrayList<>();
        for (int i : claritys){
            SwitchVideoModel bean = new SwitchVideoModel();
            bean.setNum(i);
            if (i == 1){
                bean.setName("480P");
            }else if (i == 2){
                bean.setName("720P");
            }else {
                bean.setName("1080P");
            }
            list.add(bean);
        }
        SwitchVideoTypeDialog switchVideoTypeDialog = new SwitchVideoTypeDialog(getContext());
        switchVideoTypeDialog.initList(list, new SwitchVideoTypeDialog.OnListItemClickListener() {
            @Override
            public void onItemClick(int position) {
                LogUtils.e(position);
                final String name = list.get(position).getName();
                position += 1;
                if (mSourcePosition != position) {
                    mPresenter.onVideoDownloadUrl(id, position);
                } else {
                    showToast("已经是 " + name);
                }
            }
        });
        switchVideoTypeDialog.show();
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
        ShareTool.getInstance().release(act);
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null){
            orientationUtils.releaseListener();
        }
        if (downTimer != null){
            downTimer.cancel();
            downTimer = null;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            mB.videoPlayer.onConfigurationChanged(act, newConfig, orientationUtils, true, true);
        }
        shareAction.close();
    }

    private GSYVideoPlayer getCurPlay() {
        if (mB.videoPlayer.getFullWindowPlayer() != null) {
            return mB.videoPlayer.getFullWindowPlayer();
        }
        return mB.videoPlayer;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    @Override
    public void setData(DataBean data) {
        setVideoPlay(data.getCover(), data.getPlayUrl());
        residualViewCount = data.getResidualViewCount();
        mB.tvTitle.setText(data.getTitle());
        String[] split = data.getCreateTime().split(" ");
        mB.tvTime.setText(split[0]);
        mB.tvContent.setText(data.getContext());
        mB.rvLabel.setVisibility(View.GONE);
        claritys = data.getClaritys();
        final List<DataBean> tags = data.getTags();
        if (tags != null && tags.size() != 0) {
            Random random=new Random();
            int r = random.nextInt(tags.size()); //获得随机下标
            mPresenter.onHomeGuessLike(tags.get(r).getTagId());

            mB.rvLabel.setVisibility(View.VISIBLE);
            mB.rvLabel.removeAllViews();
            mB.rvLabel.setAdapter(new FlowAdapter(tags) {
                @Override
                public View getView(final int i) {
                    View view = View.inflate(act, R.layout.i_video_label, null);
                    TextView tvText = view.findViewById(R.id.tv_text);
                    DataBean bean = tags.get(i);
                    tvText.setText(bean.getTagName());
                    return view;
                }
            });
        } else {
            mB.tvLabel.setVisibility(View.GONE);
            mB.rvLabel.setVisibility(View.INVISIBLE);
        }
        isCollection = data.isCollected();
        setCollection(isCollection);

        if (claritys.length == 0)return;
        final List<SwitchVideoModel> list = new ArrayList<>();
        for (int i : claritys){
            SwitchVideoModel bean = new SwitchVideoModel();
            bean.setNum(i);
            if (i == 1){
                tvResolvingPower.setText("480P");
            }else if (i == 2){
                tvResolvingPower.setText("720P");
            }else {
                tvResolvingPower.setText("1080P");
            }
            list.add(bean);
        }
    }

    @Override
    public void onCollectionSuccess(boolean isCollection) {
        setCollection(isCollection);
    }

    @Override
    public void onHomeGuessLike(List<DataBean> data) {
        listBean.clear();
        mB.refreshLayout.finishRefreshing();
        listBean.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setDownloadUrl(String data, int position) {
//        setVideoPlay(null, data);
        mSourcePosition = position;
        if (position == 1){
            tvResolvingPower.setText("480P");
        }else if (position == 2){
            tvResolvingPower.setText("720P");
        }else {
            tvResolvingPower.setText("1080P");
        }
        setVideoPlay(null, data);
        mB.videoPlayer.startPlayLogic();
    }

    private CountDownTimer downTimer = new CountDownTimer(300000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            mB.videoPlayer.onVideoPause();
            PopupWindowTool.showDialog(act, PopupWindowTool.onTrial, new PopupWindowTool.DialogListener() {
                @Override
                public void onClick() {
                    act.finish();
                }
            });
        }
    } ;

}
