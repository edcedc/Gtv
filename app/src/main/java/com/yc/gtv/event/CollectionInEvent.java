package com.yc.gtv.event;

/**
 * Created by edison on 2018/11/30.
 */

public class CollectionInEvent {

    public int position;
    public int state;

    public CollectionInEvent(int position, int state) {
        this.position = position;
        this.state = state;
    }
}
