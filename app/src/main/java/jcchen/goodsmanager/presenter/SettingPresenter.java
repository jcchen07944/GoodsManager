package jcchen.goodsmanager.presenter;

import java.util.ArrayList;

import jcchen.goodsmanager.entity.ColorInfo;
import jcchen.goodsmanager.entity.DateInfo;
import jcchen.goodsmanager.entity.SizeInfo;
import jcchen.goodsmanager.entity.TypeInfo;

public interface SettingPresenter {
    void saveExchangeRate(float exRate);
    float getExchangeRate();
    void saveType(ArrayList<TypeInfo> typeList);
    void saveColor(ArrayList<ColorInfo> colorList);
    void saveSize(ArrayList<SizeInfo> sizeList);
    void saveDate(DateInfo dateInfo);
    ArrayList<TypeInfo> getTypeList();
    ArrayList<ColorInfo> getColorList();
    ArrayList<SizeInfo> getSizeList();
    DateInfo getDate();
    void resetSetting();
}
