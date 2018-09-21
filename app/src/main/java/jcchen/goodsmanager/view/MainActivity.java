package jcchen.goodsmanager.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.view.fragment.ManageFragment;
import jcchen.goodsmanager.view.fragment.PurchaseFragment;
import jcchen.goodsmanager.view.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbar_title;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout content;
    private int width = -1;

    private Fragment[] contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initUI() {

        /* Init Toolbar */
        toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        toolbar_title.setText(getResources().getString(R.string.purchase));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /* Init Bottom Navigation */
        contents = new Fragment[3];
        contents[0] = new PurchaseFragment();
        contents[1] = new ManageFragment();
        contents[2] = new SettingFragment();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {
                    case R.id.bottom_navigation_purchase:
                        toolbar_title.setText(getResources().getString(R.string.purchase));
                        switchToFragment(contents[0]);
                        return true;
                    case R.id.bottom_navigation_manage:
                        toolbar_title.setText(getResources().getString(R.string.manage));
                        switchToFragment(contents[1]);
                        return true;
                    case R.id.bottom_navigation_setting:
                        toolbar_title.setText(getResources().getString(R.string.setting));
                        switchToFragment(contents[2]);
                        return true;
                }
                return false;
            }
        });
        content = (FrameLayout) findViewById(R.id.activity_main_content);
        content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                content.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                width = content.getWidth();
                switchToFragment(contents[0]);
            }
        });
    }

    private void switchToFragment(Fragment targetFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activity_main_content, targetFragment).commit();
    }

    public int getContentWidthPixel() {
        return width;
    }


}
