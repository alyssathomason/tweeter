package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

/**
 * Contains the business logic to support the post status operation.
 */
public interface PostStatusService {

    PostStatusResponse postStatus(PostStatusRequest request) throws IOException, TweeterRemoteException;

}
