/*
 * Created by Mahek Mehta on 2018.12.01  * 
 * Copyright © 2018 Mahek Mehta. All rights reserved. * 
 */
package edu.vt.pojo;

import java.io.Serializable;

/**
 * Resource object with name, website, image, description
 * @author Mahek Mehta
 */
public class Resource implements Serializable{
    private String name;
    private String website;
    private String description;
    private String image;
    public Resource(String name, String website, String image, String description)
    {
        this.name = name;
        setName(name);
        this.website = website;
        setWebsite(website);
        this.description = description;
        setDescription(description);
        this.image = image;     
        setImage(image);
    }

    public String getName() {
        System.out.println("It hits");
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    
}