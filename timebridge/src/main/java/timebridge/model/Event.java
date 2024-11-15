package timebridge.model;

import java.util.ArrayList;

public class Event {
    private Course course;
    private String activity;
    private Interval interval;
    private ArrayList<Location> locations;

    public Event(Course course, String activity, Interval interval, ArrayList<Location> locations){
        this.course = course;
        this.activity = activity;
        this.interval = interval;
        this.locations = locations;
    }

    public Course getCourse(){
        return this.course;
    }

    public void setCourse(Course course){
        this.course = course;
    }

    public String getActivity(){
        return this.activity;
    }  

    public void setActivity(String activity){
        this.activity = activity;
    }
    
    public Interval getInterval(){
        return this.interval;
    }     

    public void setInterval(Interval interval){
        this.interval = interval;
    }
    
    public ArrayList<Location> getLocations(){
        return this.locations;
    }

    public void setLocations(ArrayList<Location> locations){
        this.locations = locations;
    }
}

