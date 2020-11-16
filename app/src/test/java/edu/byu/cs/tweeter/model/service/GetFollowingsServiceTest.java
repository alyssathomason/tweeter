package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.GetFollowingsRequest;
import edu.byu.cs.tweeter.model.service.response.GetFollowingsResponse;

public class GetFollowingsServiceTest {

    private GetFollowingsRequest validRequest;
    private GetFollowingsRequest invalidRequest;

    private GetFollowingsResponse successResponse;
    private GetFollowingsResponse failureResponse;

    private GetFollowingsService getFollowingsServiceSpy;

    /**
     * Create a GetFollowingsService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        // Setup request objects to use in the tests
        validRequest = new GetFollowingsRequest(currentUser);
        invalidRequest = new GetFollowingsRequest(null);

        // Setup a mock ServerFacade that will return known responses
        List<User> followees = new ArrayList<>();
        List<User> followers = new ArrayList<>();
        successResponse = new GetFollowingsResponse(followees, followers);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFollowings(validRequest)).thenReturn(successResponse);

        failureResponse = new GetFollowingsResponse(false, "An exception occured");
        Mockito.when(mockServerFacade.getFollowings(invalidRequest)).thenReturn(failureResponse);

        // Create a GetFollowingsService instance and wrap it with a spy that will use the mock service
        getFollowingsServiceSpy = Mockito.spy(new GetFollowingsService());
        Mockito.when(getFollowingsServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link GetFollowingsService#getFollowings(GetFollowingsRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException {
        GetFollowingsResponse response = getFollowingsServiceSpy.getFollowings(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link GetFollowingsService#getFollowings(GetFollowingsRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_validRequest_loadsProfileImages() throws IOException {
        GetFollowingsResponse response = getFollowingsServiceSpy.getFollowings(validRequest);

        for(User user : response.getFollowees()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }

    /**
     * Verify that for failed requests the {@link GetFollowingsService#getFollowings(GetFollowingsRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowees() throws IOException {
        GetFollowingsResponse response = getFollowingsServiceSpy.getFollowings(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
