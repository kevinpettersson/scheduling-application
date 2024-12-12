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
    private String summary;
    private String description;
    private Interval interval;
    private ArrayList<Location> locations;
    private ArrayList<Attendee> attendees;
    private Boolean visibility;

    private Course course;
    private String activity;

    public TimeEditEvent(Course course, String activity, Interval interval, ArrayList<Location> locations,
            ArrayList<Attendee> attendees) {
        this.id = new ObjectId().toHexString();
        this.course = course;
        this.activity = activity;
        this.interval = interval;
        this.locations = locations;
        this.attendees = attendees;
        this.visibility = true;
    }

    public TimeEditEvent() {
        super();
        this.id = new ObjectId().toHexString();
        this.course = new Course();
        this.activity = new String();
        this.interval = new Interval();
        this.locations = new ArrayList<>();
        this.attendees = new ArrayList<>();
        this.visibility = true;
    }
 

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Interval getInterval() {
        return interval;
    }

    @Override
    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    @Override
    public ArrayList<Location> getLocations() {
        return locations;
    }

    @Override
    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    @Override
    public ArrayList<Attendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(ArrayList<Attendee> attendees) {
        this.attendees = attendees;
    }

    @Override
    public Boolean getVisibility() {
        return visibility;
    }

    @Override
    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

}
