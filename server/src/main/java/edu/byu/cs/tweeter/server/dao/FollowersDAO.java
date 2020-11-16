package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.BuildConfig;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.FindUserRequest;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.FindUserResponse;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;

/**
 * A DAO for accessing 'followers' data from the database.
 */
public class FollowersDAO {
    // This is the hard coded follower data returned by the 'getFollowers()' method
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";
    private static final String SAA_AIM = "Our aim is to make the BYU experience well-rounded by connecting with and creating opportunities between students and alumni https://studentalumni.byu.edu";
    private static final String SAA_CONF = "We are looking forward to the inspiring messages we will get to hear this weekend from our global church leaders. What a great way to end the semester! Keep enduring to the end cougs! #ConnectedForGood https://alumni.byu.edu";
    private static final String SAA_LIGHT_THE_Y = "Take part in one of BYU's oldest traditions that has been happening during Homecoming since 1924! Light the Y by taking part in our daily service challenges and giveaways! https://studentalumni.byu.edu/lty";
    private static final String SAA_EWA = "Join us virtually November 5 from 6-7:30 p.m. for an evening to connect with experienced alumni and grow while discussing professional development topics. Become inspired and prepared to excel in professional settings! @BYUStudentAlumni";
    private static final String SAA_FOOD_DRIVE = "For more than 20 years, BYU Student Alumni has sponsored the largest food drive in Utah County. In 2012, BYU teamed up with Utah Valley University to collect food and monetary donations for those in our community living in hunger. We’re still doing it, so please participate!\n" +
            "\n" +
            "Throughout November, you can1) drop off non-perishable food in bins across campus,2) donate money at the BYU Store, Cougareat, or Creameries, or3) donate online or at various events happening across campus.";
    private static final String SAA_SUMMIT = "This two-day conference allows @BYUAlumni to share their inspiring journeys and teach students how to make an impact in the world. Join us on Friday and Saturday, February 12 and 13, to learn how to make the most of your BYU experience and gain leadership skills that will equip you to make a difference in the world. https://studentalumni.byu.edu/the-summit";
    private static final String SAA_SMTM = "Learn how to maximize the reach of your income from experts on topics like home buying, budgeting, and investing. https://studentalumni.byu.edu/smtm";
    private static final String SAA_CONNECT = "Take mentoring to a new level. With its ever-increasing database of BYU Alumni, @BYUConnect can help you expand your network, broaden your perspective and make vital career decisions.";

    private static final String[] STUDENT_ALUMNI_MESSAGES = { SAA_AIM, SAA_CONF, SAA_LIGHT_THE_Y, SAA_EWA, SAA_FOOD_DRIVE, SAA_SUMMIT, SAA_SMTM, SAA_CONNECT };

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);
    private final User studentAlumni = new User("BYU", "Student Alumni", "@BYUStudentAlumni", FEMALE_IMAGE_URL);
    private final User byuAlumni = new User("BYU", "Alumni", "@BYUAlumni", MALE_IMAGE_URL);

    private final User[] users = {user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18, user19, user20, studentAlumni, byuAlumni};
    Map<String, User> possibleUsers = new HashMap<>();

    private final Status status1 = new Status(STUDENT_ALUMNI_MESSAGES[(int)(Math.random() * 7)], user1, "September 10, 2019 7pm");
    private final Status status2 = new Status(STUDENT_ALUMNI_MESSAGES[(int)(Math.random() * 7)], user2, "September 9, 2019 7pm");
    private final Status status3 = new Status(STUDENT_ALUMNI_MESSAGES[(int)(Math.random() * 7)], user3, "September 8, 2019 7pm");
    private final Status status4 = new Status(STUDENT_ALUMNI_MESSAGES[(int)(Math.random() * 7)], user4, "September 7, 2019 7pm");
    private final Status status5 = new Status(STUDENT_ALUMNI_MESSAGES[(int)(Math.random() * 7)], user5, "September 6, 2019 7pm");
    private final Status status6 = new Status(STUDENT_ALUMNI_MESSAGES[(int)(Math.random() * 7)], user6, "January 20, 2016 10am");

    private ArrayList<Status> newStatuses = new ArrayList<>();

    /**
     * Gets the count of users from the database that the user specified is followers. The
     * current implementation uses generated data and doesn't actually access a database.
     *
     * @param follower the User whose count of how many followers is desired.
     * @return said count.
     */
    public Integer getFollowerCount(User follower) {
        // TODO: uses the dummy data.  Replace with a real implementation.
        assert follower != null;
        return getDummyFollowers().size();
    }

    /**
     * Returns the users that the user specified in the request is followers. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followers are to be returned and any
     *                other information required to satisfy the request.
     * @return the followers.
     */
    public FollowersResponse getFollowers(FollowersRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getLimit() > 0;
        assert request.getFollower() != null;

        List<User> allFollowers = getDummyFollowers();
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowers != null) {
                int followersIndex = getFollowersStartingIndex(request.getLastFollower(), allFollowers);

                for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                    responseFollowers.add(allFollowers.get(followersIndex));
                }

                hasMorePages = followersIndex < allFollowers.size();
            }
        }

        return new FollowersResponse(responseFollowers, hasMorePages);
    }

    /**
     * Returns the users that are followers the user specified. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followers are to be returned and any
     *                other information required to satisfy the request.
     * @return the followers response.
     */
    public FollowersResponse getFollowers(FollowersRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollower() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowers = getDummyFollowers();
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followersIndex = getFollowersStartingIndex(request.getLastFollower(), allFollowers);

            for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                responseFollowers.add(allFollowers.get(followersIndex));
            }

            hasMorePages = followersIndex < allFollowers.size();
        }

        return new FollowersResponse(responseFollowers, hasMorePages);
    }


    /**
     * Returns the feed of the user specified. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose feed is to be returned and any
     *                other information required to satisfy the request.
     * @return the feed response.
     */
    public FeedResponse getFeed(FeedRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getCurrUser() == null) {
                throw new AssertionError();
            }
        }

        ArrayList<Status> allStatuses = getDummyStatuses();
        ArrayList<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int statusesIndex = getFeedStartingIndex(request.getLastStatus(), allStatuses);

            for(int limitCounter = 0; statusesIndex < allStatuses.size() && limitCounter < request.getLimit(); statusesIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(statusesIndex));
            }

            hasMorePages = statusesIndex < allStatuses.size();
        }

        return new FeedResponse(responseStatuses, hasMorePages);
    }

    /**
     * Returns the story of the user specified. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose story is to be returned and any
     *                other information required to satisfy the request.
     * @return the story response.
     */
    public StoryResponse getStory(StoryRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getCurrUser() == null) {
                throw new AssertionError();
            }
        }

        ArrayList<Status> allStatuses = getDummyStoryStatuses(request.getCurrUser());
        ArrayList<Status> responseStatuses = new ArrayList<>(request.getLimit());

//        //TEMPORARY-- MAKING SURE DUMMY DATA ALL IS FROM THE RIGHT USER
//        for (Status status : allStatuses) {
//            status.poster = request.getCurrUser();
//        }

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int statusesIndex = getStoryStartingIndex(request.getLastStatus(), allStatuses);

            for(int limitCounter = 0; statusesIndex < allStatuses.size() && limitCounter < request.getLimit(); statusesIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(statusesIndex));
            }

            hasMorePages = statusesIndex < allStatuses.size();
        }

        return new StoryResponse(responseStatuses, hasMorePages);
    }

    /**
     * Returns the user of the alias specified. Uses information in
     * the request object to find the user in the database.
     *
     * @param request contains information about the user required to satisfy the request.
     * @return the user response.
     */
    public FindUserResponse getUser(FindUserRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getAlias().length() <= 0) {
                throw new AssertionError();
            }
        }

        for (int i = 0; i < 22; i++) {
            possibleUsers.put(users[i].getAlias(), users[i]);
        }

        if (possibleUsers.containsKey(request.getAlias())) {
            return new FindUserResponse(possibleUsers.get(request.getAlias()));
        }

        User userToPullFrom = users[(int)(Math.random() * 21)];
        User dummyUser = new User(userToPullFrom.getFirstName(), userToPullFrom.getLastName(), request.getAlias(), userToPullFrom.getImageUrl());

        return new FindUserResponse(dummyUser);
    }

    /**
     * Posts the status. Uses information in
     * the request object to find the user in the database.
     *
     * @param request contains information about the user required to satisfy the request.
     * @return the user response.
     */
    public PostStatusResponse postStatus(PostStatusRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getStatus().getMessage().length() <= 0) {
                throw new AssertionError();
            }
        }

        Status newStatus = request.getStatus();
        newStatuses.add(newStatus);

        return new PostStatusResponse(newStatus);
    }

    /**
     * Determines the index for the first follower in the specified 'allFollowers' list that should
     * be returned in the current request. This will be the index of the next follower after the
     * specified 'lastFollower'.
     *
     * @param lastFollower the last follower that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allFollowers the generated list of followers from which we are returning paged results.
     * @return the index of the first follower to be returned.
     */
    private int getFollowersStartingIndex(User lastFollower, List<User> allFollowers) {

        int followersIndex = 0;

        if(lastFollower != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowers.size(); i++) {
                if(lastFollower.equals(allFollowers.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followersIndex = i + 1;
                    break;
                }
            }
        }

        return followersIndex;
    }

    /**
     * Determines the index for the first status in the specified feed list that should
     * be returned in the current request. This will be the index of the next status after the
     * specified 'lastStatus'.
     *
     * @param lastStatus the last status that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allStatuses the generated list of statuses from which we are returning paged results.
     * @return the index of the first status to be returned.
     */
    private int getFeedStartingIndex(Status lastStatus, ArrayList<Status> allStatuses) {

        int statusesIndex = 0;

        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatus.equals(allStatuses.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusesIndex = i + 1;
                }
            }
        }

        return statusesIndex;
    }

    /**
     * Returns the list of dummy follower data. This is written as a separate method to allow
     * mocking of the followers.
     *
     * @return the followers.
     */
    List<User> getDummyFollowers() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }
}
