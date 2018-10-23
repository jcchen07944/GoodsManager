package jcchen.goodsmanager.presenter;

import java.util.ArrayList;

import jcchen.goodsmanager.entity.ColorInfo;
import jcchen.goodsmanager.entity.SizeInfo;
import jcchen.goodsmanager.entity.TypeInfo;

public interface SettingPresenter {
    void saveExchangeRate(float exRate);
    float getExchangeRate();
    void addType(TypeInfo typeInfo);
    void addColor(ColorInfo colorInfo);
    void addSize(SizeInfo sizeInfo);
    ArrayList<TypeInfo> getTypeList();
    ArrayList<ColorInfo> getColorList();
    ArrayList<SizeInfo> getSizeList();

}
