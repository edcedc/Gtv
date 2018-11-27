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
}