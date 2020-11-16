package edu.byu.cs.tweeter.client.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.client.presenter.PostStatusPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.PostStatusTask;

/**
 * Contains the minimum UI required to allow the user to post a hard coded status. Most or all
 * of this should be replaced when the back-end is implemented.
 */
public class PostStatusActivity extends AppCompatActivity implements PostStatusPresenter.View, PostStatusTask.Observer {

    private static final String LOG_TAG = "PostStatusActivity";

    private Toast postStatusToast;

    private PostStatusPresenter presenter;

    private User user;

    private TextView messageET;
    private Button postStatusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_status);

        presenter = new PostStatusPresenter(this);

        user = (User) getIntent().getSerializableExtra("USER");

        messageET = findViewById(R.id.status_message_input);
        postStatusButton = findViewById(R.id.post_status_button);

        postStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postStatusToast = Toast.makeText(getBaseContext(), "Posting status", Toast.LENGTH_LONG);
                postStatusToast.show();

                Status status = new Status(messageET.getText().toString(), user, Calendar.getInstance().getTime().toString());

                PostStatusRequest postStatusRequest = new PostStatusRequest(status);
                PostStatusTask postStatusTask = new PostStatusTask(presenter, PostStatusActivity.this);
                postStatusTask.execute(postStatusRequest);
            }
        });
    }

    /**
     * The callback method that gets invoked for a successful post status. Displays the MainActivity.
     *
     * @param postStatusResponse the response from the post status request.
     */
    @Override
    public void postSuccessful(PostStatusResponse postStatusResponse) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(MainActivity.CURRENT_USER_KEY, user);

        postStatusToast.cancel();
        startActivity(intent);
    }

    /**
     * The callback method that gets invoked for an unsuccessful status post. Displays a toast with a
     * message indicating why the status post failed.
     *
     * @param postStatusResponse the response from the post status request.
     */
    @Override
    public void postUnsuccessful(PostStatusResponse postStatusResponse) {
        Toast.makeText(this, "Failed to post status. " + postStatusResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * A callback indicating that an exception was thrown in an asynchronous method called on the
     * presenter.
     *
     * @param exception the exception.
     */
    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(this, "Failed to post status because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
