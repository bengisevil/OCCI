/*
 * Created by Bengi Sevil on 2018.11.25
 * Copyright © 2018 Bengi Sevil. All rights reserved.
 */
package edu.vt.globals;

public final class Constants {

    /*
        Define contants for paths
        Absolute path for photos on local machine: "/Users/Sevil/DocRoot/SurveyUserPhotoStorage/"
        Submission: /home/cloudsd/Sevil/DocRoot/SurveyUserPhotoStorage/
     */
    public static final String PHOTOS_ABSOLUTE_PATH = "/home/cloudsd/Team9-FileStorage/";
    public static final String PHOTOS_RELATIVE_PATH = "/Team9-FileStorage/";
    public static final String DEFAULT_PHOTO_RELATIVE_PATH = "/Team9-FileStorage/defaultUserPhoto.png";
    public static final Integer THUMBNAIL_SIZE = 200;
    
    /*
        Twitter developer account credentials
        Account owner: bsevil
        Access level: Read and Write
    */
    public static final String CONSUMER_KEY = "nQ7u6DdrvRE7Torim1AJEeviP";
    public static final String CONSUMER_SECRET = "3uTGg5mf48UvmlY35XGnnLefqTsoVtTfTI5LZO2OilbrbgqJwJ";
    public static final String ACCESS_TOKEN = "963420186291396608-8PghJALvyCNCYkzAlyuCfferxQl9zvJ";
    public static final String ACCESS_TOKEN_SECRET = "sYYPJnQkj3CnMfJ4UWYr92bXvyIggIsSfqt1w2VFd9aMO";
    
    /*
        API key for the news api.
        Account owner: bsevil
    */
    public static final String NEWS_API_KEY = "b16c6b9b7fb04c7cba1d360a84788a6a";

    /* 
    United States postal state abbreviations (codes) 
     */
    public static final String[] STATES = {"AK", "AL", "AR", "AZ", "CA", "CO", "CT",
        "DC", "DE", "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA",
        "MD", "ME", "MH", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM",
        "NV", "NY", "OH", "OK", "OR", "PA", "PR", "PW", "RI", "SC", "SD", "TN", "TX", "UT",
        "VA", "VI", "VT", "WA", "WI", "WV", "WY"};

    /* 
    A security question is selected and answered by the user at the time of account creation.
    The selected question is used as a second level of authentication in addition to password.
     */
    public static final String[] QUESTIONS = {
        "In what city or town did your mother and father meet?",
        "In what city or town were you born?",
        "What did you want to be when you grew up?",
        "What do you remember most from your childhood?",
        "What is the name of the boy or girl that you first kissed?",
        "What is the name of the first school you attended?",
        "What is the name of your favorite childhood friend?",
        "What is the name of your first pet?",
        "What is your mother's maiden name?",
        "What was your favorite place to visit as a child?"
    };

}
