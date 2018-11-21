package jcchen.goodsmanager.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.DateInfo;
import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.entity.SettingProfile;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.presenter.impl.PurchasePresenterImpl;
import jcchen.goodsmanager.presenter.impl.SettingPresenterImpl;
import jcchen.goodsmanager.view.fragment.DateSelectDialogFragment;
import jcchen.goodsmanager.view.fragment.ManageFragment;
import jcchen.goodsmanager.view.fragment.PostDialogFragment;
import jcchen.goodsmanager.view.fragment.PurchaseFragment;
import jcchen.goodsmanager.view.fragment.SettingDialogFragment;
import jcchen.goodsmanager.view.fragment.TypeSelectDialogFragment;
import jcchen.goodsmanager.view.listener.OnPurchaseInfoUploadListener;

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
    private TextView exchangeRateText, dateText, dayText;
    private LinearLayout dateLayout;
    private MaterialSearchView mMaterialSearchView;

    private TypeSelectDialogFragment mTypeSelectDialogFragment = null;
    private PurchaseFragment mPurchaseFragment = null;
    private ManageFragment mManageFragment = null;
    private PostDialogFragment mPostDialogFragment = null;
    private SettingDialogFragment mSettingDialogFragment = null;

    private SettingProfile mSettingProfile;

    private FragmentManager mFragmentManager;
    private Window mWindow;
    private long firstTime;

    private PurchaseInfo selectedCard;

    private SettingPresenterImpl mSettingPresenter;
    private PurchasePresenterImpl mPurchasePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mPurchasePresenter = new PurchasePresenterImpl(this);
        mSettingPresenter = new SettingPresenterImpl(this);
        mSettingProfile = mSettingPresenter.getProfile();

        initUI();

        firstTime = System.currentTimeMillis() - 2000;
    }

    private void initUI() {

        /* Init Toolbar */
        ACTIONBAR_STATE = ACTIONBAR_STATE_HOME;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.manage));
        mToolbar.setSubtitle("");
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        mMaterialSearchView = (MaterialSearchView) findViewById(R.id.toolbar_search);
        mMaterialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                // Hide input window.
                if (imm != null)
                    imm.hideSoftInputFromWindow(mMaterialSearchView.getWindowToken(), 0);
                mMaterialSearchView.clearFocus();
                mMaterialSearchView.setVisibility(View.GONE);
                mToolbar.getMenu().findItem(R.id.menu_resume).setVisible(true);
                mToolbar.getMenu().findItem(R.id.menu_search).setVisible(false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mManageFragment.getAdapter().getFilter().filter(newText);
                return false;
            }
        });
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

        /* Init Drawer */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_add:
                        openTypeDialog();
                        break;
                    case R.id.nav_setting:
                        mSettingDialogFragment.show(getFragmentManager(), SettingDialogFragment.TAG);
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        exchangeRateText = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.drawer_ex_rate_text);
        exchangeRateText.setText(FloatToString(mSettingPresenter.getExchangeRate()));
        exchangeRate = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.drawer_ex_rate);
        exchangeRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExchangeRateDialog();
            }
        });
        dateLayout = (LinearLayout) mNavigationView.getHeaderView(0).findViewById(R.id.drawer_date_layout);
        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateSelectDialogFragment mDateSelectDialogFragment = new DateSelectDialogFragment();
                mDateSelectDialogFragment.setCancelable(true);
                mDateSelectDialogFragment.show(getFragmentManager(), DateSelectDialogFragment.TAG);
            }
        });
        dateText = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.drawer_date_text);
        dayText = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.drawer_day_text);
        onDateSet();


        /* Init Content */
        content = (FrameLayout) findViewById(R.id.activity_main_content);
        mManageFragment = new ManageFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.activity_main_content, mManageFragment).commit();

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

        /* Pre init setting dialog */
        mSettingDialogFragment = new SettingDialogFragment();

        /* Date dialog display*/
        String timeStamp = new SimpleDateFormat("yyyy年MM月dd日").format(Calendar.getInstance().getTime());
        if ((mSettingProfile.getTimeDialogShowFreq() == SettingProfile.TIME_DIALOG_MODE_ONCE &&
                !mSettingProfile.getLastDialogShowTimeStamp().equals(timeStamp)) ||
                mSettingProfile.getTimeDialogShowFreq() == SettingProfile.TIME_DIALOG_MODE_EVERYDAY) {
            DateSelectDialogFragment mDateSelectDialogFragment = new DateSelectDialogFragment();
            mDateSelectDialogFragment.setCancelable(false);
            mDateSelectDialogFragment.show(getFragmentManager(), DateSelectDialogFragment.TAG);
            mSettingProfile.setLastDialogShowTimeStamp(timeStamp);
            mSettingPresenter.saveProfile(mSettingProfile);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        // mMaterialSearchView.setMenuItem(menu.findItem(R.id.menu_search));
        mMaterialSearchView.setVoiceSearch(false);
        switch (ACTIONBAR_STATE) {
            case ACTIONBAR_STATE_HOME:
                if (mManageFragment.getAdapter() != null) {
                    if (mManageFragment.getAdapter().isFiltered()) {
                        menu.findItem(R.id.menu_search).setVisible(false);
                        menu.findItem(R.id.menu_resume).setVisible(true);
                    }
                    else {
                        menu.findItem(R.id.menu_search).setVisible(true);
                        menu.findItem(R.id.menu_resume).setVisible(false);
                    }
                }
                menu.findItem(R.id.menu_upload).setVisible(true);
                menu.findItem(R.id.menu_delete).setVisible(false);
                menu.findItem(R.id.menu_po).setVisible(false);
                menu.findItem(R.id.menu_edit).setVisible(false);
                menu.findItem(R.id.menu_clear).setVisible(false);
                break;
            case ACTIONBAR_STATE_PURCHASE:
                menu.findItem(R.id.menu_search).setVisible(false);
                menu.findItem(R.id.menu_resume).setVisible(false);
                menu.findItem(R.id.menu_upload).setVisible(false);
                menu.findItem(R.id.menu_delete).setVisible(false);
                menu.findItem(R.id.menu_po).setVisible(false);
                menu.findItem(R.id.menu_edit).setVisible(false);
                menu.findItem(R.id.menu_clear).setVisible(true);
                break;
            case ACTIONBAR_STATE_SELECT_CARD:
                menu.findItem(R.id.menu_search).setVisible(false);
                menu.findItem(R.id.menu_resume).setVisible(false);
                menu.findItem(R.id.menu_upload).setVisible(false);
                menu.findItem(R.id.menu_delete).setVisible(true);
                menu.findItem(R.id.menu_po).setVisible(true);
                menu.findItem(R.id.menu_edit).setVisible(true);
                menu.findItem(R.id.menu_clear).setVisible(false);
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
            case R.id.menu_clear:
                mPurchaseFragment.clear();
                return true;
            case R.id.menu_upload:
                int padding = (int) (16 * context.getResources().getDisplayMetrics().density);
                ProgressBar mProgressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleSmall);
                mProgressBar.setPadding(padding, padding, padding, padding);
                mProgressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(
                        this, android.R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                mToolbar.getMenu().findItem(R.id.menu_upload).setActionView(mProgressBar);
                mPurchasePresenter.uploadAllPurchaseInfo(new OnPurchaseInfoUploadListener() {
                    @Override
                    public void onUploadUpdate(PurchaseInfo mPurchaseInfo) {
                        mPurchaseInfo.setUpload(true);
                        mPurchasePresenter.updatePurchaseInfo(mPurchaseInfo, mPurchaseInfo);
                    }
                    @Override
                    public void onUploadEnd() {
                        mManageFragment.refresh();
                        item.getActionView().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                item.setActionView(null);
                            }
                        }, 1000);
                    }
                });
                return true;
            case R.id.menu_resume:
                mManageFragment.getAdapter().getFilter().filter("");
                mToolbar.getMenu().findItem(R.id.menu_search).setVisible(true);
                mToolbar.getMenu().findItem(R.id.menu_resume).setVisible(false);
                return true;
            case R.id.menu_search:
                mMaterialSearchView.closeSearch();
                mMaterialSearchView.setVisibility(View.VISIBLE);
                mMaterialSearchView.showSearch();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (ACTIONBAR_STATE == ACTIONBAR_STATE_PURCHASE) {
            int BackStackCount = mFragmentManager.getBackStackEntryCount();
            if (BackStackCount > 0) {
                String Name = mFragmentManager.getBackStackEntryAt(BackStackCount - 1).getName();
                if (Name.equals(PurchaseFragment.TAG)) {
                    mFragmentManager.popBackStack();
                    onPurchaseEnd();
                    return;
                }
            }
        } else if (ACTIONBAR_STATE == ACTIONBAR_STATE_SELECT_CARD) {
            mManageFragment.onBackPressed();
        } else if (ACTIONBAR_STATE == ACTIONBAR_STATE_HOME) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(this, "再按一次返回鍵離開", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return;
            }
            this.finishAffinity();
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
                mSettingPresenter.saveExchangeRate(Float.parseFloat(mEditText.getText().toString()));
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

    public void onDateSet() {
        DateInfo mDateInfo = mSettingPresenter.getDate();
        if (mDateInfo != null) {
            dateText.setText("日期 : " + mDateInfo.getDate());
            dayText.setText("第 " + mDateInfo.getDay() + " 天");
        }
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

    public static String FloatToString(float d) {
        if (d == (long) d)
            return String.format("%d", (long) d);
        else
            return String.format("%s", d);
    }

    public PurchaseInfo getSelectedCard() {
        return selectedCard;
    }
}
