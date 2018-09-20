package jcchen.goodsmanager.view.container;

/**
 * Created by JCChen on 2018/9/20.
 */

public interface Container {
    void init();
    void showItem(Object object);
    boolean onBackPressed();
}
