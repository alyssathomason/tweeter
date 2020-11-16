package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

<<<<<<< Updated upstream:server/src/main/java/edu/byu/cs/tweeter/server/dao/FollowingDAO.java
=======
import edu.byu.cs.tweeter.BuildConfig;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Status;
>>>>>>> Stashed changes:app/src/main/java/edu/byu/cs/tweeter/model/net/ServerFacade.java
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
<<<<<<< Updated upstream:server/src/main/java/edu/byu/cs/tweeter/server/dao/FollowingDAO.java
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
=======
import edu.byu.cs.tweeter.model.service.request.GetFollowingsRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.request.FindUserRequest;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.GetFollowingsResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.model.service.response.FindUserResponse;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;
>>>>>>> Stashed changes:app/src/main/java/edu/byu/cs/tweeter/model/net/ServerFacade.java

/**
 * A DAO for accessing 'following' data from the database.
 */
public class FollowingDAO {
    // This is the hard coded followee data returned by the 'getFollowees()' method
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
     * Gets the count of users from the database that the user specified is following. The
     * current implementation uses generated data and doesn't actually access a database.
     *
     * @param follower the User whose count of how many following is desired.
     * @return said count.
     */
<<<<<<< Updated upstream:server/src/main/java/edu/byu/cs/tweeter/server/dao/FollowingDAO.java
    public Integer getFolloweeCount(User follower) {
        // TODO: uses the dummy data.  Replace with a real implementation.
        assert follower != null;
        return getDummyFollowees().size();
    }

    /**
     * Gets the users from the database that the user specified in the request is following. Uses
     * information in the request object to limit the number of followees returned and to return the
     * next set of followees after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
=======
    public LoginResponse login(LoginRequest request) {
        //FIND USER IN SERVER
        User user = getUser(new FindUserRequest(request.getAlias())).getUser();

        return new LoginResponse(user, new AuthToken());
    }

    /**
     * Performs a register and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a register.
     * @return the register response.
     */
    public RegisterResponse register(RegisterRequest request) {
        //FIND USER IN SERVER
        User user = new User(request.getFirstName(), request.getLastName(), request.getAlias(), MALE_IMAGE_URL);

        return new RegisterResponse(user, new AuthToken());
    }

    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LogoutResponse logout(LogoutRequest request) {
        User userToLogout = getUser(new FindUserRequest(request.getAlias())).getUser();

        return new LogoutResponse(userToLogout);
    }

    /**
     * Performs a follow and if successful, returns the follow object. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a follow.
     * @return the follow response.
     */
    public FollowResponse follow(FollowRequest request) {
        Follow follow = new Follow(request.getFollowee(), request.getFollower());
        return new FollowResponse(follow);
    }

    /**
     * Performs an unfollow. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a follow.
     * @return the follow response.
     */
    public UnfollowResponse unfollow(UnfollowRequest request) {
        if(BuildConfig.DEBUG) {

            if(request.getFollowee() == null || request.getFollower() == null) {
                throw new AssertionError();
            }
        }
        return new UnfollowResponse();
    }

    /**
     * Returns the users that the user specified in the request is following and that are following
     * the user. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followees & followers are to be
     *                returned and any other information required to satisfy the request.
     * @return the followings response.
     */
    public GetFollowingsResponse getFollowings(GetFollowingsRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {

            if(request.getUser() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowees = getDummyFollowees();
        List<User> allFollowers = getDummyFollowees();

        return new GetFollowingsResponse(allFollowees, allFollowers);
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
>>>>>>> Stashed changes:app/src/main/java/edu/byu/cs/tweeter/model/net/ServerFacade.java
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getLimit() > 0;
        assert request.getFollower() != null;

        List<User> allFollowees = getDummyFollowees();
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowees != null) {
                int followeesIndex = getFolloweesStartingIndex(request.getLastFollowee(), allFollowees);

                for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                    responseFollowees.add(allFollowees.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allFollowees.size();
            }
        }

        return new FollowingResponse(responseFollowees, hasMorePages);
    }

    /**
     * Returns the users that are following the user specified. Uses information in
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

        List<User> allFollowers = getDummyFollowees();
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
     * Determines the index for the first followee in the specified 'allFollowees' list that should
     * be returned in the current request. This will be the index of the next followee after the
     * specified 'lastFollowee'.
     *
     * @param lastFollowee the last followee that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allFollowees the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFollowee != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFollowee.equals(allFollowees.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                    break;
                }
            }
        }

        return followeesIndex;
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
     * Determines the index for the first status in the specified story list that should
     * be returned in the current request. This will be the index of the next status after the
     * specified 'lastStatus'.
     *
     * @param lastStatus the last status that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allStatuses the generated list of statuses from which we are returning paged results.
     * @return the index of the first status to be returned.
     */
    private int getStoryStartingIndex(Status lastStatus, ArrayList<Status> allStatuses) {

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
     * Returns the list of dummy followee data. This is written as a separate method to allow
     * mocking of the followees.
     *
     * @return the generator.
     */
    List<User> getDummyFollowees() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }

    /**
     * Returns the list of dummy status data. This is written as a separate method to allow
     * mocking of the statuses.
     *
     * @return the generator.
     */
    ArrayList<Status> getDummyStatuses() {
        ArrayList<Status> statuses = new ArrayList<>();
        statuses.addAll(newStatuses);
        statuses.add(status1);
        statuses.add(status2);
        statuses.add(status3);
        statuses.add(status4);
        statuses.add(status5);
        statuses.add(status6);
        return statuses;
    }

    /**
     * Returns the list of dummy status data. This is written as a separate method to allow
     * mocking of the statuses.
     *
     * @return the generator.
     */
    ArrayList<Status> getDummyStoryStatuses(User user) {
        final Status statusSA1 = new Status(STUDENT_ALUMNI_MESSAGES[(int)(Math.random() * 7)], user, "September 10, 2019 7pm");
        final Status statusSA2 = new Status(STUDENT_ALUMNI_MESSAGES[(int)(Math.random() * 7)], user, "December 9, 2019 7pm");
        final Status statusSA3 = new Status(STUDENT_ALUMNI_MESSAGES[(int)(Math.random() * 7)], user, "September 8, 2019 7pm");
        final Status statusSA4 = new Status(STUDENT_ALUMNI_MESSAGES[(int)(Math.random() * 7)], user, "September 7, 2018 7pm");
        final Status statusSA5 = new Status(STUDENT_ALUMNI_MESSAGES[(int)(Math.random() * 7)], user, "September 6, 2017 7pm");
        final Status statusSA6 = new Status(STUDENT_ALUMNI_MESSAGES[(int)(Math.random() * 7)], user, "January 20, 2016 10am");

        ArrayList<Status> statuses = new ArrayList<>();

        if (newStatuses.size() > 0 && user.equals(newStatuses.get(0).poster)) {
            statuses.addAll(newStatuses);
        }

        statuses.add(statusSA1);
        statuses.add(statusSA2);
        statuses.add(statusSA3);
        statuses.add(statusSA4);
        statuses.add(statusSA5);
        statuses.add(statusSA6);
        return statuses;
    }
}
