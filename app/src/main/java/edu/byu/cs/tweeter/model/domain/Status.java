package edu.byu.cs.tweeter.model.domain;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a follow relationship.
 */
public class Status {

    private final String message;
    public User poster;
    private final String postedTime;
    private ArrayList<String> mentions;
    private ArrayList<URL> urls;

    public Status(@NotNull String message, @NotNull User poster, String postedTime) {
        this.message = message;
        this.poster = poster;
        this.postedTime = postedTime;
        parseMentions();
        try {
            extractURLs();
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException thrown " + e.toString());
        }

    }

    public String getMessage() {
        return message;
    }

    public User getPoster() {
        return poster;
    }

    public String getPostedTime() { return postedTime; }

    public Date getPostedTimeDate() { return new Date(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status that = (Status) o;
        return message.equals(that.message) &&
                poster.equals(that.poster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, poster);
    }

    @NotNull
    @Override
    public String toString() {
        return "Status{" +
                "poster=" + poster.getAlias() +
                "posted time=" + getPostedTime() +
                ", message=" + message +
                '}';
    }

    /**
     * Populates the mentions list with all aliases contained in the message
     */
    private void parseMentions() {
        mentions = new ArrayList<>();
        String user = "";
        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) == '@') {
                for (int y = i; y < message.length(); y++) {
                    if (message.charAt(y) != ' ') {
                        user += message.charAt(y);
                    } else {
                        mentions.add(user);
                        i = y; user = "";
                    }
                }
            }
        }
    }

    /**
     * Populates the urls list with all links contained in the message
     */
    private void extractURLs() throws MalformedURLException {
        urls = new ArrayList<>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(message);

        while (urlMatcher.find()) {
            String sURL = message.substring(urlMatcher.start(0),
                    urlMatcher.end(0));
            urls.add(new URL(sURL));
        }
    }
}
