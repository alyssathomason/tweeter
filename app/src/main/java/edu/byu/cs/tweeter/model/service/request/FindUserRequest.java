package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a request to have the server return the user from
 * their alias
 */
public class FindUserRequest {

    private final String alias;

    /**
     * Creates an instance.
     *
     * @param alias the String of the user to be returned
     */
    public FindUserRequest(String alias) {
        this.alias = alias;
    }

    /**
     * Returns the alias of the user to be returned by this request.
     *
     * @return the follower.
     */
    public String getAlias() {
        return alias;
    }
}
