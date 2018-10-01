package jcchen.goodsmanager.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.view.fragment.ManageFragment;
import jcchen.goodsmanager.view.fragment.PurchaseFragment;
import jcchen.goodsmanager.view.fragment.TypeSelectDialogFragment;

public class MainActivity extends AppCompatActivity {

    public static final int TOOLBAR_ANIMATION_STATE_PURCHASE = 0;
    public static final int TOOLBAR_ANIMATION_STATE_RESUME = 1;

    public static final int ACTIONBAR_STATE_HOME = 0;
    public static final int ACTIONBAR_STATE_BACK = 1;
    public int ACTIONBAR_STATE;

    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private DrawerLayout mDrawerLayout;
    private FrameLayout content;
    private FloatingActionButton mFloatingActionButton;

    private TypeSelectDialogFragment mTypeSelectDialogFragment = null;
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
        ACTIONBAR_STATE = ACTIONBAR_STATE_HOME;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        mToolbar.setSubtitle("");
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

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
                if(ACTIONBAR_STATE == ACTIONBAR_STATE_HOME)
                    mDrawerLayout.openDrawer(GravityCompat.START);
                if(ACTIONBAR_STATE == ACTIONBAR_STATE_BACK)
                    onBackPressed();
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
        if(mTypeSelectDialogFragment == null)
            mTypeSelectDialogFragment = new TypeSelectDialogFragment();
        mTypeSelectDialogFragment.show(getFragmentManager(), "TypeSelectDialogFragment");
    }

    public void onStartPurchase() {
        mTypeSelectDialogFragment.dismiss();

        if(mPurchaseFragment == null)
            mPurchaseFragment = new PurchaseFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_content,
                mPurchaseFragment, mPurchaseFragment.getClass().getName()).commit();
    }

    public void onAnimationEnd(int STATE) {
        Window window = this.getWindow();
        switch(STATE) {
            case TOOLBAR_ANIMATION_STATE_PURCHASE:
                mFloatingActionButton.setVisibility(View.GONE);
                // Set StatusBar color.
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorSecondaryDark));

                // ActionBar icon & color.
                ACTIONBAR_STATE = ACTIONBAR_STATE_BACK;
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
                mToolbar.setNavigationIcon(R.drawable.ic_back);
                break;
            case TOOLBAR_ANIMATION_STATE_RESUME:
                mFloatingActionButton.setVisibility(View.VISIBLE);
                // Set StatusBar color.
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

                // ActionBar icon & color.
                ACTIONBAR_STATE = ACTIONBAR_STATE_HOME;
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mToolbar.setNavigationIcon(R.drawable.ic_menu);
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
