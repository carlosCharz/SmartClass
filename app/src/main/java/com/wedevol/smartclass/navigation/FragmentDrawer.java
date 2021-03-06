package com.wedevol.smartclass.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wedevol.smartclass.R;
import com.wedevol.smartclass.models.User;
import com.wedevol.smartclass.utils.SharedPreferencesManager;
import com.wedevol.smartclass.utils.UtilMethods;
import com.wedevol.smartclass.utils.interfaces.Constants;

import java.util.ArrayList;
import java.util.List;

/** Created by paolo on 12/7/16.*/
public class FragmentDrawer extends Fragment {
    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private FragmentDrawerListener drawerListener;
    private View view;
    private SharedPreferencesManager mPreferencesManager;
    private boolean isInstructor;

    public FragmentDrawer() {

    }

    public static FragmentDrawer newInstance(boolean isInstructor) {
        FragmentDrawer fragmentDrawer = new FragmentDrawer();

        Bundle args = new Bundle();
        args.putBoolean("isInstructor", isInstructor);
        fragmentDrawer.setArguments(args);

        return fragmentDrawer;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferencesManager = SharedPreferencesManager.getInstance(getActivity());
        isInstructor = mPreferencesManager.getUserType();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nav_fragment_drawer, container, false);

        setupElements();
        return view;
    }

    public void setupElements(){
        TextView tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
        ImageView iv_user_image = (ImageView) view.findViewById(R.id.iv_user_image);
        User user = mPreferencesManager.getUserInfo();
        tv_user_name.setText(user.getReducedName(22));
        UtilMethods.setPhoto(getActivity(), iv_user_image, user.getProfilePictureUrl(),
                Constants.USER_PHOTO);

        recyclerView = (RecyclerView) view.findViewById(R.id.drawerList);
        setupRecyclerView();
    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    private List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();
        if(isInstructor){
            data.add(new NavDrawerItem(getString(R.string.nav_home), true, false, R.drawable.ic_desktop_black));
            data.add(new NavDrawerItem(getString(R.string.nav_item_profile), true, false, R.drawable.ic_profile_black));
            data.add(new NavDrawerItem(getString(R.string.nav_item_schedule), true, false, R.drawable.ic_schedule_black));
            data.add(new NavDrawerItem(getString(R.string.nav_item_notification), true, false, R.drawable.ic_notification_black));
            data.add(new NavDrawerItem(getString(R.string.nav_item_consultation), true, false, R.drawable.ic_counsel_black));
            data.add(new NavDrawerItem(getString(R.string.nav_item_course), true, false, R.drawable.ic_course_black));
            data.add(new NavDrawerItem(getString(R.string.nav_item_pay_course), true, false, R.drawable.ic_pay_course_black));
            data.add(new NavDrawerItem(getString(R.string.nav_item_bank_account), true, false, R.drawable.ic_bank_account_black));
            data.add(new NavDrawerItem(getString(R.string.nav_item_exit), true,false, R.drawable.ic_exit_black));
        }else{
            data.add(new NavDrawerItem(getString(R.string.nav_home), true, false, R.drawable.ic_locker_black));
            data.add(new NavDrawerItem(getString(R.string.nav_item_profile), true, false, R.drawable.ic_profile_black));
            data.add(new NavDrawerItem(getString(R.string.nav_item_course), true, false, R.drawable.ic_course_black));
            data.add(new NavDrawerItem(getString(R.string.nav_item_requests), true, false, R.drawable.ic_request_send_black));
            data.add(new NavDrawerItem(getString(R.string.nav_item_consultation), true, false, R.drawable.ic_counsel_black));
            data.add(new NavDrawerItem(getString(R.string.nav_item_pay_course), true, false, R.drawable.ic_pay_course_black));
            data.add(new NavDrawerItem(getString(R.string.nav_item_bank_account), true, false, R.drawable.ic_bank_account_black));
            data.add(new NavDrawerItem(getString(R.string.nav_item_exit), true,false, R.drawable.ic_exit_black));
        }

        return data;
    }

    private void setupRecyclerView(){
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(new NavigationDrawerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                drawerListener.onDrawerItemSelected(v, position);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public interface FragmentDrawerListener {
        void onDrawerItemSelected(View view, int position);
    }
}