package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.Follow;

/**
 * A response for a {@link edu.byu.cs.tweeter.model.service.request.FollowRequest}.
 */
public class FollowResponse extends Response {

    private Follow follow;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public FollowResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param follow the now follow relationship fulfilled by the request.
     */
    public FollowResponse(Follow follow) {
        super(true, null);
        this.follow = follow;
    }

    /**
     * Returns the follow relationship fulfilled.
     *
     * @return the follow.
     */
    public Follow getFollow() {
        return follow;
    }
}
