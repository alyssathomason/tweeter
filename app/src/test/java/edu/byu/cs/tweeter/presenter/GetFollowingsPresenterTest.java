package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.GetFollowingsService;
import edu.byu.cs.tweeter.model.service.request.GetFollowingsRequest;
import edu.byu.cs.tweeter.model.service.response.GetFollowingsResponse;

public class GetFollowingsPresenterTest {

    private GetFollowingsRequest request;
    private GetFollowingsResponse response;
    private GetFollowingsService mockGetFollowingsService;
    private GetFollowingsPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        List<User> followees = new ArrayList<>();
        List<User> followers = new ArrayList<>();
        request = new GetFollowingsRequest(currentUser);
        response = new GetFollowingsResponse(followees, followers);

        // Create a mock GetFollowingsService
        mockGetFollowingsService = Mockito.mock(GetFollowingsService.class);
        Mockito.when(mockGetFollowingsService.getFollowings(request)).thenReturn(response);

        // Wrap a GetFollowingsPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new GetFollowingsPresenter(new GetFollowingsPresenter.View() {}));
        Mockito.when(presenter.getFollowingsService()).thenReturn(mockGetFollowingsService);
    }

    @Test
    public void testGetFollowings_returnsServiceResult() throws IOException {
        Mockito.when(mockGetFollowingsService.getFollowings(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getFollowings(request));
    }

    @Test
    public void testGetFollowings_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockGetFollowingsService.getFollowings(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getFollowings(request);
        });
    }
}
