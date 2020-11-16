package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

/**
 * Contains the business logic for getting the users a user is story.
 */
    public interface StoryService {

    /**
     * Returns the story of the current user. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request. Gets the statuses from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the story.
     */
    public StoryResponse getStory(StoryRequest request) throws IOException, TweeterRemoteException;

}
