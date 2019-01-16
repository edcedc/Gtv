package com.yc.gtv.bean;

/**
 * Created by shuyu on 2016/12/7.
 */

public class SwitchVideoModel {
    private String url;
    private String name;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public SwitchVideoModel( ) {
    }

    public SwitchVideoModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}