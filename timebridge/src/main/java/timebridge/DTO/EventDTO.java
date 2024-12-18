package timebridge.DTO;

import timebridge.model.event.component.Attendee;
import timebridge.model.event.component.Interval;

import java.util.ArrayList;

public class EventDTO {

    private String summary;

    private String description;

    private String location;

    private Interval interval; // Assuming this is a custom class to represent start and end time.

    private ArrayList<Attendee> attendees; // Use List instead of ArrayList for flexibility.

    // Default constructor
    public EventDTO() {}

    // Getters and Setters
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public ArrayList<Attendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(ArrayList<Attendee> attendees) {
        this.attendees = attendees;
    }
}