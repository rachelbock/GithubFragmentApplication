package edu.lclark.githubfragmentapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.fragments.UserFragment;
import edu.lclark.githubfragmentapplication.models.GithubUser;
import edu.lclark.githubfragmentapplication.models.TabAdapter;

public class TabbedActivity extends AppCompatActivity {

    @Bind(R.id.activity_tabbed_tablayout)
    TabLayout tabLayout;
    @Bind(R.id.activity_tabbed_viewpager)
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_tabbed_toolbar);
        setSupportActionBar(toolbar);

        ArrayList<GithubUser> followers = (ArrayList<GithubUser>) getIntent().getSerializableExtra(UserFragment.ARG_FOLLOWERS);
        GithubUser user = (GithubUser) getIntent().getParcelableExtra(UserFragment.ARG_USER);
        setTitle(getString((R.string.follower_list_title), user.getLogin()));

        FragmentStatePagerAdapter adapter = new TabAdapter(this, getSupportFragmentManager(), followers);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.activity_tabbed_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }


}
