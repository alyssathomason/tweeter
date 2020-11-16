package edu.byu.cs.tweeter.model.service.request;

/**
 * Contains all the information needed to make a login request.
 */
public class LogoutRequest {

    private final String alias;

    /**
     * Creates an instance.
     *
     * @param alias of the user to be logged out.
     */
    public LogoutRequest(String alias) {
        this.alias = alias;
    }

    /**
     * Returns the alias of the user to be logged out by this request.
     *
     * @return the user.
     */
    public String getAlias() {
        return alias;
    }
}
