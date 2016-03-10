package edu.lclark.githubfragmentapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.lclark.githubfragmentapplication.NetworkAsyncTask;
import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.activities.MainActivity;
import edu.lclark.githubfragmentapplication.activities.TabbedActivity;
import edu.lclark.githubfragmentapplication.models.GithubUser;

/**
 * Created by ntille on 2/25/16.
 */
public class UserFragment extends Fragment implements NetworkAsyncTask.GithubListener {

    public static final String ARG_USER = "UserFragment.User";
    public static final String ARG_FOLLOWERS = "UserFragment.Followers";
    private GithubUser mUser;
    private UserListener mUserListener;
    protected NetworkAsyncTask asyncTask;

    @Bind(R.id.fragment_user_imageview)
    ImageView mImageView;
    @Bind(R.id.fragment_user_name_textview)
    TextView mNameTextView;


    public interface UserListener {
        void onUserFollowerButtonClicked(GithubUser user);
    }


    public static UserFragment newInstance(GithubUser user) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        ButterKnife.bind(this, rootView);

        mUser = getArguments().getParcelable(ARG_USER);
        assert mUser != null;
        getActivity().setTitle(mUser.getLogin());

        Picasso.with(getContext()).load(mUser.getAvatar_url()).fit().centerInside().into(mImageView);

        mNameTextView.setText(mUser.getLogin());

        mUserListener = (MainActivity) getActivity();

        return rootView;
    }



    @OnClick(R.id.fragment_user_user_button)
    public void onFollowerButtonClick() {
        mUserListener.onUserFollowerButtonClicked(mUser);
    }

    @OnClick(R.id.fragment_user_tabbed_followers_button)
    public void onTabbedFollowerButtonClick() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                asyncTask = new NetworkAsyncTask(this);
                asyncTask.execute(mUser.getLogin());
            }
            else {
                Toast.makeText(getActivity(), R.string.login_no_internet, Toast.LENGTH_LONG).show();
            }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (asyncTask != null && !asyncTask.isCancelled()) {
            asyncTask.cancel(true);
            asyncTask = null;
        }
    }

    @Override
    public void onGithubFollowersRetrieved(@Nullable ArrayList<GithubUser> followers) {

        Log.d("Tag", followers.toString());
        Intent intent = new Intent(getActivity(), TabbedActivity.class);
        intent.putExtra(ARG_USER, mUser);
        intent.putExtra(ARG_FOLLOWERS, followers);
        startActivity(intent);

    }


}

