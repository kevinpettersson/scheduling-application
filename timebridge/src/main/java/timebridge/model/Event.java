package timebridge.model;

import java.util.ArrayList;

public class Event {
    private Course course;
    private String activity;
    private Interval interval;
    private ArrayList<Location> locations;
    private Boolean visibility;
    private EventFormat format;

    public Event() {
        this.course = new Course();
        this.activity = "";
        this.interval = new Interval();
        this.locations = new ArrayList<Location>();
        this.visibility = true;
        this.format = new EventFormat();
    }

    public Event(Course course, String activity, Interval interval, ArrayList<Location> locations) {
        this.course = course;
        this.activity = activity;
        this.interval = interval;
        this.locations = locations;
        this.visibility = true;
        this.format = new EventFormat();
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getActivity() {
        return this.activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Interval getInterval() {
        return this.interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public ArrayList<Location> getLocations() {
        return this.locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public Boolean getVisibility() {
        return this.visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public void setFormat(EventFormat format) {
        this.format = format;
    }

    public EventFormat getFormat() {
        return this.format;
    }
}
