package edu.byu.cs.tweeter.model.service.response;

import java.util.ArrayList;
import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * A response for a {@link edu.byu.cs.tweeter.model.service.request.StoryRequest}.
 */
public class StoryResponse extends PagedResponse {

    private ArrayList<Status> statuses;
    private boolean hasMorePages;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful. Sets the
     * success and more pages indicators to false.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public StoryResponse(String message) {
        super(false, message, false);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param statuses the statuses to be included in the result.
     * @param hasMorePages an indicator of whether more data is available for the request.
     */
    public StoryResponse(ArrayList<Status> statuses, boolean hasMorePages) {
        super(true, hasMorePages);
        this.statuses = statuses;
        this.hasMorePages = hasMorePages;
    }

    /**
     * Returns the statuses for the corresponding request.
     *
     * @return the statuses.
     */
    public ArrayList<Status> getStatuses() {
        return statuses;
    }

    /**
     * Returns if the corresponding request has more pages or not.
     *
     * @return the hasMorePages.
     */
    public boolean hasMorePages() { return hasMorePages; }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        StoryResponse that = (StoryResponse) param;

        return (Objects.equals(statuses, that.statuses) && this.hasMorePages == that.hasMorePages &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(statuses, hasMorePages);
    }
}
