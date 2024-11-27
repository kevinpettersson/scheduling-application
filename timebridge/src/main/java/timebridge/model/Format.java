package timebridge.model;

import java.util.ArrayList;

public class Format {

    private ArrayList<String> summary;
    private ArrayList<String> description;
    private ArrayList<String> location;

    public Format() {
        this.summary = new ArrayList<>();
        this.description = new ArrayList<>();
        this.location = new ArrayList<>();
    }

    // Regular constructor
    public Format(ArrayList<String> summary, ArrayList<String> description, ArrayList<String> location) {
        this.summary = summary;
        this.description = description;
        this.location = location;
    }

    public ArrayList<String> getSummary() {
        return summary;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public ArrayList<String> getLocation() { 
        return location; 
    }

    public void setSummary(ArrayList<String> summary) {
        this.summary = summary;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    public void setLocation(ArrayList<String> location) {
        this.location = location;
    }
}
