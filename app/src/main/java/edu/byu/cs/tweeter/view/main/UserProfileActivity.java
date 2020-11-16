package edu.byu.cs.tweeter.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.GetFollowingsRequest;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.GetFollowingsResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;
import edu.byu.cs.tweeter.presenter.FollowPresenter;
import edu.byu.cs.tweeter.presenter.GetFollowingsPresenter;
import edu.byu.cs.tweeter.presenter.UnfollowPresenter;
import edu.byu.cs.tweeter.view.LoginActivity;
import edu.byu.cs.tweeter.view.asyncTasks.FollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetFollowingsTask;
import edu.byu.cs.tweeter.view.asyncTasks.UnfollowTask;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The user profile activity for the application. Contains tabs for story, following, and followers.
 */
public class UserProfileActivity extends AppCompatActivity implements GetFollowingsTask.Observer, GetFollowingsPresenter.View, FollowPresenter.View, FollowTask.Observer, UnfollowPresenter.View, UnfollowTask.Observer {

    private static final String LOG_TAG = "UserProfileActivity";
    private static final String USER_KEY = "USER";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private GetFollowingsPresenter getFollowingsPresenter;
    private FollowPresenter followPresenter;
    private UnfollowPresenter unfollowPresenter;

    private User loggedInUser;
    private  User user;
    private AuthToken authToken;

    private int followeeCount, followerCount;

    private TextView userName, userAlias, followeeCountTV, followerCountTV;
    private ImageView userImageView;
    private ToggleButton followButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userName = findViewById(R.id.full_name);
        userAlias = findViewById(R.id.userAlias);
        userImageView = findViewById(R.id.userImage);

        followeeCountTV = findViewById(R.id.followeeCount);
        followerCountTV = findViewById(R.id.followerCount);

        loggedInUser = (User) getIntent().getSerializableExtra("LOGGED_IN_USER");
        user = (User) getIntent().getSerializableExtra(USER_KEY);
        authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        getFollowingsPresenter = new GetFollowingsPresenter(this);
        followPresenter = new FollowPresenter(this);
        unfollowPresenter = new UnfollowPresenter(this);

        userName.setText(user.getName());
        userAlias.setText(user.getAlias());
        System.out.println("\n \nStart of setting image:");
        user.setImageBytes(user.getImageUrl().getBytes());
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));
        System.out.println("End of setting image\n \n");

        //GET FOLLOWINGS
        GetFollowingsTask getFollowingsTask = new GetFollowingsTask(getFollowingsPresenter, this);
        GetFollowingsRequest request = new GetFollowingsRequest(user);
        getFollowingsTask.execute(request);

        followButton = (ToggleButton) findViewById(R.id.follow_button);

        FollowTask.Observer followTaskObserver = this;
        UnfollowTask.Observer unfollowTaskObserver = this;

        followButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UnfollowTask unfollowTask = new UnfollowTask(unfollowPresenter, unfollowTaskObserver);
                    UnfollowRequest unfollowRequest = new UnfollowRequest(user, loggedInUser);
                    unfollowTask.execute(unfollowRequest);
                } else {
                    FollowTask followTask = new FollowTask(followPresenter, followTaskObserver);
                    FollowRequest followRequest = new FollowRequest(user, loggedInUser);
                    followTask.execute(followRequest);
                }
            }
        });

        UserProfileSectionsPagerAdapter uProfSectionsPagerAdapter = new UserProfileSectionsPagerAdapter(this, getSupportFragmentManager(), user, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager_profile);
        viewPager.setAdapter(uProfSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs_profile);
        tabs.setupWithViewPager(viewPager);
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

        if (followers.contains(loggedInUser)) {
            followButton.setChecked(false);
        } else {
            followButton.setChecked(true);
        }

        followeeCount = followees.size();
        followerCount = followers.size();
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

    /**
     * The callback method that gets invoked for a successful follow.
     *
     * @param followResponse the response from the login request.
     */
    @Override
    public void followSuccessful(FollowResponse followResponse) {
        followerCountTV.setText(getString(R.string.followerCount, ++followerCount));
    }

    /**
     * The callback method that gets invoked for an unsuccessful follow. Displays a toast with a
     * message indicating why the follow failed.
     *
     * @param followResponse the response from the follow request.
     */
    @Override
    public void followUnsuccessful(FollowResponse followResponse) {
        Toast.makeText(this, "Failed to follow. " + followResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * A callback indicating that an exception was thrown in an asynchronous method called on the
     * presenter.
     *
     * @param exception the exception.
     */
    @Override
    public void handleFollowException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(this, "Failed to follow because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * The callback method that gets invoked for a successful unfollow.
     *
     * @param unfollowResponse the response from the login request.
     */
    @Override
    public void unfollowSuccessful(UnfollowResponse unfollowResponse) {
        followerCountTV.setText(getString(R.string.followerCount, --followerCount));
    }

    /**
     * The callback method that gets invoked for an unsuccessful unfollow. Displays a toast with a
     * message indicating why the unfollow failed.
     *
     * @param unfollowResponse the response from the unfollow request.
     */
    @Override
    public void unfollowUnsuccessful(UnfollowResponse unfollowResponse) {
        Toast.makeText(this, "Failed to unfollow. " + unfollowResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * A callback indicating that an exception was thrown in an asynchronous method called on the
     * presenter.
     *
     * @param exception the exception.
     */
    @Override
    public void handleUnfollowException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(this, "Failed to unfollow because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}