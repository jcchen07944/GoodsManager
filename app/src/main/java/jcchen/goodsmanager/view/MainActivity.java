package jcchen.goodsmanager.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.view.fragment.ManageFragment;
import jcchen.goodsmanager.view.fragment.PurchaseFragment;
import jcchen.goodsmanager.view.fragment.SelectTypeDialogFragment;

public class MainActivity extends AppCompatActivity {

    public static final int TOOLBAR_ANIMATION_STATE_PURCHASE = 0;
    public static final int TOOLBAR_ANIMATION_STATE_RESUME = 1;


    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private FrameLayout content;
    private FloatingActionButton mFloatingActionButton;

    private SelectTypeDialogFragment mSelectTypeDialogFragment = null;
    private PurchaseFragment mPurchaseFragment = null;
    private ManageFragment mManageFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {

        /* Init Toolbar */
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        mToolbar.setSubtitle("");
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        /* Init Drawer */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        /* Init Content */
        content = (FrameLayout) findViewById(R.id.activity_main_content);
        mManageFragment = new ManageFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_content, mManageFragment).commit();

        /* Init FloatingActionButton */
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setElevation(6 * getResources().getDisplayMetrics().density);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDialog() {
        /*final float OldY = mFloatingActionButton.getY();

        ValueAnimator moveFab_VA = ValueAnimator.ofFloat(mFloatingActionButton.getY(),
                                        mFloatingActionButton.getY() - (content.getHeight() / 2));
        moveFab_VA.setDuration(300);
        moveFab_VA.setInterpolator(new AccelerateInterpolator(0.6f));
        moveFab_VA.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFloatingActionButton.setY((float) animation.getAnimatedValue());
            }
        });
        moveFab_VA.start();*/
        if(mSelectTypeDialogFragment == null)
            mSelectTypeDialogFragment = new SelectTypeDialogFragment();
        mSelectTypeDialogFragment.show(getFragmentManager(), "SelectTypeDialogFragment");
    }

    public void onStartPurchase() {
        mSelectTypeDialogFragment.dismiss();

        if(mPurchaseFragment == null)
            mPurchaseFragment = new PurchaseFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_content,
                mPurchaseFragment, mPurchaseFragment.getClass().getName()).commit();
    }

    public void onPurchaseAnimationEnd(int STATE) {
        switch(STATE) {
            case TOOLBAR_ANIMATION_STATE_PURCHASE:
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
                break;
            case TOOLBAR_ANIMATION_STATE_RESUME:
                break;
        }
    }

    public void onEndPurchase() {
        if(mManageFragment == null)
            mManageFragment = new ManageFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_content,
                mManageFragment, mManageFragment.getClass().getName()).commit();
    }

}
