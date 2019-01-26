/*
 * Created by Mahek Mehta on 2018.12.01  * 
 * Copyright © 2018 Mahek Mehta. All rights reserved. * 
 */
package edu.vt.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class ResourceScroller implements Serializable{
    //static list of different resources
    private static final List<Resource> resources = new 
        ArrayList<Resource>(Arrays.asList(new Resource("Substance Abuse and Mental Health Administration", 
                "https://www.samhsa.gov/find-help/national-helpline", "samhsa", 
                "SAMHSA’s National Helpline is a free, confidential, 24/7, "
                        + "365-day-a-year treatment referral and information service "
                        + "(in English and Spanish) for individuals and families facing mental "
                        + "and/or substance use disorders."), 
                new Resource("Health Resources & Services Administration", 
                                "https://www.hrsa.gov/opioids", "hrsa", "The Health Resources and Services Administration (HRSA) "
                                        + "supports its grantees with resources, technical assistance, and training to integrate "
                                        + "behavioral health care services into practice settings and communities. "), 
                new Resource("American Academy of Family Physicians", "https://www.aafp.org/patient-care/public-health/pain-opioids/resources.html", 
                "aafp", "The AAFP is actively working toward addressing pain managment and opioid abuse problems in the U.S. through advocacy, collaboration, and education."),
                new Resource("Center for Disease Control and Prevention", "https://www.cdc.gov/drugoverdose/index.html", "cdc", 
                        "CDC raises awareness about the risks of opioid misuse and abuse empowering the people to make safe choices."), 
                new Resource("U. S. Department of Health & Human Resources", "https://www.hhs.gov/opioids/prevention/index.html", 
                "hhs", "HHS issued over $800 million in grants in 2017 to support treatment, prevention, and recovery, while making it easier for "
                        + "states to receive waivers to cover treatment through their Medicaid programs. ")));

    
    public List<Resource> getResources() {
        return resources;
    }

    
    
}