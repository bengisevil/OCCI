
/*
 * Created by Bengi Sevil on 2018.11.25  * 
 * Copyright © 2018 Bengi Sevil. All rights reserved. * 
 */
package edu.vt.controllers;

import edu.vt.globals.Constants;
import edu.vt.globals.Methods;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * This class obtains the status tweets posted by NIDAnews using the Twitter4j
 * library.
 *
 * @author Sevil
 */
@Named(value = "tweetTicketController")
@RequestScoped

public class TweetTickerController {
    // Each String object in the List contains the image filename, e.g., photo1.jpg

    private List<String> tweets;
    private String twitterAccount;

    public TweetTickerController() {
        tweets = new ArrayList<>();
        
        // The twitter account to get the streaming statuses from
        twitterAccount = "NIDAnews";
        
        // Twitter Configuration and Authentication Stepts
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(Constants.CONSUMER_KEY)
                .setOAuthConsumerSecret(Constants.CONSUMER_SECRET)
                .setOAuthAccessToken(Constants.ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(Constants.ACCESS_TOKEN_SECRET);

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        try {
            // Statuses stores the tweets posted by the given account
            List<Status> statuses;

            // Attempt to store the tweets from the NIDAnews account
            statuses = twitter.getUserTimeline(twitterAccount);
            // If any tweets obtained, add to tweets list to display in the ticker
            for (Status status : statuses) {
                tweets.add(status.getText());
            }
        } catch (TwitterException te) {
            Methods.showMessage("Fatal Error", "Unable to obtain tweets!",
                    "Failed to get timeline: " + te.getMessage());
        }
    }

    public List<String> getTweets() {
        return tweets;
    }

    public void setTweets(List<String> tweets) {
        this.tweets = tweets;
    }

}
