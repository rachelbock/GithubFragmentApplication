package edu.lclark.githubfragmentapplication.activities;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.lclark.githubfragmentapplication.LoginAsyncTask;
import edu.lclark.githubfragmentapplication.R;

public class LoginActivity extends AppCompatActivity implements LoginAsyncTask.gitHubLoginUserListener {

    public static final String EXTRA_USER = "extra_user";
    public int count = 0;
    @Bind(R.id.login_activity_user_name)
    EditText userName;
    @Bind(R.id.login_activity_login_button)
    Button signInButton;
    @Bind(R.id.login_activity_progress_bar)
    ProgressBar progressBar;

    LoginAsyncTask asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.login_activity_login_button)
    public void onButtonClick (Button button) {
        if (count > 2) {
            signInButton.setEnabled(false);
            Toast.makeText(this, R.string.login_invalid_attempts, Toast.LENGTH_LONG).show();
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            asyncTask = new LoginAsyncTask(this);
            asyncTask.execute(userName.getText().toString());
            progressBar.animate();
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            Toast.makeText(this, R.string.login_no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGitHubLoginUserRetrieved(JSONObject jsonObject) {
        progressBar.setVisibility(View.INVISIBLE);
        try {
            if (jsonObject == null) {
                Toast.makeText(this, R.string.login_invalid_username, Toast.LENGTH_SHORT).show();
                count ++;
            }
            else {
                String login = jsonObject.getString("login");
                if (userName.getText().toString().equals(login)) {
                    Toast.makeText(this, R.string.login_successful, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra(EXTRA_USER, userName.getText().toString());
                    startActivity(intent);
                    finish();
                }
                else {
                    count ++;
                    Toast.makeText(this, R.string.login_invalid_username, Toast.LENGTH_SHORT).show();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
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
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}

