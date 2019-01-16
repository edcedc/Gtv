package com.yc.gtv.utils;

import android.os.Environment;

import com.yc.gtv.base.User;

/**
 * Created by yc on 2017/9/30.
 */

public class Constants {

   public static final int ITEMCOUNT = 10;
   public static final int pageSize = 10;

   public static final int day_min = 86400;//一天秒数

   public static final String ShareID = "5bc9f7e8f1f556233100068d";

   public static final String WX_APPID = "wx5f3d158419a3865f";
   public static final String WX_SECRER = "9934d27cec5fd1aaa3535db02540d086";
   public static final String QQ_APPID = "1107890734";
   public static final String QQ_KEY = "QF8dwXayGiU1ybmF";

   public static final String mainPath = Environment.getExternalStorageDirectory() + "/gtv/";
   public static final String imgUrl = mainPath + "img/" + User.getInstance().getUserId() + "/";
   public static final String videoUrl = mainPath + "video/" + User.getInstance().getUserId() + "/";

   public static final String ZFB_PAY = "2018102061789029";

   public static final int HISTORICAL_VIDEO = 0;//视频历史
   public static final int HISTORICAL_IMG = 1;//图片历史
   public static final int COLLECTION_VIDEO = 2;//视频收藏
   public static final int COLLECTION_IMG = 3;//图片收藏
   public static final int CACHE_VIDEO = 4;//视频缓存
   public static final int CACHE_IMG = 5;//图片缓存

}
