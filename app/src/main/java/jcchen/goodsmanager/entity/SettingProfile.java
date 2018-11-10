package jcchen.goodsmanager.entity;

import java.io.Serializable;

public class SettingProfile implements Serializable {
    
    private int GoodsNumberStart = 0;
    private int CurrentGoodsNumber;

    public void setGoodsNumberStart(int goodsNumberStart) {
        GoodsNumberStart = goodsNumberStart;
    }

    public int getGoodsNumberStart() {
        return GoodsNumberStart;
    }

    public void initCurrentGoodsNumber() {
        CurrentGoodsNumber = GoodsNumberStart;
    }

    public void increaseCurrentGoodsNumber() {
        CurrentGoodsNumber++;
    }
}
