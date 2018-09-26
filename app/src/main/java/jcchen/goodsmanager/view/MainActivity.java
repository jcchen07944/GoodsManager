package jcchen.goodsmanager.view;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.view.fragment.PurchaseTypeDialogFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private FrameLayout content;
    private FloatingActionButton mFloatingActionButton;

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
        PurchaseTypeDialogFragment mPurchaseTypeDialogFragment = new PurchaseTypeDialogFragment();
        mPurchaseTypeDialogFragment.show(getFragmentManager(), "PurchaseTypeDialogFragment");
    }

    private void closeDialog() {

    }

}
