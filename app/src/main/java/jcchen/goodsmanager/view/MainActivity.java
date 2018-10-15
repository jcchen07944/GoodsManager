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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.view.fragment.ManageFragment;
import jcchen.goodsmanager.view.fragment.PurchaseFragment;
import jcchen.goodsmanager.view.fragment.TypeSelectDialogFragment;

public class MainActivity extends AppCompatActivity {

    public static final int ACTIONBAR_STATE_HOME = 0;
    public static final int ACTIONBAR_STATE_PURCHASE = 1;
    public static final int ACTIONBAR_STATE_SELECT_CARD = 2;
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
    private long firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        firstTime = System.currentTimeMillis();
    }

    private void initUI() {

        /* Init Toolbar */
        ACTIONBAR_STATE = ACTIONBAR_STATE_HOME;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.manage));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        switch(ACTIONBAR_STATE) {
            case ACTIONBAR_STATE_HOME:
                menu.findItem(R.id.menu_upload).setVisible(true);
                menu.findItem(R.id.menu_delete).setVisible(false);
                menu.findItem(R.id.menu_po).setVisible(false);
                menu.findItem(R.id.menu_edit).setVisible(false);
                break;
            case ACTIONBAR_STATE_PURCHASE:
                menu.findItem(R.id.menu_upload).setVisible(false);
                menu.findItem(R.id.menu_delete).setVisible(false);
                menu.findItem(R.id.menu_po).setVisible(false);
                menu.findItem(R.id.menu_edit).setVisible(false);
                break;
            case ACTIONBAR_STATE_SELECT_CARD:
                menu.findItem(R.id.menu_upload).setVisible(false);
                menu.findItem(R.id.menu_delete).setVisible(true);
                menu.findItem(R.id.menu_po).setVisible(true);
                menu.findItem(R.id.menu_edit).setVisible(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (ACTIONBAR_STATE == ACTIONBAR_STATE_HOME)
                    mDrawerLayout.openDrawer(GravityCompat.START);
                else if (ACTIONBAR_STATE == ACTIONBAR_STATE_PURCHASE)
                    onBackPressed();
                else if (ACTIONBAR_STATE == ACTIONBAR_STATE_SELECT_CARD)
                    onBackPressed();
                    return true;
            case R.id.menu_delete:
                mManageFragment.removeSelectedCard();
                setActionbarState(ACTIONBAR_STATE_HOME);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (ACTIONBAR_STATE == ACTIONBAR_STATE_PURCHASE) {
            int BackStackCount = getSupportFragmentManager().getBackStackEntryCount();
            if (BackStackCount > 0) {
                String Name = getSupportFragmentManager().getBackStackEntryAt(BackStackCount - 1).getName();
                if (Name.equals(PurchaseFragment.TAG)) {
                    getSupportFragmentManager().popBackStack();
                    onPurchaseEnd();
                    return;
                }
            }
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(this, "再按一次返回鍵離開", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return;
            }
            finish();
        }
        else if (ACTIONBAR_STATE == ACTIONBAR_STATE_SELECT_CARD) {
            mManageFragment.onBackPressed();
        }
    }

    public void setActionbarState(int state) {
        ACTIONBAR_STATE = state;
        switch(ACTIONBAR_STATE) {
            case ACTIONBAR_STATE_HOME:
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mToolbar.setNavigationIcon(R.drawable.ic_menu);
                break;
            case ACTIONBAR_STATE_PURCHASE:
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
                mToolbar.setNavigationIcon(R.drawable.ic_back);
                break;
            case ACTIONBAR_STATE_SELECT_CARD:
                mToolbar.setNavigationIcon(R.drawable.ic_back);
                break;
        }
        invalidateOptionsMenu();
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

        setActionbarState(ACTIONBAR_STATE_PURCHASE);
    }

    public void onPurchaseEnd() {
        if(mManageFragment == null)
            mManageFragment = new ManageFragment();
        mFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.activity_main_content,
                mManageFragment, ManageFragment.TAG).commit();

        mToolbar.setTitle(getResources().getString(R.string.manage));

        mFloatingActionButton.setVisibility(View.VISIBLE);
        // Set StatusBar color.
        if (mWindow == null)
            mWindow = getWindow();
        mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mWindow.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        setActionbarState(ACTIONBAR_STATE_HOME);
    }

    public void onCardSelectStart() {
        setActionbarState(ACTIONBAR_STATE_SELECT_CARD);
    }

    public void onCardSelectEnd() {
        setActionbarState(ACTIONBAR_STATE_HOME);
    }

    public static int safeParseInt(String str) {
        return str.equals("") ? 0 : Integer.parseInt(str);
    }

    public static byte[] toByteArray(Object obj) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
        return bytes;
    }

    public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (ois != null) {
                ois.close();
            }
        }
        return obj;
    }


}
