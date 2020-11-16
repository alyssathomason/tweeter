package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.util.ByteArrayUtils;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.GetFollowingsService;
import edu.byu.cs.tweeter.model.service.request.GetFollowingsRequest;
import edu.byu.cs.tweeter.model.service.response.GetFollowingsResponse;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class GetFollowingsServiceProxy implements GetFollowingsService {

    static final String URL_PATH = "/getfollowings";

    /**
     * Returns the users that the user specified in the request is following and that are following them.
     * Uses the {@link ServerFacade} to get the followees & followers from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees and followers.
     */
    @Override
    public GetFollowingsResponse getFollowings(GetFollowingsRequest request) throws IOException, TweeterRemoteException {
        GetFollowingsResponse response = getServerFacade().getFollowings(request, URL_PATH);
        return response;
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
