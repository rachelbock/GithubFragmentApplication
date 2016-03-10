package edu.lclark.githubfragmentapplication.models;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import edu.lclark.githubfragmentapplication.fragments.TabFragment;

/**
 * Created by rage on 3/9/16.
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    private final Context context;
    protected ArrayList<GithubUser> followers;

    public TabAdapter(Context c, FragmentManager fm, ArrayList<GithubUser> listFollowers) {
        super(fm);
        context = c;
        followers = listFollowers;
    }

    @Override
    public Fragment getItem(int position) {
        return TabFragment.newInstance(followers.get(position));
    }

    @Override
    public int getCount() {
        return followers.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = followers.get(position).getLogin();
        return title;
    }
}
