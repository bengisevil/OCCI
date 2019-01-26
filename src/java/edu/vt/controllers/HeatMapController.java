

package edu.vt.controllers;

import edu.vt.globals.Methods;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.annotation.PostConstruct;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import edu.vt.globals.Methods;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;



/*
 * Created by Zachary King on 2018.11.25  * 
 * Copyright © 2018 Zachary King. All rights reserved. * 
 */
/**
 *
 * @author Zach
 */

@Named("heatMapController")
@SessionScoped
public class HeatMapController implements Serializable {
    
    private final String opioidURLDataCDC
            = "https://data.cdc.gov/resource/a3uk-kgrx.json?$$app_token=oEyXft4Trb2U132DgZfXC5hLZ";

    private int deaths2017 = 0;
    private int deaths2016 = 0;
    private int deaths2015 = 0;
    private int deaths2014 = 0;
    private BarChartModel barChart = new BarChartModel();
    private String testArray = "['State', 'Popularity'], ['NJ', 200], ['NM', 300], ['CA', 400], ['VA', 500], ['GA', 600], ['NY', 700]";
    private String jsonData;
    
    @PostConstruct
    public void init() {


        /*
        JSON uses the following notation:
        { }    represents a JavaScript object as a Dictionary with Key:Value pairs
        [ ]    represents Array
        [{ }]  represents an Array of JavaScript objects (dictionaries)
          :    separates Key from the Value

        ******************************************************************
        *   Create the vtBuildings list containing VT building objects   *
        ******************************************************************
         */
        try {
            // Using the method given below, obtain the JSON data file as a String from the API URL
            jsonData = readUrlContent(opioidURLDataCDC);

            /*
            The API URL https://data.cdc.gov/resource/a3uk-kgrx.json?$$app_token=oEyXft4Trb2U132DgZfXC5hLZ 
            returns the following JSON file as one array containing all national information.
            
            [
                {
                    "data_value": "97.3762376237624",
                    "footnote": "Underreported due to incomplete data",
                    "indicator": "Percent with drugs specified",
                    "month": "February",
                    "percent_complete": "100",
                    "percent_pending_investigation": "0.251168479447866",
                    "state": "NC",
                    "state_name": "NC",
                    "year": "2017"
                }
            ]
             */
            // Convert the JSON data into a JSON array
            JSONArray jsonArray = new JSONArray(jsonData);
            
            int dataLength = jsonArray.length();
            for (int i = 0; i < dataLength; i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i); 
                if (jsonObject.optString("indicator").equals("Number of Deaths")) {
                    if (jsonObject.optString("year").equals("2014")) {
                        deaths2014 += Integer.parseInt(jsonObject.optString("data_value"));
                    }
                    
                    else if (jsonObject.optString("year").equals("2015")) {
                        deaths2015 += Integer.parseInt(jsonObject.optString("data_value"));
                    }     
                    
                    else if (jsonObject.optString("year").equals("2016")) {
                        deaths2016 += Integer.parseInt(jsonObject.optString("data_value"));
                    }
                    
                    else if (jsonObject.optString("year").equals("2017")) {
                        deaths2017 += Integer.parseInt(jsonObject.optString("data_value"));
                    }                                         
                }
            }
        

            barChart = new BarChartModel();

            ChartSeries series = new ChartSeries();
            series.setLabel("OCCI Chart 1");
            
            series.set("2014", deaths2014);
            series.set("2015", deaths2015);
            series.set("2016", deaths2016);
            series.set("2017", deaths2017);

            barChart.addSeries(series);
            barChart.setLegendPosition("ne");

            Axis xAxis = barChart.getAxis(AxisType.X);
            xAxis.setLabel("Years");

            Axis yAxis = barChart.getAxis(AxisType.Y);
            yAxis.setLabel("Total Deaths");

        // Use Java 8 Stream max() and min() to determine max and min stock prices
            int maxCount = deaths2016 + 1000000;
            int minCount = 0;

        // Add 10 to leave some space above the max value on the Y axis
            yAxis.setMax(maxCount + 10.0); 
            yAxis.setMin(minCount);

        // JavaScript function "barChartExtender" in StockChart.xhtml is used to style the chart
            barChart.setExtender("barChartExtender");
             
            

        } catch (Exception ex) {
            Methods.showMessage("Fatal Error", "Something went wrong while accessing VTBuildingsWS RESTful web service!",
                    "See: " + ex.getMessage());
        }
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
    
    public int getDeaths2017() {
        return deaths2017;
    }

    public void setDeaths2017(int deaths2017) {
        this.deaths2017 = deaths2017;
    }

    public int getDeaths2016() {
        return deaths2016;
    }

    public void setDeaths2016(int deaths2016) {
        this.deaths2016 = deaths2016;
    }

    public int getDeaths2015() {
        return deaths2015;
    }

    public void setDeaths2015(int deaths2015) {
        this.deaths2015 = deaths2015;
    }

    public int getDeaths2014() {
        return deaths2014;
    }

    public void setDeaths2014(int deaths2014) {
        this.deaths2014 = deaths2014;
    }

    public BarChartModel getBarChart() {
        return barChart;
    }

    public void setBarChart(BarChartModel barChart) {
        this.barChart = barChart;
    }    

    public String getTestArray() {
        return testArray;
    }

    public void setTestArray(String testArray) {
        this.testArray = testArray;
    }
    
    public String readJSONData() {
        try {
            jsonData = readUrlContent(opioidURLDataCDC);
        } catch (Exception ex) {
            Methods.showMessage("Fatal Error", "Something went wrong while accessing VTBuildingsWS RESTful web service!",
                    "See: " + ex.getMessage());
        }
        
        return jsonData;
    }
    
 /*
    ================
    Instance Methods
    ================
     */
    /**
     * Return the content of a given URL as String
     *
     * @param webServiceURL to retrieve the JSON data from
     * @return JSON data from the given URL as String
     * @throws Exception
     */
    public String readUrlContent(String webServiceURL) throws Exception {
        /*
        reader is an object reference pointing to an object instantiated from the BufferedReader class.
        Currently, it is "null" pointing to nothing.
         */
        BufferedReader reader = null;

        try {
            // Create a URL object from the webServiceURL given
            URL url = new URL(webServiceURL);
            /*
            The BufferedReader class reads text from a character-input stream, buffering characters
            so as to provide for the efficient reading of characters, arrays, and lines.
             */
            reader = new BufferedReader(new InputStreamReader(url.openStream()));

            // Create a mutable sequence of characters and store its object reference into buffer
            StringBuilder buffer = new StringBuilder();

            // Create an array of characters of size 10240
            char[] chars = new char[10240];

            int numberOfCharactersRead;
            /*
            The read(chars) method of the reader object instantiated from the BufferedReader class
            reads 10240 characters as defined by "chars" into a portion of a buffered array.

            The read(chars) method attempts to read as many characters as possible by repeatedly
            invoking the read method of the underlying stream. This iterated read continues until
            one of the following conditions becomes true:

                (1) The specified number of characters have been read, thus returning the number of characters read.
                (2) The read method of the underlying stream returns -1, indicating end-of-file, or
                (3) The ready method of the underlying stream returns false, indicating that further input requests would block.

            If the first read on the underlying stream returns -1 to indicate end-of-file then the read(chars) method returns -1.
            Otherwise the read(chars) method returns the number of characters actually read.
             */
            while ((numberOfCharactersRead = reader.read(chars)) != -1) {
                buffer.append(chars, 0, numberOfCharactersRead);
            }

            // Return the String representation of the created buffer
            return buffer.toString();

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

 
}
