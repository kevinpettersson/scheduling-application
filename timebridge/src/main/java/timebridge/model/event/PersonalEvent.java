package timebridge.model.event;
import timebridge.model.event.component.Interval;
import timebridge.model.event.component.Location;
import org.bson.types.ObjectId;


import java.util.ArrayList;

public class PersonalEvent implements Event {
    private String summary;
    private String description;
    private Interval interval;
    private ArrayList<Location> locations;
    private String attendees;
    private Boolean visibility;
    private String id;

    public PersonalEvent() {

    }

    public PersonalEvent(String summary, String description, Interval interval, ArrayList<Location> locations, String attendees, Boolean visibility) {
        this.summary = summary;
        this.description = description;
        this.interval = interval;
        this.locations = locations;
        this.attendees = attendees;
        this.visibility = visibility;
        this.id = new ObjectId().toHexString();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String setDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }


    public void setAttendee(String attendee) {
        this.attendees = attendee;
    }


    public String getAttendee() {
        return attendees;
    }


    public void setInterval(Interval interval) {
        this.interval = interval;
    }


    public Interval getInterval() {
        return interval;
    }


    public void setLocation(ArrayList<Location> locations) {
        this.locations = locations;
    }


    public ArrayList<Location> getLocation() {
        return locations;
    }


    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public Boolean getVisibility() {
        return visibility;
    }

}
