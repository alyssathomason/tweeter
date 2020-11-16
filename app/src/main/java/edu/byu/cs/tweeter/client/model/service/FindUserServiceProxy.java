package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FindUserService;
import edu.byu.cs.tweeter.model.service.request.FindUserRequest;
import edu.byu.cs.tweeter.model.service.response.FindUserResponse;

/**
 * Contains the business logic for getting the user associated with a given alias.
 */
public class FindUserServiceProxy implements FindUserService {

    static final String URL_PATH = "/finduser";

    /**
     * Returns the user that the alias specified in the request points to. Uses information in
     * the request object to return the correct user object associated with the alias.
     *
     * @param request contains the data required to fulfill the request.
     * @return the user.
     */
    public FindUserResponse getUser(FindUserRequest request) throws IOException, TweeterRemoteException {
        FindUserResponse response = getServerFacade().getUser(request, URL_PATH);

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
