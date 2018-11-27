package com.yc.gtv.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by edison on 2018/11/19.
 */

public class SaveSearchListBean extends LitePalSupport {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
