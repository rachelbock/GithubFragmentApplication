package edu.lclark.githubfragmentapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.models.GithubUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

    public static final String ARG_TABUSER = "TabFragment.User";
    public static final String TAG = TabFragment.class.getSimpleName();
    @Bind(R.id.fragment_tab_imageview)
    ImageView userImage;
    @Bind(R.id.fragment_tab_name_textview)
    TextView userName;

    private GithubUser githubUser;

    public TabFragment() {
        // Required empty public constructor
    }

    public static TabFragment newInstance(GithubUser user) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_TABUSER, user);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        ButterKnife.bind(this, rootView);

        githubUser = getArguments().getParcelable(ARG_TABUSER);

        if (githubUser == null) {
            Log.d(TAG, "github user is null");
        }
        else {
            userName.setText(githubUser.getLogin());
            Picasso.with(getContext()).load(githubUser.getAvatar_url()).fit().centerInside().into(userImage);

        }

        return rootView;

    }

}
