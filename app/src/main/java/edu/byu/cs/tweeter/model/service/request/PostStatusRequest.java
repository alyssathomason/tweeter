package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a request to have the server return the user from
 * their alias
 */
public class PostStatusRequest {

    private final Status status;
//    private final User user;

    /**
     * Creates an instance.
     *
     * @param status the String of the user to be returned
     */
    public PostStatusRequest(Status status/**, User user*/) {
        this.status = status;
//        this.user = user;
    }

    /**
     * Returns the status be posted by this request.
     *
     * @return the follower.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Returns the user to post the status by this request.
     *
     * @return the user.
     */
//    public User getUser() {
//        return user;
//    }
}
