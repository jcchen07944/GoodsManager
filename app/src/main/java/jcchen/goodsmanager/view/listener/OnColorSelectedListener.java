package jcchen.goodsmanager.view.listener;

import java.util.Vector;

import jcchen.goodsmanager.entity.ColorInfo;

public interface OnColorSelectedListener {
    void onColorSelected(Vector<ColorInfo> selectedColor);
}
