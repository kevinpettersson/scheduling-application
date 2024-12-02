package timebridge.model;

import java.util.ArrayList;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
public class Event {
    @Id
    private String id;
    private Course course;
    private String activity;
    private Interval interval;
    private ArrayList<Location> locations;
    private Boolean visibility;
    private ArrayList<Attendee> attendees;

    public Event() {
        this.id = new ObjectId().toHexString();
        this.course = new Course();
        this.activity = "";
        this.interval = new Interval();
        this.locations = new ArrayList<Location>();
        this.visibility = true;
    }

    public Event(Course course, String activity, Interval interval, ArrayList<Location> locations) {
        this.id = new ObjectId().toHexString();
        this.course = course;
        this.activity = activity;
        this.interval = interval;
        this.locations = locations;
        this.visibility = true;
        this.attendees = attendees;
    }

    public String getId(){
        return this.id;
    }
    
    public void setId(ObjectId id){
        this.id = id.toHexString();
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
