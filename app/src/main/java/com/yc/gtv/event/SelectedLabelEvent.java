package com.yc.gtv.event;

import com.yc.gtv.bean.DataBean;

/**
 * Created by edison on 2018/11/19.
 */

public class SelectedLabelEvent {

    public DataBean bean;
    public boolean select = false;
    public int type;//0是添加到选择标签    1是删除标签

    public SelectedLabelEvent(DataBean bean, boolean select, int type) {
        this.bean = bean;
        this.select = select;
        this.type = type;
    }

    public SelectedLabelEvent(DataBean bean, int type) {
        this.bean = bean;
        this.type = type;
    }
}
