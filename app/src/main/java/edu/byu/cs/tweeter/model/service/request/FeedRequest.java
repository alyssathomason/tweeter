package edu.byu.cs.tweeter.model.service.request;

import java.util.ArrayList;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a request to have the server return the feed of the
 * current user
 */
public class FeedRequest {

    private User currUser;
    private int limit;
    private Status lastStatus;

    /**
     * Creates an instance.
     *
     * @param currUser the {@link User} whose feed is to be returned
     * @param limit the limit of statuses to return
     * @param lastStatus the last status that was returned in the previous request (null if
     *                     there was no previous request or if no statuses were returned in the
     *                     previous request).
     */
    public FeedRequest(User currUser, int limit, Status lastStatus) {
        this.currUser = currUser;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    /**
     * Returns the user whose feed is to be returned by this request.
     *
     * @return the user.
     */
    public User getCurrUser() {
        return currUser;
    }

    /**
     * Returns the number representing the maximum number of statuses to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Returns the last status that was returned in the previous request or null if there was no
     * previous request or if no statuses were returned in the previous request.
     *
     * @return the last status.
     */
    public Status getLastStatus() {
        return lastStatus;
    }
}
