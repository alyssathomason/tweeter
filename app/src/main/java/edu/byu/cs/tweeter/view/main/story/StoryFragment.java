package edu.byu.cs.tweeter.view.main.story;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.request.FindUserRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.model.service.response.FindUserResponse;
import edu.byu.cs.tweeter.presenter.StoryPresenter;
import edu.byu.cs.tweeter.presenter.FindUserPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.FindUserTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetStoryTask;
import edu.byu.cs.tweeter.view.main.UserProfileActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The fragment that displays on the 'Story' tab.
 */
public class StoryFragment extends Fragment {

    private static final String LOG_TAG = "StoryFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 4;

    private User user;
    private AuthToken authToken;


    private StoryRecyclerViewAdapter storyRecyclerViewAdapter;

    /**
     * Creates an instance of the fragment and places the user and auth token in an arguments
     * bundle assigned to the fragment.
     *
     * @param user the logged in user.
     * @param authToken the auth token for this user's session.
     * @return the fragment.
     */
    public static StoryFragment newInstance(User user, AuthToken authToken) {
        StoryFragment fragment = new StoryFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story, container, false);

        //noinspection ConstantConditions
        user = (User) getArguments().getSerializable(USER_KEY);



        RecyclerView storyRecyclerView = view.findViewById(R.id.storyRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        storyRecyclerView.setLayoutManager(layoutManager);

        storyRecyclerViewAdapter = new StoryRecyclerViewAdapter();
        storyRecyclerView.setAdapter(storyRecyclerViewAdapter);

        storyRecyclerView.addOnScrollListener(new StoryRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    /**
     * The ViewHolder for the RecyclerView that displays the Story data.
     */
    private class StoryHolder extends RecyclerView.ViewHolder implements FindUserPresenter.View, FindUserTask.Observer {

        private final ImageView userImage;
        private final TextView userAlias, userName, message, postedTime;

        private FindUserPresenter userPresenter;

        /**
         * Creates an instance and sets an OnClickListener for the user's row.
         *
         * @param itemView the view on which the user will be displayed.
         */
        StoryHolder(@NonNull View itemView) {
            super(itemView);

            userPresenter = new FindUserPresenter(this);

            userImage = itemView.findViewById(R.id.status_user_image);
            userAlias = itemView.findViewById(R.id.status_alias);
            userName = itemView.findViewById(R.id.status_user_name);
            postedTime = itemView.findViewById(R.id.time_posted);
            message = itemView.findViewById(R.id.status_message);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(getContext(), "You selected '" + userName.getText() + "'.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        /**
         * Binds the status's data.
         *
         * @param status the status.
         */
        void bindStatus(Status status) {
            bindUser(user);
            postedTime.setText(status.getPostedTime());
            makeClickable(status.getMessage(), message);
        }

        /**
         * Binds the user's data and the message to the view.
         *
         * @param user the user.
         */
        void bindUser(User user) {
            userImage.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));
            System.out.println(user + ": " + user.getImageUrl());
            userAlias.setText(user.getAlias());
            userName.setText(user.getName());
        }

        private void makeClickable(String message, TextView messageTV) {

            SpannableString ss = new SpannableString(message);

            //PARSE MENTIONS

            ArrayList<String> mentions = new ArrayList<>();
            ArrayList<Pair> locationsMentions = new ArrayList<>();
            for (int i = 0; i < message.length(); i++) {
                if (message.charAt(i) == '@') {
                    String mention = "";
                    Integer start = i;
                    Integer end;
                    for (int y = i; y < message.length() + 1; y++) {
                        if (y == message.length() || message.charAt(y) == ' ') {
                            mentions.add(mention);
                            end = y;
                            Pair<Integer, Integer> pair = new Pair(start, end);
                            locationsMentions.add(pair);
                            i = y;
                            break;
                        } else {
                            mention += message.charAt(y);
                        }
                    }
                }
            }

            int countM = 0;
            for (Pair<Integer, Integer> pair : locationsMentions) {
                FindUserTask findUserTask = new FindUserTask(userPresenter, this);
                ClickableSpan clickableMention = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        FindUserRequest requestUser = new FindUserRequest(mentions.get(countM));
                        findUserTask.execute(requestUser);
                    }
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                    }
                };

                ss.setSpan(clickableMention, pair.first, pair.second, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            //PARSE URLs

            ArrayList<Uri> urls = new ArrayList<>();
            ArrayList<Pair> locationsURLs = new ArrayList<>();

            String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
            Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
            Matcher urlMatcher = pattern.matcher(message);

            while (urlMatcher.find()) {
                locationsURLs.add(new Pair(urlMatcher.start(0), urlMatcher.end(0)));
                String sURL = message.substring(urlMatcher.start(0),
                        urlMatcher.end(0));
                urls.add(Uri.parse(sURL));
            }

            int count = 0;
            for (Pair<Integer, Integer> pair : locationsURLs) {
                Uri url = urls.get(count);
                ClickableSpan clickableURL = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        Intent i = new Intent(Intent.ACTION_VIEW, url);
                        startActivity(i);
                    }
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                    }
                };
                ss.setSpan(clickableURL, pair.first, pair.second, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                count++;
            }

            messageTV.setText(ss);
            messageTV.setMovementMethod(LinkMovementMethod.getInstance());
            messageTV.setHighlightColor(Color.TRANSPARENT);
        }

        /**
         * A callback indicating more following data has been received. Loads the new followings
         * and removes the loading footer.
         *
         * @param userResponse the asynchronous response to the request to load more items.
         */
        @Override
        public void userRetrieved(FindUserResponse userResponse) {
            User mentionedUser = userResponse.getUser();
            Intent intent = new Intent(getActivity(), UserProfileActivity.class);
            intent.putExtra("LOGGED_IN_USER", user);
            intent.putExtra("USER", mentionedUser);
            startActivity(intent);
        }

        /**
         * A callback indicating that an exception was thrown by the presenter.
         *
         * @param exception the exception.
         */
        @Override
        public void handleUserException(Exception exception) {
            Log.e("", exception.getMessage(), exception);
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    /**
     * The adapter for the RecyclerView that displays the Story data.
     */
    private class StoryRecyclerViewAdapter extends RecyclerView.Adapter<StoryHolder> implements GetStoryTask.Observer, StoryPresenter.View {

        private final ArrayList<Status> statuses = new ArrayList<>();

        private StoryPresenter storyPresenter;

        private Status lastStatus;

        private boolean hasMorePages;
        private boolean isLoading = false;

        /**
         * Creates an instance and loads the first page of st data.
         */
        StoryRecyclerViewAdapter() { loadMoreItems(); }

        /**
         * Adds new users to the list from which the RecyclerView retrieves the users it displays
         * and notifies the RecyclerView that items have been added.
         *
         * @param newStatuses the users to add.
         */
        void addItems(ArrayList<Status> newStatuses) {
            int startInsertPosition = statuses.size();
            statuses.addAll(newStatuses);
            this.notifyItemRangeInserted(startInsertPosition, newStatuses.size());
        }

        /**
         * Adds a single status to the list from which the RecyclerView retrieves the statuses it
         * displays and notifies the RecyclerView that an item has been added.
         *
         * @param status the status to add.
         */
        void addItem(Status status) {
            statuses.add(status);
            this.notifyItemInserted(statuses.size() - 1);
        }

        /**
         * Removes a status from the list from which the RecyclerView retrieves the statuses it displays
         * and notifies the RecyclerView that an item has been removed.
         *
         * @param status the status to remove.
         */
        void removeItem(Status status) {
            int position = statuses.indexOf(status);
            statuses.remove(position);
            this.notifyItemRemoved(position);
        }

        /**
         *  Creates a view holder for a status to be displayed in the RecyclerView or for a message
         *  indicating that new rows are being loaded if we are waiting for rows to load.
         *
         * @param parent the parent view.
         * @param viewType the type of the view (ignored in the current implementation).
         * @return the view holder.
         */
        @NonNull
        @Override
        public StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(StoryFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.status_row, parent, false);
            }

            return new StoryHolder(view);
        }

        /**
         * Binds the status at the specified position unless we are currently loading new data. If
         * we are loading new data, the display at that position will be the data loading footer.
         *
         * @param storyHolder the ViewHolder to which the status should be bound.
         * @param position the position (in the list of statuses) that contains the status to be
         *                 bound.
         */
        @Override
        public void onBindViewHolder(@NonNull StoryHolder storyHolder, int position) {
            if(!isLoading) {
                storyHolder.bindStatus(statuses.get(position));
            }
        }

        /**
         * Returns the current number of statuses available for display.
         * @return the number of statuses available for display.
         */
        @Override
        public int getItemCount() {
            return statuses.size();
        }

        /**
         * Returns the type of the view that should be displayed for the item currently at the
         * specified position.
         *
         * @param position the position of the items whose view type is to be returned.
         * @return the view type.
         */
        @Override
        public int getItemViewType(int position) {
            return (position == statuses.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        /**
         * Causes the Adapter to display a loading footer and make a request to get more story
         * data.
         */
        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            storyPresenter = new StoryPresenter(this);

            GetStoryTask getStoryTask = new GetStoryTask(storyPresenter, this);
            StoryRequest request = new StoryRequest(statuses.get(0).getPoster(), PAGE_SIZE, lastStatus);
            getStoryTask.execute(request);
        }

        /**
         * A callback indicating more story data has been received. Loads the new statuses
         * and removes the loading footer.
         *
         * @param storyResponse the asynchronous response to the request to load more items.
         */
        @Override
        public void statusesRetrieved(StoryResponse storyResponse) {
            ArrayList<Status> statuses = storyResponse.getStatuses();

            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() -1) : null;
            hasMorePages = storyResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            storyRecyclerViewAdapter.addItems(statuses);
        }

        /**
         * A callback indicating that an exception was thrown by the presenter.
         *
         * @param exception the exception.
         */
        @Override
        public void handleException(Exception exception) {
            Log.e(LOG_TAG, exception.getMessage(), exception);
            removeLoadingFooter();
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * Adds a dummy user to the list of users so the RecyclerView will display a view (the
         * loading footer view) at the bottom of the list.
         */
        private void addLoadingFooter() {
            addItem(new Status("We are all doomed.", new User("First", "Last", "@TestUser", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"), "October 31, 2018 4pm"));
        }

        /**
         * Removes the dummy user from the list of users so the RecyclerView will stop displaying
         * the loading footer at the bottom of the list.
         */
        private void removeLoadingFooter() {
            removeItem(statuses.get(statuses.size() - 1));
        }
    }

    /**
     * A scroll listener that detects when the user has scrolled to the bottom of the currently
     * available data.
     */
    private class StoryRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         *
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        StoryRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        /**
         * Determines whether the user has scrolled to the bottom of the currently available data
         * in the RecyclerView and asks the adapter to load more data if the last load request
         * indicated that there was more data to load.
         *
         * @param recyclerView the RecyclerView.
         * @param dx the amount of horizontal scroll.
         * @param dy the amount of vertical scroll.
         */
        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!storyRecyclerViewAdapter.isLoading && storyRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    storyRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }


}
