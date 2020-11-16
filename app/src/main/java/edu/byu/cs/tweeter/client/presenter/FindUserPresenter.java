package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FindUserService;
import edu.byu.cs.tweeter.model.service.request.FindUserRequest;
import edu.byu.cs.tweeter.model.service.response.FindUserResponse;

/**
 * The presenter for the user functionality of the application.
 */
public class FindUserPresenter {

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
    public FindUserPresenter(View view) {
        this.view = view;
    }

    /**
     * Returns the user associated with the alias.
     *
     * @param request contains the data required to fulfill the request.
     * @return the feed.
     */
    public FindUserResponse getUser(FindUserRequest request) throws IOException {
        FindUserService findUserService = getUserService();
        return findUserService.getUser(request);
    }

    /**
     * Returns an instance of {@link FindUserService}. Allows mocking of the FollowingService class
     * for testing purposes. All usages of FollowingService should get their FollowingService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FindUserService getUserService() {
        return new FindUserService();
    }
}
