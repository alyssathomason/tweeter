package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.PostStatusServiceProxy;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FindUserService;
import edu.byu.cs.tweeter.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

/**
 * The presenter for the "status" functionality of the application.
 */
public class PostStatusPresenter {

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
    public PostStatusPresenter(View view) {
        this.view = view;
    }

    /**
     * Returns the status that the status specified in the request is followers. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followers.
     */
    public PostStatusResponse getStatus(PostStatusRequest request) throws IOException, TweeterRemoteException {
        PostStatusService postStatusService = getPostStatusService();
        return postStatusService.postStatus(request);
    }

    /**
     * Returns an instance of {@link PostStatusService}. Allows mocking of the PostStatusService class
     * for testing purposes.
     *
     * @return the instance.
     */
    PostStatusService getPostStatusService() { return new PostStatusServiceProxy(); }
}
