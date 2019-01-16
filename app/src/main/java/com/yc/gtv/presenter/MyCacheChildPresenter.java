package com.yc.gtv.presenter;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.utils.Constants;
import com.yc.gtv.view.impl.MyCacheChildContract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/21.
 */

public class MyCacheChildPresenter extends MyCacheChildContract.Presenter {

    @Override
    public void onRequest(int type) {
        mView.showLoadDataing();
        final List<DataBean> list = new ArrayList<>();
        if (type == Constants.CACHE_VIDEO) {
            final List<File> files = FileUtils.listFilesInDir(Constants.videoUrl);
            if (files != null && files.size() != 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (File file : files) {
                            DataBean bean = new DataBean();
                            String fileName = FileUtils.getFileName(file);
                            bean.setTitle(fileName.substring(0, fileName.length() - 4));
                            bean.setCover(file.toString());
                            bean.setViewCount(FileUtils.getFileSize(file));
                            list.add(bean);
                            LogUtils.e(fileName, FileUtils.getFileSize(file), file.toString());
                        }
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mView.setData(list);
                                mView.hideLoading();
                            }
                        });
                    }
                }).start();
            }else {
                mView.showLoadEmpty();
            }
        } else {
            final List<File> files = FileUtils.listFilesInDir(Constants.imgUrl);
            if (files != null && files.size() != 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (File file : files) {
                            DataBean bean = new DataBean();
                            String fileName = FileUtils.getFileName(file);
                            bean.setTitle(fileName.substring(0, fileName.length() - 4));
                            bean.setName(fileName);
                            bean.setCover(file.toString());
                            bean.setViewCount(FileUtils.getFileSize(file));
                            list.add(bean);
                            LogUtils.e(fileName, FileUtils.getFileSize(file), file.toString());
                        }
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mView.setData(list);
                                mView.hideLoading();
                            }
                        });
                    }
                }).start();
            }else {
                mView.showLoadEmpty();
            }
        }

    }

}
