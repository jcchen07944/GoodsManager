package jcchen.goodsmanager.view.listener;

import java.util.Vector;

import jcchen.goodsmanager.entity.SizeInfo;

public interface OnSizeSelectedListener {
    void onSizeSelected(Vector<SizeInfo> sizeList);
}
