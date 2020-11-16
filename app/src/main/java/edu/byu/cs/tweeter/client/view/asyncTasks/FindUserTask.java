package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FindUserRequest;
import edu.byu.cs.tweeter.model.service.response.FindUserResponse;
import edu.byu.cs.tweeter.client.presenter.FindUserPresenter;

/**
 * An {@link AsyncTask} for retrieving a user from an alias.
 */
public class FindUserTask extends AsyncTask<FindUserRequest, Void, FindUserResponse> {

    private final FindUserPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void userRetrieved(FindUserResponse userResponse);
        void handleUserException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve the user.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public FindUserTask(FindUserPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on the background thread to retrieve a user. This method is
     * invoked indirectly by calling {@link #execute(FindUserRequest...)}.
     *
     * @param userRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected FindUserResponse doInBackground(FindUserRequest... userRequests) {

        FindUserResponse response = null;

        try {
            response = presenter.getUser(userRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param userResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(FindUserResponse userResponse) {
        if(exception != null) {
            observer.handleUserException(exception);
        } else {
            observer.userRetrieved(userResponse);
        }
    }
}
