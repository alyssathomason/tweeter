package edu.byu.cs.tweeter.model.service.response;

import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * A paged response for a {@link edu.byu.cs.tweeter.model.service.request.PostStatusRequest}.
 */
public class PostStatusResponse extends Response {

    private Status status;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public PostStatusResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param status the user to be included in the result.
     */
    public PostStatusResponse(Status status) {
        super(true, null);
        this.status = status;
    }

    /**
     * Returns the user for the corresponding request.
     *
     * @return the status.
     */
    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        PostStatusResponse that = (PostStatusResponse) param;

        return (Objects.equals(status, that.status));
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }
}
