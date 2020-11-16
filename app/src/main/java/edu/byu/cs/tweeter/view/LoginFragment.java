package edu.byu.cs.tweeter.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LoginTask;
import edu.byu.cs.tweeter.view.main.MainActivity;

/**
 * The fragment that displays on the 'Followers' tab.
 */
public class LoginFragment extends Fragment implements LoginPresenter.View, LoginTask.Observer {

    private static final String LOG_TAG = "FollowersFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private LoginPresenter presenter;
    private Toast loginInToast;

    private EditText alias, password;
    private Button loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        presenter = new LoginPresenter(this);

        loginButton = view.findViewById(R.id.login_button);

        alias = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);

        alias.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

        loginButton.setOnClickListener(loginOnClick);


        return view;
    }

    private View.OnClickListener loginOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loginInToast = Toast.makeText(getContext(), "Logging In", Toast.LENGTH_LONG);
            loginInToast.show();

            String aliasStr = alias.getText().toString();
            String passwordStr = password.getText().toString();


            LoginRequest loginRequest = new LoginRequest(aliasStr, passwordStr);
            LoginTask loginTask = new LoginTask(presenter, LoginFragment.this);
            loginTask.execute(loginRequest);
        }
    };

    /**
     * The callback method that gets invoked for a successful login. Displays the MainActivity.
     *
     * @param loginResponse the response from the login request.
     */
    @Override
    public void loginSuccessful(LoginResponse loginResponse) {
        Intent intent = new Intent(getContext(), MainActivity.class);

        intent.putExtra(MainActivity.CURRENT_USER_KEY, loginResponse.getUser());
        intent.putExtra(MainActivity.AUTH_TOKEN_KEY, loginResponse.getAuthToken());

        loginInToast.cancel();
        startActivity(intent);
    }

    /**
     * The callback method that gets invoked for an unsuccessful login. Displays a toast with a
     * message indicating why the login failed.
     *
     * @param loginResponse the response from the login request.
     */
    @Override
    public void loginUnsuccessful(LoginResponse loginResponse) {
        Toast.makeText(getContext(), "Failed to login. " + loginResponse.getMessage(), Toast.LENGTH_LONG).show();
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
        Toast.makeText(getContext(), "Failed to login because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            loginButton.setEnabled(!alias.getText().toString().isEmpty() && !password.getText().toString().isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
}
