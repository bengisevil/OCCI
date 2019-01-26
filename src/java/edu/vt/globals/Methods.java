/*
 * Created by Osman Balci on 2018.06.15
 * Copyright © 2018 Osman Balci. All rights reserved.
 */
package edu.vt.globals;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/*
This class is created to provide Convenience Class Methods (typed with the "static" keyword")
for all classes in the project to use. This is in support of the Separation of Concerns software
engineering design principle and it prevents verboseness and cluttering in the code. The class
methods provided herein are called as Methods.methodName in any class in the project.
 */
public final class Methods {

    /*
    Tell JSF that you want to keep the FacesMessage messages in the Flash scope. 
    --------------------------------------------------------------
    *** This is required when you redirect to show a JSF page. ***
    --------------------------------------------------------------
    Redirecting to show a JSF page involves more than one subsequent requests and
    the messages would die from one request to another if not kept in the Flash scope.
    
    You can invoke this method in any class as: Methods.preserveMessages()
     */
    public static void preserveMessages() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    /*
    We display FacesMessage messages using p:growl in an overlay on all JSF pages by including the 
    content of the growlMessages.xhtml within the editableContent of the siteTemplate.xhtml.
    
    In growlMessages.xhtml, we specify showDetail="false" for the SEVERITY_ERROR messages 
    to prevent duplicate display of the same error message generated by the system. 
    Therefore, you should set messageDetail to "" for the SEVERITY_ERROR messages.
    
    You can invoke this method in any class as follows:

        Methods.showMessage("Information", "messageSummary", "messageDetail");
        Methods.showMessage("Warning", "messageSummary", "messageDetail");
        Methods.showMessage("Error", "messageSummary", "");
        Methods.showMessage("Fatal Error", "messageSummary", "messageDetail");
    
    For exception (ex) errors:
        Methods.showMessage("Fatal Error", "messageSummary", "See: " + ex.getMessage());
     */
    public static void showMessage(String messageType, String messageSummary, String messageDetail) {

        switch (messageType) {
            case "Information":
                FacesMessage infoMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, messageSummary, messageDetail);
                FacesContext.getCurrentInstance().addMessage(null, infoMessage);
                break;
            case "Warning":
                FacesMessage warningMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, messageSummary, messageDetail);
                FacesContext.getCurrentInstance().addMessage(null, warningMessage);
                break;
            case "Error":
                FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageSummary, messageDetail);
                FacesContext.getCurrentInstance().addMessage(null, errorMessage);
                break;
            case "Fatal Error":
                FacesMessage fatalErrorMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, messageSummary, messageDetail);
                FacesContext.getCurrentInstance().addMessage(null, fatalErrorMessage);
                break;
            default:
                System.out.print("Message Type is Out of Range!");
        }
    }

    /*
    Return a mutable Map representing the session scope attributes for the current application.
    You can invoke this method in any class as: Methods.sessionMap()
     */
    public static Map sessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }
    
     /**
     * Return the content of a given URL as String
     *
     * @param webServiceURL to retrieve the JSON data from
     * @return JSON data from the given URL as String
     * @throws Exception
     */
    public static String readUrlContent(String webServiceURL) throws Exception {
        /*
        reader is an object reference pointing to an object instantiated from the BufferedReader class.
        Initially, it is "null" pointing to nothing.
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
