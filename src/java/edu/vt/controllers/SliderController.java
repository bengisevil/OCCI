package edu.vt.controllers;

/*
 * Created by Bengi Sevil on 2018.10.14  * 
 * Copyright © 2018 Bengi Sevil. All rights reserved. * 
 */
import edu.vt.EntityBeans.News;
import edu.vt.globals.Constants;
import edu.vt.globals.Methods;
import static edu.vt.globals.Methods.readUrlContent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

@Named(value = "sliderController")
@RequestScoped

public class SliderController {

    // Each String object in the List contains the image filename, e.g., photo1.jpg
    private List<String> sliderImages;
    private List<News> recentNews;
    private String newsApiBaseURL;
    private Boolean displayNews;

    public SliderController() throws Exception {
        // Default value of displayNews 
        displayNews = false;

        // Set news API base URL 
        newsApiBaseURL = "http://newsapi.org/v2/everything?q=opioid&apiKey=";

        // Set slider images from project directory
        sliderImages = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            sliderImages.add("photo" + i + ".jpg");
        }

        // Obtain and process json data from the news api for newsImages
        recentNews = new ArrayList<>();
        obtainRecentNews();
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public List<String> getSliderImages() {
        return sliderImages;
    }

    public List<News> getRecentNews() {
        return recentNews;
    }

    public Boolean getDisplayNews() {
        return displayNews;
    }

    public void setDisplayNews(Boolean displayNews) {
        this.displayNews = displayNews;
    }

    /*
    ================
    Instance Methods
    ================
     */
    public String description(String image) {

        String imageDescription = "";

        switch (image) {
            case "photo1.jpg":
                imageDescription = "All opioids are chemically related and interact with opioid receptors on nerve cells in the body and brain";
                break;
            case "photo2.jpg":
                imageDescription = "Regular use—even as prescribed by a doctor—can lead to dependence and, when misused, opioid pain relievers can lead to addiction, overdose incidents, and deaths";
                break;
            case "photo3.jpg":
                imageDescription = "Statistics show the rising concern";
                break;
            case "photo4.jpg":
                imageDescription = "Break the cycle of guilt and shame. Do your recovery with other people who are going through the same thing";
                break;
            case "photo5.jpg":
                imageDescription = "Ask for help. Have a strong support system.";
                break;
            case "photo6.jpg":
                imageDescription = "There are many different routes you can take when it comes to addiction treatment";
                break;
            case "photo7.jpg":
                imageDescription = "There’re outpatient programs or inpatient programs, detox hospitalization or private detox, public programs or private programs, long-term drug rehab, and 12-step programs or dual diagnosis programs";
                break;
            case "photo8.jpg":
                imageDescription = "NIDA Dir. Nora Volkow discusses why nicotine is so addictive despite producing relatively low reward and pleasure for users";
                break;
            case "photo9.jpg":
                imageDescription = "Did you know drug addiction is a mental illness? Addiction changes brain behaviors and weakens its ability to control impulses in way that resembles other mental illnesses";
               break;
            case "photo10.jpg":
                imageDescription = "Non-steroidal anabolics like IGF and HGH are produced by the body and are sometimes medically prescribed. They can also be misused in an attempt to enhance athletic performance";
                break;
            case "photo11.jpg":
                imageDescription = "New findings related to cell functions and calcium deficiency; may impact substance use disorders";
                break;
            case "photo12.jpg":
                imageDescription = "Plan your community event today by creating an account with OCCI as an event organizer! Together, we are stronger";
                break;
            case "photo13.jpg":
                imageDescription = "Heroin often contains additives like starch, powdered milk and sugar that can cause clogged blood vessels when injected";
                break;
            case "photo14.jpg":
                imageDescription = "More\n" +
"Taking unmarked medications or medications that are not yours can cause serious harm. Make sure you are only using medication prescribed by your doctor";
                break;
                    
        }

        return imageDescription;
    }

    public void obtainRecentNews() throws Exception {

        String newsAPIUrl = newsApiBaseURL + Constants.NEWS_API_KEY;

        try {
            //Obtain json data from the url
            String jsonData = readUrlContent(newsAPIUrl);
            // Obtain the only dictionary returned as a JSON object
            JSONObject newsData = new JSONObject(jsonData);

            /*
                The news api response format is as follows:
            {
                "status": "ok",
                "totalResults": 20,
                -"articles": [
                -{
                -"source": {
                "id": null,
                "name": "Bostonglobe.com"
                },
                "author": null,
                "title": "Trump lashes out at GM as company announces plans to cut 14000 jobs",
                "description": null,
                "url": "https://www.bostonglobe.com/business/2018/11/26/slashing-factory-and-white-collar-jobs-north-america/ufIuAefldgAbgyOuvVUp8K/story.html",
                "urlToImage": null,
                "publishedAt": "2018-11-26T21:59:24Z",
                "content": null
                },
                -{
                -"source": {
                "id": null,
                "name": "Usnews.com"
                },
                "author": null,
                "title": "White House Pastry Chefs Make National Mall in Gingerbread",
                "description": null,
                "url": "https://www.usnews.com/news/politics/articles/2018-11-26/melania-trump-unveils-white-house-christmas-decorations",
                "urlToImage": null,
                "publishedAt": "2018-11-26T21:33:00Z",
                "content": null
                },
                -{
                ...
             */
            String strResults = newsData.optString("articles");
            JSONArray results = new JSONArray(strResults);
            JSONObject newsObj;

            // Temporary variables for news object setup
            String title;
            String url;
            String urlToImage;
            String publishedAt;
            /*
                Go through each result to create a news object for each qualifying (imageURL available).
             */
            for (int i = 0; i < results.length(); i++) {
                News news = new News();
                newsObj = results.getJSONObject(i);

                /*
                 ==================
                   News Title
                 ==================
                 */
                title = newsObj.optString("title", null);
                if (title != null) {
                    news.setTitle(title);
                } else {
                    //Invalid news
                    continue;
                }

                /*
                 ==================
                   Source URL
                 ==================
                 */
                url = newsObj.optString("url", null);
                if (url != null) {
                    news.setUrl(url);
                } else {
                    //Invalid news
                    continue;
                }

                /*
                 ==================
                   Image URL
                 ==================
                 */
                urlToImage = newsObj.optString("urlToImage", null);
                if (urlToImage != null) {
                    String imgUrl = urlToImage.replace("https", "http");
                    System.out.print("HERE " + imgUrl);
                    news.setUrlToImage(imgUrl);
                } else {
                    continue;
                }

                /*
                 ==================
                   Publish Date
                 ==================
                 */
                publishedAt = newsObj.optString("publishedAt", null);
                if (publishedAt != null) {
                    //Format date
                    String str = publishedAt.substring(0, 10);
                    publishedAt = str;
                    news.setPublishedAt(publishedAt + ": ");
                } else {
                    news.setPublishedAt("");
                }

                //Add news object to the recentNews list
                recentNews.add(news);
            }

        } catch (IOException ex) {
            Methods.showMessage("Fatal Error", "Error in processing JSON data returned from News API!",
                    "See: " + ex.getMessage());
        }
    }

    public String getImageUrl(News curNews) {
        if (curNews != null) {
            return curNews.getUrlToImage();
        }
        return "";
    }

    public String getUrl(News curNews) {
        if (curNews != null) {
            return curNews.getUrl();
        }
        return "";
    }

    public String getDate(News curNews) {
        if (curNews != null) {
            return curNews.getPublishedAt();
        }
        return "";
    }

    public String getTitle(News curNews) {
        if (curNews != null) {
            return curNews.getTitle();
        }
        return "";
    }
    
    public void inputSwitchUpdate(boolean flag){
        displayNews = flag;
    }
}
