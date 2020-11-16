package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a request to have the server return the followings
 * of a user
 */
public class GetFollowingsRequest {

    private final User user;
    /**
     * Creates an instance.
     *
     * @param user the {@link User} whose followings are to be returned.
     */
    public GetFollowingsRequest(User user) {
        this.user = user;
    }

    /**
     * Returns the user whose followings are to be returned by this request.
     *
     * @return the user.
     */
    public User getUser() {
        return user;
    }

}
