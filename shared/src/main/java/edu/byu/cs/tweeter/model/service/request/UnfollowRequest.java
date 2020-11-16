package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a login request.
 */
public class UnfollowRequest {

    private final User followee;
    private final User follower;

    /**
     * Creates an instance.
     *
     * @param followee user being followed
     * @param follower user doing the following
     */
    public UnfollowRequest(User followee, User follower) {
        this.followee = followee;
        this.follower = follower;
    }

    /**
     * Returns the user to be unfollowed by this request
     *
     * @return the followee.
     */
    public User getFollowee() {
        return followee;
    }

    /**
     * Returns the user to unfollow the other user by this request.
     *
     * @return the follower.
     */
    public User getFollower() {
        return follower;
    }
}
