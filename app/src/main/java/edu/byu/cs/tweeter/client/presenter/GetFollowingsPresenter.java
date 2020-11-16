package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.GetFollowingsServiceProxy;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FollowingService;
import edu.byu.cs.tweeter.model.service.GetFollowingsService;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.GetFollowingsRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.GetFollowingsResponse;

/**
 * The presenter for the "following" functionality of the application.
 */
public class GetFollowingsPresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public GetFollowingsPresenter(View view) {
        this.view = view;
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees & followers.
     */
    public GetFollowingsResponse getFollowings(GetFollowingsRequest request) throws IOException, TweeterRemoteException {
        GetFollowingsService followingService = getFollowingsService();
        return followingService.getFollowings(request);
    }

    /**
     * Returns an instance of {@link GetFollowingsService}. Allows mocking of the GetFollowingsService class
     * for testing purposes. All usages of GetFollowingsService should get their GetFollowingsService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    GetFollowingsService getFollowingsService() {
        return new GetFollowingsServiceProxy();
    }
}
