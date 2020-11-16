package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.client.presenter.PostStatusPresenter;

/**
 * An {@link AsyncTask} for retrieving followers for a user.
 */
public class PostStatusTask extends AsyncTask<PostStatusRequest, Void, PostStatusResponse> {

    private final PostStatusPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void postSuccessful(PostStatusResponse postStatusResponse);
        void postUnsuccessful(PostStatusResponse postStatusResponse);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve followers.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public PostStatusTask(PostStatusPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on the background thread to retrieve followers. This method is
     * invoked indirectly by calling {@link #execute(PostStatusRequest...)}.
     *
     * @param postStatusRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected PostStatusResponse doInBackground(PostStatusRequest... postStatusRequests) {

        PostStatusResponse response = null;

        try {
            response = presenter.getStatus(postStatusRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param postStatusResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(PostStatusResponse postStatusResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(postStatusResponse.isSuccess()) {
            observer.postSuccessful(postStatusResponse);
        } else {
            observer.postUnsuccessful(postStatusResponse);
        }
    }
}
