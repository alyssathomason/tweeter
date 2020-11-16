package edu.byu.cs.tweeter.model.domain;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a follow relationship.
 */
public class Story {

    private User user;
    private ArrayList<Status> statuses;

    public Story(User user, ArrayList<Status> statuses) {
        this.user = user;
        this.statuses = statuses;
    }

    public User getUser() { return user; }

    public ArrayList<Status> getStatuses() { return statuses; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Story that = (Story) o;
        return statuses.equals(that.statuses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statuses);
    }

    @NotNull
    @Override
    public String toString() {
        String result = "Story{\n";
        for (Status status : statuses) {
            result += status.toString() + "\n";
        }
        return result;
    }
}
