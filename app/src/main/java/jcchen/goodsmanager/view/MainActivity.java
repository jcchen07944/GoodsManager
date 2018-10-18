package jcchen.goodsmanager.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.view.fragment.ManageFragment;
import jcchen.goodsmanager.view.fragment.PostDialogFragment;
import jcchen.goodsmanager.view.fragment.PurchaseFragment;
import jcchen.goodsmanager.view.fragment.TypeSelectDialogFragment;

public class MainActivity extends AppCompatActivity {

    public static final int ACTIONBAR_STATE_HOME = 0;
    public static final int ACTIONBAR_STATE_PURCHASE = 1;
    public static final int ACTIONBAR_STATE_SELECT_CARD = 2;
    public int ACTIONBAR_STATE;

    private Context context;

    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private DrawerLayout mDrawerLayout;
    private FrameLayout content;
    private FloatingActionButton mFloatingActionButton;
    private NavigationView mNavigationView;
    private ImageView exchangeRate;
    private TextView exchangeRateText;

    private TypeSelectDialogFragment mTypeSelectDialogFragment = null;
    private PurchaseFragment mPurchaseFragment = null;
    private ManageFragment mManageFragment = null;
    private PostDialogFragment mPostDialogFragment = null;

    private FragmentManager mFragmentManager;
    private Window mWindow;
    private long firstTime;

    private PurchaseInfo selectedCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

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
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        exchangeRateText = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.drawer_ex_rate_text);
        exchangeRate = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.drawer_ex_rate);
        exchangeRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExchangeRateDialog();
            }
        });


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
                mManageFragment.onBackPressed();
                mPurchaseFragment.setMode(PurchaseFragment.MODE_ADD);
                openTypeDialog();
            }
        });

        /* Pre init purchase fragment */
        mPurchaseFragment = new PurchaseFragment();

        /* Pre init bottom sheet */
        mPostDialogFragment = new PostDialogFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        switch (ACTIONBAR_STATE) {
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
                new AlertDialog.Builder(this)
                        .setMessage(R.string.delete_confirm_message)
                        .setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mManageFragment.removeSelectedCard();
                                setActionbarState(ACTIONBAR_STATE_HOME);
                            }
                        })
                        .setNegativeButton(R.string.confirm_no, null)
                        .show();
                return true;
            case R.id.menu_po:
                mPostDialogFragment.show(getFragmentManager(), PostDialogFragment.TAG);
                return true;
            case R.id.menu_edit:
                mPurchaseFragment.setMode(PurchaseFragment.MODE_EDIT);
                mTypeSelectDialogFragment = new TypeSelectDialogFragment();
                mTypeSelectDialogFragment.setDefaultType(selectedCard.getTypeInfo());
                mTypeSelectDialogFragment.show(getFragmentManager(), TypeSelectDialogFragment.TAG);
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
        } else if (ACTIONBAR_STATE == ACTIONBAR_STATE_SELECT_CARD) {
            mManageFragment.onBackPressed();
        }
    }

    public void setActionbarState(int state) {
        ACTIONBAR_STATE = state;
        switch (ACTIONBAR_STATE) {
            case ACTIONBAR_STATE_HOME:
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mToolbar.setNavigationIcon(R.drawable.ic_menu);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;
            case ACTIONBAR_STATE_PURCHASE:
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
                mToolbar.setNavigationIcon(R.drawable.ic_back);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case ACTIONBAR_STATE_SELECT_CARD:
                mToolbar.setNavigationIcon(R.drawable.ic_back);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
        }
        invalidateOptionsMenu();
    }

    private void openTypeDialog() {
        mTypeSelectDialogFragment = new TypeSelectDialogFragment();
        mTypeSelectDialogFragment.show(getFragmentManager(), "TypeSelectDialogFragment");
    }

    private void openExchangeRateDialog() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(context);
        mAlertDialog.setTitle(R.string.exchange_rate_dialog_title);
        final EditText mEditText = new EditText(context);
        mEditText.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int) (100 * context.getResources().getDisplayMetrics().density),
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMarginStart((int) (20 * context.getResources().getDisplayMetrics().density));
        params.setMarginEnd((int) (20 * context.getResources().getDisplayMetrics().density));
        mEditText.setLayoutParams(params);
        mEditText.setText(exchangeRateText.getText());
        mAlertDialog.setView(mEditText);
        mAlertDialog.setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                exchangeRateText.setText(mEditText.getText());
                dialogInterface.cancel();
            }
        });
        mAlertDialog.setNegativeButton(R.string.confirm_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        mAlertDialog.show();
    }

    public void onPurchaseStart(TypeInfo selectedType) {
        mTypeSelectDialogFragment.dismiss();

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
        if (mManageFragment == null)
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

    public void onCardSelectStart(PurchaseInfo purchaseInfo) {
        setActionbarState(ACTIONBAR_STATE_SELECT_CARD);
        selectedCard = purchaseInfo;
    }

    public void onCardSelectEnd() {
        setActionbarState(ACTIONBAR_STATE_HOME);
        selectedCard = null;
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

    public PurchaseInfo getSelectedCard() {
        return selectedCard;
    }
}
