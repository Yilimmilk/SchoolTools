package com.icezx.tools.Utils;

import android.app.ActivityOptions;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.icezx.tools.Calculator.CalculatorActivity;
import com.icezx.tools.Console.ConsoleLoginActivity;
import com.icezx.tools.Homework.HomeworkActivity;
import com.icezx.tools.Logical.LogicalActivity;
import com.icezx.tools.MainActivity;
import com.icezx.tools.R;
import com.icezx.tools.Radix.RadixActivity;
import com.icezx.tools.Schedule.ScheduleActivity;
import com.icezx.tools.Translate.TranslateActivity;

public class NavigationSelectUtil extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    private Toolbar mToolbar;

    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;

    private boolean mToolbarInitialized;

    private int mItemToOpenWhenDrawerCloses = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Activity onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mToolbarInitialized) {
            throw new IllegalStateException("You must run super.initializeToolbar at " +
                    "the end of your onCreate method");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Whenever the fragment back stack changes, we may need to update the
        // action bar toggle: only top level screens show the hamburger-like icon, inner
        // screens - either Activities or fragments - show the "Up" icon instead.
        getFragmentManager().addOnBackStackChangedListener(backStackChangedListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        getFragmentManager().removeOnBackStackChangedListener(backStackChangedListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerToggle != null) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        // If the drawer is open, back will close it
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        // Otherwise, it may return to the previous fragment stack
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            // Lastly, it will rely on the system behavior for back
            super.onBackPressed();
        }
    }

    private final FragmentManager.OnBackStackChangedListener backStackChangedListener =
            new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    updateDrawerToggle();
                }
            };

    protected void updateDrawerToggle() {
        if (mDrawerToggle == null) {
            return;
        }
        boolean isRoot = getFragmentManager().getBackStackEntryCount() == 0;
        mDrawerToggle.setDrawerIndicatorEnabled(isRoot);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(!isRoot);
            getSupportActionBar().setDisplayHomeAsUpEnabled(!isRoot);
            getSupportActionBar().setHomeButtonEnabled(!isRoot);
        }
        if (isRoot) {
            mDrawerToggle.syncState();
        }
    }

    protected void initializeToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null) {
            throw new IllegalStateException("Layout is required to include a Toolbar with id " +
                    "'toolbar'");
        }

//        mToolbar.inflateMenu(R.menu.menu_default);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout != null) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            if (navigationView == null) {
                throw new IllegalStateException("Layout requires a NavigationView " +
                        "with id 'nav_view'");
            }

            // Create an ActionBarDrawerToggle that will handle opening/closing of the drawer:
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            mDrawerLayout.setDrawerListener(drawerListener);
            populateDrawerItems(navigationView);
            setSupportActionBar(mToolbar);
            updateDrawerToggle();
        } else {
            setSupportActionBar(mToolbar);
        }

        mToolbarInitialized = true;
    }

    private final DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            if (mDrawerToggle != null) mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            if (mDrawerToggle != null) mDrawerToggle.onDrawerOpened(drawerView);
            if (getSupportActionBar() != null) getSupportActionBar().setTitle(R.string.app_name);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            if (mDrawerToggle != null) mDrawerToggle.onDrawerClosed(drawerView);
            if (mItemToOpenWhenDrawerCloses >= 0) {
                Bundle extras = ActivityOptions.makeCustomAnimation(
                        NavigationSelectUtil.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                Class activityClass = null;
                switch (mItemToOpenWhenDrawerCloses) {
                    //在这里写activity
                    case R.id.nav_message:
                        activityClass = MainActivity.class;
                        break;

                    case R.id.nav_schedule:
                        activityClass = ScheduleActivity.class;
                        break;

                    case R.id.nav_homework:
                        activityClass = HomeworkActivity.class;
                        break;

                    case R.id.nav_calculator:
                        activityClass = CalculatorActivity.class;
                        break;

                    case R.id.nav_radix:
                        activityClass = RadixActivity.class;
                        break;

                    case R.id.nav_logical:
                        activityClass = LogicalActivity.class;
                        break;

                    case R.id.nav_console:
                        activityClass = ConsoleLoginActivity.class;
                        break;

                    case R.id.nav_translate:
                        activityClass = TranslateActivity.class;

                }
                if (activityClass != null) {
                    startActivity(new Intent(NavigationSelectUtil.this, activityClass), extras);
                    finish();
                }
            }
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            if (mDrawerToggle != null) mDrawerToggle.onDrawerStateChanged(newState);
        }
    };

    private void populateDrawerItems(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mItemToOpenWhenDrawerCloses = menuItem.getItemId();
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
        //这里也要写，同时在activity中引入navigationSelectActivity，同时在onCreate方法中加入initializeToolbar();
        if (MainActivity.class.isAssignableFrom(getClass())) {
            navigationView.setCheckedItem(R.id.nav_message);
        } else if (ScheduleActivity.class.isAssignableFrom(getClass())) {
            navigationView.setCheckedItem(R.id.nav_schedule);
        } else if (HomeworkActivity.class.isAssignableFrom(getClass())) {
            navigationView.setCheckedItem(R.id.nav_homework);
        } else if (CalculatorActivity.class.isAssignableFrom(getClass())) {
            navigationView.setCheckedItem(R.id.nav_calculator);
        } else if (RadixActivity.class.isAssignableFrom(getClass())) {
            navigationView.setCheckedItem(R.id.nav_radix);
        } else if (RadixActivity.class.isAssignableFrom(getClass())) {
            navigationView.setCheckedItem(R.id.nav_logical);
        } else if (ConsoleLoginActivity.class.isAssignableFrom(getClass())) {
            navigationView.setCheckedItem(R.id.nav_console);
        } else if (TranslateActivity.class.isAssignableFrom(getClass())) {
            navigationView.setCheckedItem(R.id.nav_translate);
        }
    }
}
