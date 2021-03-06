package com.shahbazly_dev.makeitlouder;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shahbazly_dev.makeitlouder.Menu.Music_vk;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;


import co.mobiwise.library.ProgressLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txt;
    Toolbar toolbar;
    Button btn_open;
    DrawerLayout drawer;
    ProgressLayout progressLayout;
    NavigationView navigationView;
    SlidingUpPanelLayout slidingUpPanelLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getUserInfo();

        slidingUpPanelLayout.setTouchEnabled(false);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }
    public void init(){
        progressLayout = (ProgressLayout)findViewById(R.id.progressLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
    }

    public void onShow(View view) {
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }
    public void onCollapse(View view){
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }
    public void onStart1(View view){
        progressLayout.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_camera:
                fragmentClass = Music_vk.class;
                break;
            default:
                fragmentClass = Fragment_welcome.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void getUserInfo() {
        final VKRequest username = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                "first_name,last_name,photo_100"));
        username.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                VKApiUserFull user = ((VKList<VKApiUserFull>) response.parsedModel).get(0);

                //устанавливаем имя пользователя в TextView
                txt = (TextView) findViewById(R.id.txt_username);
                txt.setText(user.first_name + " " + user.last_name);
                ImageView MenuUserProfilePhoto = (ImageView) findViewById(R.id.MenuUserProfilePhoto);
                int avatarSize = getResources().getDimensionPixelSize(R.dimen.global_menu_avatar_size);
                String profilePhoto = user.photo_100;
                Context context = getApplicationContext();
                Picasso.with(context)
                        .load(profilePhoto)
                        .placeholder(R.drawable.img_circle_placeholder)
                        .resize(avatarSize, avatarSize)
                        .centerCrop()
                        .transform(new CircleTransformation())
                        .into(MenuUserProfilePhoto);
            }
        });
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}

