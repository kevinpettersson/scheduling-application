package timebridge.model.event;

import timebridge.model.event.component.Attendee;
import timebridge.model.event.component.Interval;
import timebridge.model.event.component.Location;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class PersonalEvent implements Event {
    @Id
    private String id;
    private String summary;
    private String description;
    private Interval interval;
    private ArrayList<Location> locations;
    private ArrayList<Attendee> attendees;
    private Boolean visibility;

    public PersonalEvent(String summary, String description, Interval interval, ArrayList<Location> locations,
            ArrayList<Attendee> attendees) {
        this.id = new ObjectId().toHexString();
        this.summary = summary;
        this.description = description;
        this.interval = interval;
        this.locations = locations;
        this.attendees = attendees;
        this.visibility = true;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
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
}
