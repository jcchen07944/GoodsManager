package jcchen.goodsmanager.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
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
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.view.fragment.ManageFragment;
import jcchen.goodsmanager.view.fragment.PurchaseFragment;
import jcchen.goodsmanager.view.fragment.TypeSelectDialogFragment;

public class MainActivity extends AppCompatActivity {

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

    private FragmentManager mFragmentManager;
    private Window mWindow;


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
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.activity_main_content, mManageFragment).commit();

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

    @Override
    public void onBackPressed() {
        int BackStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if (BackStackCount <= 0)
            super.onBackPressed();
        String Name = getSupportFragmentManager().getBackStackEntryAt( BackStackCount - 1).getName();
        if(Name.equals(PurchaseFragment.TAG)) {
            getSupportFragmentManager().popBackStack();
            onPurchaseEnd();
        }
        else {
            super.onBackPressed();
        }
    }

    private void openDialog() {
        if(mTypeSelectDialogFragment == null)
            mTypeSelectDialogFragment = new TypeSelectDialogFragment();
        mTypeSelectDialogFragment.show(getFragmentManager(), "TypeSelectDialogFragment");
    }

    public void onPurchaseStart(TypeInfo selectedType) {
        mTypeSelectDialogFragment.dismiss();

        if(mPurchaseFragment == null)
            mPurchaseFragment = new PurchaseFragment();
        mPurchaseFragment.setSelectedType(selectedType);

        mFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.activity_main_content,
                mPurchaseFragment, PurchaseFragment.TAG).addToBackStack(PurchaseFragment.TAG).commit();

        mToolbar.setTitle(selectedType.getType());

        mFloatingActionButton.setVisibility(View.GONE);
        // Set StatusBar color.
        if (mWindow == null)
            mWindow = getWindow();
        mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mWindow.setStatusBarColor(ContextCompat.getColor(this, R.color.colorSecondaryDark));

        // ActionBar icon & color.
        ACTIONBAR_STATE = ACTIONBAR_STATE_BACK;
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
        mToolbar.setNavigationIcon(R.drawable.ic_back);
    }

    public void onPurchaseEnd() {
        if(mManageFragment == null)
            mManageFragment = new ManageFragment();
        mFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.activity_main_content,
                mManageFragment, ManageFragment.TAG).commit();

        mToolbar.setTitle(getResources().getString(R.string.app_name));

        mFloatingActionButton.setVisibility(View.VISIBLE);
        // Set StatusBar color.
        if (mWindow == null)
            mWindow = getWindow();
        mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mWindow.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        // ActionBar icon & color.
        ACTIONBAR_STATE = ACTIONBAR_STATE_HOME;
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
    }

}
