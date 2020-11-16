package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.GetFollowingsRequest;
import edu.byu.cs.tweeter.model.service.response.GetFollowingsResponse;
import edu.byu.cs.tweeter.client.presenter.GetFollowingsPresenter;

/**
 * An {@link AsyncTask} for retrieving followings for a user.
 */
public class GetFollowingsTask extends AsyncTask<GetFollowingsRequest, Void, GetFollowingsResponse> {

    private final GetFollowingsPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void followingsRetrieved(GetFollowingsResponse getFollowingsResponse);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve followings.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public GetFollowingsTask(GetFollowingsPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on the background thread to retrieve followings. This method is
     * invoked indirectly by calling {@link #execute(GetFollowingsRequest...)}.
     *
     * @param getFollowingsRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected GetFollowingsResponse doInBackground(GetFollowingsRequest... getFollowingsRequests) {

        GetFollowingsResponse response = null;

        try {
            response = presenter.getFollowings(getFollowingsRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param getFollowingsResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(GetFollowingsResponse getFollowingsResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.followingsRetrieved(getFollowingsResponse);
        }
    }
}
