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
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.presenter.RegisterPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.RegisterTask;
import edu.byu.cs.tweeter.view.main.MainActivity;

/**
 * The fragment that displays on the 'Followers' tab.
 */
public class RegisterFragment extends Fragment implements RegisterPresenter.View, RegisterTask.Observer {

    private static final String LOG_TAG = "FollowersFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private User user;
    private AuthToken authToken;
    private RegisterPresenter presenter;
    private Toast registerInToast;

    Button registerButton;
    EditText alias, password, firstName, lastName;

    /**
     * Creates an instance of the fragment and places the user and auth token in an arguments
     * bundle assigned to the fragment.
     *
     * @param user the logged in user.
     * @param authToken the auth token for this user's session.
     * @return the fragment.
     */
    public static RegisterFragment newInstance(User user, AuthToken authToken) {
        RegisterFragment fragment = new RegisterFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        presenter = new RegisterPresenter(this);

        Button registerButton = view.findViewById(R.id.register_button);

        firstName = view.findViewById(R.id.first_name_reg);
        lastName = view.findViewById(R.id.last_name_reg);
        alias = view.findViewById(R.id.username_reg);
        password = view.findViewById(R.id.password_reg);

//        alias.addTextChangedListener(textWatcher);
//        password.addTextChangedListener(textWatcher);
//        firstName.addTextChangedListener(textWatcher);
//        lastName.addTextChangedListener(textWatcher);

        registerButton.setOnClickListener(registerOnClick);

        return view;
    }

    private View.OnClickListener registerOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            registerInToast = Toast.makeText(getContext(), "Registering & Logging In", Toast.LENGTH_LONG);
            registerInToast.show();

            String aliasStr = alias.getText().toString();
            String passwordStr = password.getText().toString();
            String firstNameStr = firstName.getText().toString();
            String lastNameStr = lastName.getText().toString();

            RegisterRequest registerRequest = new RegisterRequest(firstNameStr, lastNameStr, aliasStr, passwordStr);
            RegisterTask registerTask = new RegisterTask(presenter, RegisterFragment.this);
            registerTask.execute(registerRequest);
        }
    };

    /**
     * The callback method that gets invoked for a successful register. Displays the MainActivity.
     *
     * @param registerResponse the response from the register request.
     */
    @Override
    public void registerSuccessful(RegisterResponse registerResponse) {
        Intent intent = new Intent(getContext(), MainActivity.class);

        intent.putExtra(MainActivity.CURRENT_USER_KEY, registerResponse.getUser());
        intent.putExtra(MainActivity.AUTH_TOKEN_KEY, registerResponse.getAuthToken());

        registerInToast.cancel();
        startActivity(intent);
    }

    /**
     * The callback method that gets invoked for an unsuccessful register. Displays a toast with a
     * message indicating why the register failed.
     *
     * @param registerResponse the response from the login request.
     */
    @Override
    public void registerUnsuccessful(RegisterResponse registerResponse) {
        Toast.makeText(getContext(), "Failed to login. " + registerResponse.getMessage(), Toast.LENGTH_LONG).show();
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
            registerButton.setEnabled(!alias.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
}
