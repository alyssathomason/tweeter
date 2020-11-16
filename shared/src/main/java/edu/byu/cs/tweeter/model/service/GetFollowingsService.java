package edu.byu.cs.tweeter.model.service;

import java.io.IOException;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.GetFollowingsRequest;
import edu.byu.cs.tweeter.model.service.response.GetFollowingsResponse;

/**
 * Contains the business logic for getting the users a user is following.
 */
    public interface GetFollowingsService {

    /**
     * Returns the users that the user specified in the request is following and that are following them.
     * Gets the followees & followers from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees and followers.
     */
    GetFollowingsResponse getFollowings(GetFollowingsRequest request) throws IOException, TweeterRemoteException;

}
