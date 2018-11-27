package com.yc.gtv.event;

/**
 * Created by edison on 2018/11/19.
 */

public class SelectedLabelEvent {

    public String id;
    public String content;
    public boolean select = false;

    public SelectedLabelEvent(String id, String content, boolean select) {
        this.id = id;
        this.content = content;
        this.select = select;
    }

    public SelectedLabelEvent(String id) {
        this.id = id;
    }
}
