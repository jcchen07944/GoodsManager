package jcchen.goodsmanager.presenter.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import jcchen.goodsmanager.entity.ColorInfo;
import jcchen.goodsmanager.entity.DateInfo;
import jcchen.goodsmanager.entity.PostBlock;
import jcchen.goodsmanager.entity.SettingProfile;
import jcchen.goodsmanager.entity.SizeInfo;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.model.impl.LocalModelImpl;
import jcchen.goodsmanager.presenter.SettingPresenter;

public class SettingPresenterImpl implements SettingPresenter {

    private Context context;

    private LocalModelImpl mLocalModel;

    public SettingPresenterImpl(Context context) {
        this.context = context;
        this.mLocalModel = new LocalModelImpl(context);
    }

    @Override
    public void saveExchangeRate(float exRate) {
        mLocalModel.savePrivateObject("ExchangeRate", exRate, false);
    }

    @Override
    public float getExchangeRate() {
        Object object = mLocalModel.getPrivateObject("ExchangeRate", false);
        if (object != null)
            return (float) object;
        // Default ExchangeRate.
        saveExchangeRate(35.0f);
        return 35.0f;
    }


    @Override
    public void saveProfile(SettingProfile settingProfile) {
        mLocalModel.savePrivateObject("SettingProfile", settingProfile, false);
    }

    @Override
    public void savePostList(ArrayList<PostBlock> postList) {
        mLocalModel.savePrivateObject("PostList", postList, false);
    }

    @Override
    public void saveType(ArrayList<TypeInfo> list) {

        ArrayList<TypeInfo> typeList = new ArrayList<>(list);

        for (int i = typeList.size() - 1; i >= 0; i--)
            if (typeList.get(i) == null)
                typeList.remove(i);

        if (typeList.size() == 0)
            typeList = getDefaultTypeList();

        mLocalModel.savePrivateObject("TypeList", typeList, false);
    }

    @Override
    public void saveColor(ArrayList<ColorInfo> list) {

        ArrayList<ColorInfo> colorList = new ArrayList<>(list);

        for (int i = colorList.size() - 1; i >= 0; i--)
            if (colorList.get(i) == null)
                colorList.remove(i);

        if (colorList.size() == 0)
            colorList = getDefaultColorList();

        mLocalModel.savePrivateObject("ColorList", colorList, false);
    }

    @Override
    public void saveSize(ArrayList<SizeInfo> list) {

        ArrayList<SizeInfo> sizeList = new ArrayList<>(list);

        for (int i = sizeList.size() - 1; i >= 0; i--)
            if (sizeList.get(i) == null)
                sizeList.remove(i);

        if (sizeList.size() == 0)
            sizeList = getDefaultSizeList();

        mLocalModel.savePrivateObject("SizeList", sizeList, false);
    }

    @Override
    public void saveDate(DateInfo dateInfo) {
        mLocalModel.savePrivateObject("DateInfo", dateInfo, false);
    }

    @Override
    public SettingProfile getProfile() {
        Object object = mLocalModel.getPrivateObject("SettingProfile", false);
        if (object != null)
            return (SettingProfile) object;

        SettingProfile mSettingProfile = new SettingProfile();
        saveProfile(mSettingProfile);
        return mSettingProfile;
    }

    @Override
    public ArrayList<PostBlock> getPostList() {
        Object object = mLocalModel.getPrivateObject("PostList", false);
        if (object != null)
            return (ArrayList<PostBlock>) object;

        ArrayList<PostBlock> postList = getDefaultPostList();
        savePostList(postList);
        return postList;
    }

    /**
     *  Get type list from model(maybe from DB or from setting)
     *
     *  Now getting from setting.
     **/
    @Override
    public ArrayList<TypeInfo> getTypeList() {
        Object object = mLocalModel.getPrivateObject("TypeList", false);
        if (object != null)
            return (ArrayList<TypeInfo>) object;

        ArrayList<TypeInfo> typeList = getDefaultTypeList();
        saveType(typeList);
        return typeList;
    }

    @Override
    public ArrayList<ColorInfo> getColorList() {
        Object object = mLocalModel.getPrivateObject("ColorList", false);
        if (object != null)
            return (ArrayList<ColorInfo>) object;

        ArrayList<ColorInfo> colorList = getDefaultColorList();
        saveColor(colorList);
        return colorList;
    }

    @Override
    public ArrayList<SizeInfo> getSizeList() {
        Object object = mLocalModel.getPrivateObject("SizeList", false);
        if (object != null)
            return (ArrayList<SizeInfo>) object;

        ArrayList<SizeInfo> sizeList = getDefaultSizeList();
        saveSize(sizeList);
        return sizeList;
    }

    @Override
    public DateInfo getDate() {
        Object object = mLocalModel.getPrivateObject("DateInfo", false);
        if (object != null)
            return (DateInfo) object;

        return null;
    }

    @Override
    public void resetSetting() {
        mLocalModel.removePrivateObject("SizeList");
        mLocalModel.removePrivateObject("ColorList");
        mLocalModel.removePrivateObject("TypeList");
        mLocalModel.removePrivateObject("ExchangeRate");
        mLocalModel.removePrivateObject("DateInfo");
        mLocalModel.removePrivateObject("SettingProfile");
        mLocalModel.removePrivateObject("PostList");
    }

    private ArrayList<PostBlock> getDefaultPostList() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        ArrayList<PostBlock> postList = new ArrayList<>();
        postList.add(new PostBlock(false, "<3 " + (calendar.get(Calendar.MONTH) + 1) + "月官網連線新款相簿：\n" + "https://reurl.cc/"));
        postList.add(new PostBlock(true, ""));
        postList.add(new PostBlock(false, "<3 " + (calendar.get(Calendar.MONTH) + 1) + "月連線優惠活動：\n" + "https://reurl.cc/"));
        postList.add(new PostBlock(false, "<3 " + (calendar.get(Calendar.MONTH) + 1) + "月直播抽獎活動：\n" + "https://reurl.cc/"));

        return postList;
    }

    private ArrayList<TypeInfo> getDefaultTypeList() {
        ArrayList<TypeInfo> typeList = new ArrayList<>();
        ArrayList<String> column = new ArrayList<>();
        column.add("全長");
        column.add("肩寬");
        column.add("胸寬");
        column.add("腰寬");
        column.add("下擺寬");
        column.add("袖長");
        column.add("");
        column.add("");
        typeList.add(new TypeInfo("上衣", column));
        column.clear();
        column.add("全長");
        column.add("腰寬");
        column.add("臀寬");
        column.add("腿寬");
        column.add("褲口寬");
        column.add("褲襠");
        column.add("");
        column.add("");
        typeList.add(new TypeInfo("褲子", column));
        column.clear();
        column.add("全長");
        column.add("肩寬");
        column.add("胸寬");
        column.add("腰寬");
        column.add("臀寬");
        column.add("下擺寬");
        column.add("袖長");
        column.add("");
        typeList.add(new TypeInfo("洋裝", column));
        column.clear();
        column.add("全長");
        column.add("肩寬");
        column.add("胸寬");
        column.add("腰寬");
        column.add("下擺寬");
        column.add("袖長");
        column.add("");
        column.add("");
        typeList.add(new TypeInfo("內外", column));
        column.clear();
        column.add("全長");
        column.add("腰寬");
        column.add("臀寬");
        column.add("下擺寬");
        column.add("");
        column.add("");
        column.add("");
        column.add("");
        typeList.add(new TypeInfo("裙子", column));
        column.clear();
        column.add("全長");
        column.add("腰寬");
        column.add("臀寬");
        column.add("腿寬");
        column.add("下擺寬");
        column.add("");
        column.add("");
        column.add("");
        typeList.add(new TypeInfo("褲裙", column));
        column.clear();
        column.add("全長");
        column.add("肩寬");
        column.add("胸寬");
        column.add("腰寬");
        column.add("臀寬");
        column.add("腿寬");
        column.add("褲口寬");
        column.add("袖長");
        typeList.add(new TypeInfo("連身褲", column));
        column.clear();
        column.add("全長");
        column.add("肩寬");
        column.add("胸寬");
        column.add("腰寬");
        column.add("下擺寬");
        column.add("袖長");
        column.add("");
        column.add("");
        typeList.add(new TypeInfo("套裙(上)", column));
        column.clear();
        column.add("全長");
        column.add("腰寬");
        column.add("臀寬");
        column.add("下擺寬");
        column.add("");
        column.add("");
        column.add("");
        column.add("");
        typeList.add(new TypeInfo("套裙(下)", column));
        column.clear();
        column.add("全長");
        column.add("肩寬");
        column.add("胸寬");
        column.add("腰寬");
        column.add("下擺寬");
        column.add("袖長");
        column.add("");
        column.add("");
        typeList.add(new TypeInfo("套褲(上)", column));
        column.clear();
        column.add("全長");
        column.add("腰寬");
        column.add("臀寬");
        column.add("腿寬");
        column.add("褲口寬");
        column.add("褲襠");
        column.add("");
        column.add("");
        typeList.add(new TypeInfo("套褲(下)", column));
        column.clear();
        column.add("前高");
        column.add("後高");
        column.add("筒寬");
        column.add("");
        column.add("");
        column.add("");
        column.add("");
        column.add("");
        typeList.add(new TypeInfo("鞋", column));
        column.clear();
        column.add("長");
        column.add("寬");
        column.add("高");
        column.add("背帶可拆與否");
        column.add("背帶可調與否");
        column.add("");
        column.add("");
        column.add("");
        typeList.add(new TypeInfo("包", column));
        column.clear();
        column.add("長");
        column.add("寬");
        column.add("高");
        column.add("帽圍可調與否");
        column.add("");
        column.add("");
        column.add("");
        column.add("");
        typeList.add(new TypeInfo("帽子", column));
        column.clear();
        column.add("");
        column.add("");
        column.add("");
        column.add("");
        column.add("");
        column.add("");
        column.add("");
        column.add("");
        typeList.add(new TypeInfo("其他", column));
        return typeList;
    }

    private ArrayList<ColorInfo> getDefaultColorList() {
        ArrayList<ColorInfo> colorList = new ArrayList<>();
        colorList.add(new ColorInfo("丈青", "NV"));
        colorList.add(new ColorInfo("黑", "KK"));
        colorList.add(new ColorInfo("白", "WW"));
        colorList.add(new ColorInfo("米白", "IV"));
        colorList.add(new ColorInfo("杏", "CC"));
        colorList.add(new ColorInfo("均", "XX"));
        colorList.add(new ColorInfo("灰", "HH"));
        colorList.add(new ColorInfo("紅", "RR"));
        colorList.add(new ColorInfo("粉", "RL"));
        colorList.add(new ColorInfo("深紅", "RD"));
        colorList.add(new ColorInfo("桃", "RT"));
        colorList.add(new ColorInfo("酒紅", "RW"));
        colorList.add(new ColorInfo("綠", "GG"));
        colorList.add(new ColorInfo("淺綠", "GL"));
        colorList.add(new ColorInfo("深綠", "GD"));
        colorList.add(new ColorInfo("軍綠", "GS"));
        colorList.add(new ColorInfo("墨綠", "GI"));
        colorList.add(new ColorInfo("湖水綠", "GP"));
        colorList.add(new ColorInfo("藍", "BB"));
        colorList.add(new ColorInfo("淺藍", "BL"));
        colorList.add(new ColorInfo("深藍", "BD"));
        colorList.add(new ColorInfo("藍綠", "BG"));
        colorList.add(new ColorInfo("水藍", "BS"));
        colorList.add(new ColorInfo("寶藍", "BT"));
        colorList.add(new ColorInfo("藍紫", "BP"));
        colorList.add(new ColorInfo("黃", "YY"));
        colorList.add(new ColorInfo("淺黃", "YL"));
        colorList.add(new ColorInfo("深黃", "YD"));
        colorList.add(new ColorInfo("鵝黃", "YC"));
        colorList.add(new ColorInfo("紫", "PP"));
        colorList.add(new ColorInfo("淺紫", "PL"));
        colorList.add(new ColorInfo("深紫", "PD"));
        colorList.add(new ColorInfo("粉紫", "PR"));
        colorList.add(new ColorInfo("淺杏", "CL"));
        colorList.add(new ColorInfo("深杏", "CD"));
        colorList.add(new ColorInfo("咖", "CF"));
        colorList.add(new ColorInfo("駝", "CM"));
        colorList.add(new ColorInfo("可可", "CO"));
        colorList.add(new ColorInfo("卡其", "CK"));
        colorList.add(new ColorInfo("橘", "OO"));
        colorList.add(new ColorInfo("淺橘", "OL"));
        colorList.add(new ColorInfo("深橘", "OD"));
        colorList.add(new ColorInfo("粉橘", "OR"));
        colorList.add(new ColorInfo("淺灰", "HL"));
        colorList.add(new ColorInfo("深灰", "HD"));
        colorList.add(new ColorInfo("灰藍", "HB"));
        colorList.add(new ColorInfo("灰綠", "HG"));
        colorList.add(new ColorInfo("金", "MM"));
        colorList.add(new ColorInfo("銀", "MS"));
        colorList.add(new ColorInfo("蜜桃", "SW"));
        colorList.add(new ColorInfo("條紋", "TW"));
        return colorList;
    }

    private ArrayList<SizeInfo> getDefaultSizeList() {
        ArrayList<SizeInfo> sizeList = new ArrayList<>();
        sizeList.add(new SizeInfo("XS", "XS"));
        sizeList.add(new SizeInfo("S", "S"));
        sizeList.add(new SizeInfo("M", "M"));
        sizeList.add(new SizeInfo("L", "L"));
        sizeList.add(new SizeInfo("XL", "XL"));
        sizeList.add(new SizeInfo("XXL", "XXL"));
        sizeList.add(new SizeInfo("24腰", "4Y"));
        sizeList.add(new SizeInfo("25腰", "5Y"));
        sizeList.add(new SizeInfo("26腰", "6Y"));
        sizeList.add(new SizeInfo("27腰", "7Y"));
        sizeList.add(new SizeInfo("28腰", "8Y"));
        sizeList.add(new SizeInfo("29腰", "9Y"));
        sizeList.add(new SizeInfo("30腰", "0Y"));
        sizeList.add(new SizeInfo("31腰", "1Y"));
        sizeList.add(new SizeInfo("32腰", "2Y"));
        sizeList.add(new SizeInfo("22.5", "225"));
        sizeList.add(new SizeInfo("23", "230"));
        sizeList.add(new SizeInfo("23.5", "235"));
        sizeList.add(new SizeInfo("24", "240"));
        sizeList.add(new SizeInfo("24.5", "245"));
        sizeList.add(new SizeInfo("25", "250"));
        sizeList.add(new SizeInfo("25.5", "255"));
        sizeList.add(new SizeInfo("2號", "22"));
        sizeList.add(new SizeInfo("3號", "33"));
        sizeList.add(new SizeInfo("4號", "44"));
        sizeList.add(new SizeInfo("5號", "55"));
        sizeList.add(new SizeInfo("6號", "66"));
        sizeList.add(new SizeInfo("7號", "77"));
        sizeList.add(new SizeInfo("8號", "88"));
        sizeList.add(new SizeInfo("9號", "99"));
        sizeList.add(new SizeInfo("10號", "10"));
        sizeList.add(new SizeInfo("11號", "11"));
        sizeList.add(new SizeInfo("12號", "12"));
        sizeList.add(new SizeInfo("A", "A"));
        sizeList.add(new SizeInfo("B", "B"));
        sizeList.add(new SizeInfo("C", "C"));
        sizeList.add(new SizeInfo("75A", "75A"));
        sizeList.add(new SizeInfo("75C", "75C"));
        return sizeList;
    }
}
