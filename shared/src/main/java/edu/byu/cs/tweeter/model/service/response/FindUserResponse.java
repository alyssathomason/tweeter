package edu.byu.cs.tweeter.model.service.response;

import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.User;

/**
 * A paged response for a {@link edu.byu.cs.tweeter.model.service.request.FollowingRequest}.
 */
public class FindUserResponse extends Response {

    private User user;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public FindUserResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param user the user to be included in the result.
     */
    public FindUserResponse(User user) {
        super(true, null);
        this.user = user;
    }

    /**
     * Returns the user for the corresponding request.
     *
     * @return the user.
     */
    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        FindUserResponse that = (FindUserResponse) param;

        return (Objects.equals(user, that.user));
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
