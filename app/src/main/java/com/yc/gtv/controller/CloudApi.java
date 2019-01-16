package com.yc.gtv.controller;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okrx2.adapter.ObservableBody;
import com.lzy.okrx2.adapter.ObservableResponse;
import com.yc.gtv.base.User;
import com.yc.gtv.bean.BaseListBean;
import com.yc.gtv.bean.BaseResponseBean;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.callback.JsonConvert;
import com.yc.gtv.callback.NewsCallback;
import com.yc.gtv.utils.Constants;
import com.yc.gtv.utils.cache.ShareSessionIdCache;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：yc on 2018/6/20.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class CloudApi {


    private static final String url =
//            "10.0.0.200:8081/luxury_shopping/" ;
//            "47.106.217.107/";
            "tstapi.gzyunck.com/";

    public static final String SERVLET_IMG_URL = "http://" +
            url;

    public static final String SERVLET_URL = SERVLET_IMG_URL + "api/";


    public static final String TEST_URL = ""; //测试

    private static final String TAG = "CloudApi";

    private CloudApi() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 3.1.1获取验证码注册 接口(好)
     */
    public static Observable<Response<BaseResponseBean>> authGetSms(String phone) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "auth/getSms")
                .params("mobile", phone)
                .params("uuid", DeviceUtils.getAndroidID())
                .converter(new JsonConvert<BaseResponseBean>() {
                })
                .adapt(new ObservableResponse<BaseResponseBean>())
                .subscribeOn(Schedulers.io());
    }


    /**
     * 注册
     */
    public static Observable<Response<BaseResponseBean>> authRegister(String mobile, String password, String code, String inviteCode) {
        PostRequest<BaseResponseBean> post = OkGo.<BaseResponseBean>post(SERVLET_URL + "auth/register");
        if (!StringUtils.isEmpty(inviteCode)) {
            post.params("inviteCode", inviteCode);
        } else {
            post.params("inviteCode", "");
        }
        return post
                .params("mobile", mobile)
                .params("password", password)
                .params("code", code)
                .params("uuid", DeviceUtils.getAndroidID())
                .converter(new JsonConvert<BaseResponseBean>() {
                })
                .adapt(new ObservableResponse<BaseResponseBean>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 找回密码
     */
    public static Observable<Response<BaseResponseBean>> authResetPwd(String mobile, String password, String code) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "auth/resetPwd")
                .params("mobile", mobile)
                .params("password", password)
                .params("code", code)
                .params("uuid", DeviceUtils.getAndroidID())
                .converter(new JsonConvert<BaseResponseBean>() {
                })
                .adapt(new ObservableResponse<BaseResponseBean>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 登陆接口
     */
    public static Observable<JSONObject> authLogin(String mobile, String code) {
        return OkGo.<JSONObject>post(SERVLET_URL + "auth/login")
                .params("mobile", mobile)
                .params("password", code)
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<JSONObject>())
                .subscribeOn(Schedulers.io());
    }


    /**
     * 用户退出接口(好)
     */
    public static Observable<Response<BaseResponseBean>> authLogout() {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "auth/logout")
                .params("sessionId", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new JsonConvert<BaseResponseBean>() {
                })
                .adapt(new ObservableResponse<BaseResponseBean>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取某个频道详情
     *
     * @return
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> channelGetChannelTagDetail(String id) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "channel/getChannelTagDetail")
                .params("channelId", id)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 收藏 1图集2视频
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> commonCollect(String id, int type) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "common/collect")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("id", id)
                .params("type", type)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 提交意见
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> mineOpinion(String id, String context) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "mine/opinion")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("reasonId", id)
                .params("context", context)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 设置已读
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> noticeSetRead(String noticeId) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "notice/setRead")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("noticeId", noticeId)
                .params("udid", DeviceUtils.getAndroidID())
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取用户信息
     */
    public static Observable<JSONObject> userGetUserInfo() {
        return OkGo.<JSONObject>post(SERVLET_URL + "user/getUserInfo")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<JSONObject>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取匿名用户当天观影次数（不需要登陆）
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> userAnonymousViewTimesAtToday() {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "user/anonymousViewTimes")
                .params("udid", DeviceUtils.getAndroidID())
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 10.3、获取用户当天观影次数
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> userGetViewTimesAtToday() {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "user/getViewTimes")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("udid", DeviceUtils.getAndroidID())
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取图集详细
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> galleryGetGalleryDeatil(String id) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "gallery/getGalleryDeatil")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("galleryId", id)
                .params("userId", User.getInstance().getUserId())
                .params("udid", DeviceUtils.getAndroidID())
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 7.4、记录观影次数
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> videoRecordViewTimes() {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "video/recordViewTimes")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", User.getInstance().getUserId())
                .params("udid", DeviceUtils.getAndroidID())
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }



    /**
     * 10.4、修改用户昵称
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> userModifyNickname(String name) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "user/modifyNickname")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("nickname", name)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 7.2、获取视频详情 (主页接口部分)
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> videoGetVideoDeatil(String id) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "video/getVideoDeatil")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("videoId", id)
                .params("token", User.getInstance().getSessionId())
                .params("udid", DeviceUtils.getAndroidID())
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 1.9、获取APP协议
     *  2: 用户协议，3: 会员权益说明 4: 推广规则
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> commonQueryAPPAgreement(int flag) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "common/queryAPPAgreement")
                .params("flag", flag)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 10.5、上传用户头像
     */
    public static final String userUploadAvatar = SERVLET_URL + "user/uploadAvatar";
    public static Observable<Response<BaseResponseBean<DataBean>>> userUploadAvatar(String head) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "user/uploadAvatar")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .upFile(new File(head))
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 删除收藏
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> collectDelete(List<String> listBean) {
        JSONArray array = new JSONArray();
        for (String s : listBean) {
            array.put(s);
        }
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "collect/delete")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("ids", array.toString())
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 9.3、删除历史记录
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> historyDelete(List<String> listBean) {
        JSONArray array = new JSONArray();
        for (String s : listBean) {
            array.put(s);
        }
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "history/delete")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("ids", array.toString())
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 10.7、获取我的推广统计
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> userGetMyPromotionStatist() {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "user/getMyPromotionStatist")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 10.6、获取推广信息
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> userGetPromotionInfo() {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "user/getPromotionInfo")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }


    /**
     * 10.8、获取代理商推广明细
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> mineOemDetail() {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "mine/oemDetail")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 7.3、获取视频下载链接(有权限)
     */
    public static Observable<JSONObject> videoVideoDownloadUrl(String id, int clarity) {
        return OkGo.<JSONObject>post(SERVLET_URL + "video/videoDownloadUrl")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("videoId", id)
                .params("clarity", clarity)
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<JSONObject>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 5.2、支付套餐
     */
    public static Observable<JSONObject> minePay(String vipWareid, int type) {
        return OkGo.<JSONObject>post(SERVLET_URL + "mine/pay")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("vipWareid", vipWareid)
                .params("type", type)
                .params("userId", User.getInstance().getUserId())
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<JSONObject>())
                .subscribeOn(Schedulers.io());
    }


    /**
     * 获取图集列表（分页）
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> galleryGetGalleryList(int pageNumber) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>post(SERVLET_URL + "gallery/getGalleryList")
                .params("pageNumber", pageNumber)
                .params("pageIndex", pageNumber)
                .params("pageSize", Constants.pageSize)
                .params("userId", User.getInstance().getUserId())
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<BaseListBean<DataBean>>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 2.4、搜索查找视频
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> homeSearch(int pageNumber, String keyWord) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>post(SERVLET_URL + "home/search")
                .params("pageNumber", pageNumber)
                .params("pageIndex", pageNumber)
                .params("pageSize", Constants.pageSize)
                .params("keyWord", keyWord)
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<BaseListBean<DataBean>>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 根据标签编号查找视频(分页)
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> videoGetVideoByTagIds(int pageNumber, List<DataBean> listBean) {
        JSONArray array = new JSONArray();
        for (DataBean bean : listBean) {
            array.put(bean.getTagId());
        }
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>post(SERVLET_URL + "video/getVideoByTagIds")
                .params("pageNumber", pageNumber)
                .params("pageIndex", pageNumber)
                .params("pageSize", Constants.pageSize)
                .params("tagIds", array.toString())
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<BaseListBean<DataBean>>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 通用list数据
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> list(int pageNumber, String url) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>post(SERVLET_URL + url)
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("pageNumber", pageNumber)
                .params("pageIndex", pageNumber)
                .params("pageSize", Constants.pageSize)
                .params("sessionId", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<BaseListBean<DataBean>>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取分类下视频
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> homeVediosByClassify(int pageNumber, String classify) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>post(SERVLET_URL + "home/vediosByClassify")
                .params("start", pageNumber)
                .params("limit", Constants.pageSize)
                .params("classify", classify)
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<BaseListBean<DataBean>>>())
                .subscribeOn(Schedulers.io());
    }


    /**
     * 获取系统通知列表（分页）
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> noticeGetSystemNoticeList(int pageNumber, String udid) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>post(SERVLET_URL + "notice/getSystemNoticeList")
                .params("pageIndex", pageNumber)
                .params("pageSize", Constants.pageSize)
                .params("udid", DeviceUtils.getAndroidID())
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<BaseListBean<DataBean>>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 9.1、获取图集历史查看记录
     */
    public static final String historyGetGalleryHistoryList = "history/getGalleryHistoryList";
    /**
     * 9.2、获取视频历史观看记录
     */
    public static final String historyGetVideoHistoryList = "history/getVideoHistoryList";

    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> historyGetGalleryHistoryList(String url, int pageNumber, long startTime, long endTime) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>post(SERVLET_URL + url)
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("pageIndex", pageNumber)
                .params("pageSize", Constants.pageSize)
                .params("udid", DeviceUtils.getAndroidID())
                .params("startTime", startTime)
                .params("endTime", endTime)
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<BaseListBean<DataBean>>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 频道
     */
    public static final String channelGetAllChannelTag = "channel/getAllChannelTag";
    /**
     * 获取视频分类
     */
    public static final String homeClassify = "home/classify";
    /**
     *  获取收藏的图集(分页)
     */
//    public static final String collectPageQueryGalleryCollect = "collect/getGalleryList";
    /**
     * 获取收藏的视频（分页）
     */
    public static final String collectPageQueryVideoCollect = "collect/pageQueryVideoCollect";
    /**
     *  获取收藏的图集(分页)
     */
    public static final String collectPageQueryGalleryCollect = "collect/pageQueryGalleryCollect";
    /**
     * 反馈原因列表
     */
    public static final String mineReasons = "mine/reasons";
    /**
     * 获取系统通知列表（分页）
     */
    public static final String noticeGetSystemNoticeList = "notice/getSystemNoticeList";
    /**
     *  2.3、获取轮播图
     */
    public static final String homeGetBanner = "home/getBanner";
    /**
     *  2.6、获取热门搜索关键字
     */
    public static final String homeKeywords = "home/keywords";
    /**
     *  10.9、获取我的余额流水记录
     */
    public static final String mineBalanceFlow = "mine/balanceFlow";
    /**
     *  5.1、获取支付套餐
     */
    public static final String mineVipWares = "mine/vipWares";

    /**
     * 获取所有视频标签
     */
    public static Observable<Response<BaseResponseBean<List<DataBean>>>> channelGetAllTag() {
        return OkGo.<BaseResponseBean<List<DataBean>>>post(SERVLET_URL + "channel/getAllTag")
                .converter(new NewsCallback<BaseResponseBean<List<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<List<DataBean>>> response) {
                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<List<DataBean>>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 2.5、猜你喜欢
     * @param id
     */
    public static Observable<Response<BaseResponseBean<List<DataBean>>>> homeGuessLike(String id) {
        return OkGo.<BaseResponseBean<List<DataBean>>>post(SERVLET_URL + "home/guessLike")
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("topNum", 10)
                .params("tagId", id)
                .converter(new NewsCallback<BaseResponseBean<List<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<List<DataBean>>> response) {
                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<List<DataBean>>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 通用list 2
     */
    public static Observable<Response<BaseResponseBean<List<DataBean>>>> list2(String url) {
        return OkGo.<BaseResponseBean<List<DataBean>>>post(SERVLET_URL + url)
                .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("sessionId", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<List<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<List<DataBean>>> response) {
                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<List<DataBean>>>())
                .subscribeOn(Schedulers.io());
    }
}