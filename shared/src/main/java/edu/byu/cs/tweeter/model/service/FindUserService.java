package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FindUserRequest;
import edu.byu.cs.tweeter.model.service.response.FindUserResponse;

/**
 * Contains the business logic for getting the user associated with a given alias.
 */
    public interface FindUserService {

    /**
     * Returns the user that the alias specified in the request points to. Uses information in
     * the request object to return the correct user object associated with the alias.
     *
     * @param request contains the data required to fulfill the request.
     * @return the user.
     */
    FindUserResponse getUser(FindUserRequest request) throws IOException, TweeterRemoteException;

}
