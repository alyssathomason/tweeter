package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FindUserRequest;
import edu.byu.cs.tweeter.model.service.response.FindUserResponse;

/**
 * Contains the business logic for getting the user associated with a given alias.
 */
    public class FindUserService {

    /**
     * Returns the user that the alias specified in the request points to. Uses information in
     * the request object to return the correct user object associated with the alias.
     *
     * @param request contains the data required to fulfill the request.
     * @return the user.
     */
    public FindUserResponse getUser(FindUserRequest request) throws IOException {
        FindUserResponse response = getServerFacade().getUser(request);

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
