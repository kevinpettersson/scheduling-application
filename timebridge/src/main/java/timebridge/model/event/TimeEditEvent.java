package timebridge.model.event;

import java.util.ArrayList;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import timebridge.model.event.component.Attendee;
import timebridge.model.event.component.Course;
import timebridge.model.event.component.Interval;
import timebridge.model.event.component.Location;

public class TimeEditEvent implements Event {
    @Id
    private String id;
    private Course course;
    private String activity;
    private Interval interval;
    private ArrayList<Location> locations;
    private ArrayList<Attendee> attendees;
    private Boolean visibility;
    
    
    public TimeEditEvent(Course course, String activity, Interval interval, ArrayList<Location> locations,
    ArrayList<Attendee> attendees) {
        this.id = new ObjectId().toHexString();
        this.course = course;
        this.activity = activity;
        this.interval = interval;
        this.locations = locations;
        this.visibility = true;
        this.attendees = attendees;
    }
    
    public String getId() {
        return this.id;
    }
    
    
    public void setAttendee(String attendee) {
        // Implementation here
    }

    public String getAttendee() {
        // Implementation here
        return null;
    }

    public void setLocation(ArrayList<Location> location) {
        // Implementation here
    }

    public ArrayList<Location> getLocation() {
        // Implementation here
        return null;
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

    public ArrayList<Attendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(ArrayList<Attendee> attendees) {
        this.attendees = attendees;
    }
}
