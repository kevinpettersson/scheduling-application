package timebridge.model;

import java.util.ArrayList;

public class Settings {
    private ArrayList<String> courseFilter;
    private ArrayList<String> activityFilter;
    private ArrayList<String> summaryFormat;
    private ArrayList<String> descriptionFormat;
    private ArrayList<String> locationFormat;

    // Regular constructor
    public Settings(ArrayList<String> courseFilter, ArrayList<String> activityFilter, ArrayList<String> summaryFormat,
            ArrayList<String> descriptionFormat, ArrayList<String> locationFormat) {
        this.courseFilter = courseFilter;
        this.activityFilter = activityFilter;
        this.summaryFormat = summaryFormat;
        this.descriptionFormat = descriptionFormat;
        this.locationFormat = locationFormat;
    }

    public ArrayList<String> getCourseFilter() {
        return courseFilter;
    }

    public void setCourseFilter(ArrayList<String> courseFilter) {
        this.courseFilter = courseFilter;
    }

    public ArrayList<String> getActivityFilter() {
        return activityFilter;
    }

    public void setActivityFilter(ArrayList<String> activityFilter) {
        this.activityFilter = activityFilter;
    }

    public ArrayList<String> getSummaryFormat() {
        return summaryFormat;
    }

    public void setSummaryFormat(ArrayList<String> summaryFormat) {
        this.summaryFormat = summaryFormat;
    }

    public ArrayList<String> getDescriptionFormat() {
        return descriptionFormat;
    }

    public void setDescriptionFormat(ArrayList<String> descriptionFormat) {
        this.descriptionFormat = descriptionFormat;
    }

    public ArrayList<String> getLocationFormat() {
        return locationFormat;
    }

    public void setLocationFormat(ArrayList<String> locationFormat) {
        this.locationFormat = locationFormat;
    }
}
