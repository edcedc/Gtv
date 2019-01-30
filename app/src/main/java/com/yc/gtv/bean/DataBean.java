package com.yc.gtv.bean;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 2017/8/17.
 */

public class DataBean implements Serializable {

    private String name;
    private int img;
    private String id;
    private boolean isSelect = false;
    private BigDecimal price;
    private String content;
    private String context;
    private String title;
    private String tagName;//频道名字
    private String tagImage;//频道图片
    private String tagId;//频道
    private String channelId;
    private String classify;//全部频道左边名字
    private String poster;
    private String remark;
    private String url;
    private String cover;
    private String collectId;
    private String createTime;
    private String date;
    private Object images[];
    private int allowViewTimes;//允许观影次数
    private int remainingViewTimes;//剩余观影次数
    private int referNum;//推广人数
    private String imageUrl;
    private String imgUrl;
    private String href;//轮播图详情
    private int numberOfPeoplePromoted;//已推广人数
    private int numberOfVipMember;//推广Vip会员人数
    private BigDecimal promotedAmount;//推广金额
    private BigDecimal bonus;//个人奖励金/推广金
    private String downloadUrl;//下载链接
    private String invitCode;//推广码
    private boolean collected;//是否收藏
    private String viewCount;//播放数量
    private String playUrl;//视频链接
    private double totalPrice;//推广总金额
    private int noVipStat;//推广非会员人数
    private int vipStat;//推广会员人数
    private int type;
    private int calType;//1增加 2减少
    private int invalidday;
    private String historyId;
    private int[] claritys;
    private int num;
    private Object[] imageUrls;
    private int residualViewCount;//播放视频剩余次数
    private String linkUrl;

    public String getLinkUrl() {
        return linkUrl;
    }

    public int getResidualViewCount() {
        return residualViewCount;
    }

    public Object[] getImageUrls() {
        return imageUrls;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int[] getClaritys() {
        return claritys;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getHistoryId() {
        return historyId;
    }

    public int getInvalidday() {
        return invalidday;
    }

    public int getCalType() {
        return calType;
    }

    public int getType() {
        return type;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getNoVipStat() {
        return noVipStat;
    }

    public int getVipStat() {
        return vipStat;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInvitCode() {
        return invitCode;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public int getNumberOfPeoplePromoted() {
        return numberOfPeoplePromoted;
    }

    public int getNumberOfVipMember() {
        return numberOfVipMember;
    }

    public BigDecimal getPromotedAmount() {
        return promotedAmount;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public String getHref() {
        return href;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getAllowViewTimes() {
        return allowViewTimes;
    }

    public int getReferNum() {
        return referNum;
    }

    public int getRemainingViewTimes() {
        return remainingViewTimes;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object[] getImages() {
        return images;
    }

    public String getDate() {
        return date;
    }

    public String getContext() {
        return context;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCollectId() {
        return collectId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getRemark() {
        return remark;
    }

    public String getPoster() {
        return poster;
    }

    public String getTagId() {
        return tagId;
    }

    public String getClassify() {
        return classify;
    }

    public String getTagImage() {
        return tagImage;
    }

    public String getTagName() {
        return tagName;
    }

    public String getTitle() {
        return title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public String getId() {
        return id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    private List<DataBean> prod = new ArrayList<>();

    public List<DataBean> getProd() {
        return prod;
    }

    public void setProd(List<DataBean> prod) {
        this.prod = prod;
    }

    private List<DataBean> dataList = new ArrayList<>();

    public List<DataBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataBean> dataList) {
        this.dataList = dataList;
    }

    private List<DataBean> list = new ArrayList<>();

    public List<DataBean> getList() {
        return list;
    }

    private List<DataBean> tags = new ArrayList<>();

    public List<DataBean> getTags() {
        return tags;
    }
    private List<DataBean> detail = new ArrayList<>();

    public List<DataBean> getDetail() {
        return detail;
    }

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public class InfoBean{
        private double totalPrice;//推广总金额
        private int noVipStat;//推广非会员人数
        private int vipStat;//推广会员人数


        public double getTotalPrice() {
            return totalPrice;
        }

        public int getNoVipStat() {
            return noVipStat;
        }

        public int getVipStat() {
            return vipStat;
        }
    }

}