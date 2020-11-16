package edu.byu.cs.tweeter.client.view.main;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
<<<<<<< Updated upstream:app/src/main/java/edu/byu/cs/tweeter/client/view/main/MainActivity.java
import edu.byu.cs.tweeter.client.view.util.ImageUtils;
=======
import edu.byu.cs.tweeter.model.service.request.GetFollowingsRequest;
import edu.byu.cs.tweeter.model.service.response.GetFollowingsResponse;
import edu.byu.cs.tweeter.presenter.GetFollowingsPresenter;
import edu.byu.cs.tweeter.view.LoginActivity;
import edu.byu.cs.tweeter.view.asyncTasks.GetFollowingsTask;
import edu.byu.cs.tweeter.view.util.ImageUtils;
>>>>>>> Stashed changes:app/src/main/java/edu/byu/cs/tweeter/view/main/MainActivity.java

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends AppCompatActivity implements GetFollowingsPresenter.View, GetFollowingsTask.Observer {

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    GetFollowingsPresenter getFollowingsPresenter;

    TextView followeeCountTV, followerCountTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if(user == null) {
            throw new RuntimeException("User not passed to activity");
        }

        AuthToken authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), user, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);

        //GET FOLLOWING COUNTS
        getFollowingsPresenter = new GetFollowingsPresenter(this);

        GetFollowingsTask getFollowingsTask = new GetFollowingsTask(getFollowingsPresenter, this);
        GetFollowingsRequest request = new GetFollowingsRequest(user);
        getFollowingsTask.execute(request);

        TextView userName = findViewById(R.id.full_name);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));

        followeeCountTV = findViewById(R.id.followeeCount);
        followerCountTV = findViewById(R.id.followerCount);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PostStatusActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem logoutButton = menu.findItem(R.id.logout_button);
        logoutButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                intent.setAction("LOGOUT");
                startActivity(intent);
                return true;
            }
        });

        return true;
    }

    /**
     * A callback indicating more following data has been received. Loads the new followings
     * and removes the loading footer.
     *
     * @param getFollowingsResponse the asynchronous response to the request to load more items.
     */
    @Override
    public void followingsRetrieved(GetFollowingsResponse getFollowingsResponse) {
        List<User> followees = getFollowingsResponse.getFollowees();
        List<User> followers = getFollowingsResponse.getFollowers();
        int followeeCount = followees.size();
        int followerCount = followers.size();
        followeeCountTV.setText(getString(R.string.followeeCount, followeeCount));
        followerCountTV.setText(getString(R.string.followerCount, followerCount));
    }

    /**
     * A callback indicating that an exception was thrown by the presenter.
     *
     * @param exception the exception.
     */
    @Override
    public void handleException(Exception exception) {
        Log.e("", exception.getMessage(), exception);
        Toast.makeText(getBaseContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
    }


}