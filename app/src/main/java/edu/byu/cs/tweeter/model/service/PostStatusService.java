package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

/**
 * Contains the business logic to support the post status operation.
 */
public class PostStatusService {

    public PostStatusResponse postStatus(PostStatusRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        PostStatusResponse postStatusResponse = serverFacade.postStatus(request);

        if(postStatusResponse.isSuccess()) {
            //load login page
        }

        return postStatusResponse;
    }

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
