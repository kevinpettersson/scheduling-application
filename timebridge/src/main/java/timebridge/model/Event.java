package timebridge.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class Event {
    @Id
    private String id;
    
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
    
    public Interval getInterval(){
        return this.interval;
    }
 
    public String getActivity(){
        return this.activity;
    }       
    
    public ArrayList<Location> getLocations(){
        return this.locations;
    }
}

