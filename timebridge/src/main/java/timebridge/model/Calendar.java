package timebridge.model;

import java.util.ArrayList;
import org.springframework.data.annotation.Id;

public class Calendar {
    @Id
    private String id;

    private String name;
    private ArrayList<Event> events;
    private Format format;

    public Calendar() {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = new String();
        this.events = new ArrayList<>();
        this.format = new Format();
    }

    public Calendar(String name, ArrayList<Event> events) {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.events = events;
        this.format = new Format();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public ArrayList<Event> getEvents() {
        return events;
    }

    public Format getFormat() {
        return format;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    // Filters events based on course and activity settings
    public void filterEvents(ArrayList<String> codeFilter, ArrayList<String> activitiyFilter) {
        for (Event event : this.events) {
            if (!codeFilter.contains(event.getCourse().getCode()) || !activitiyFilter.contains(event.getActivity())) {
                event.setVisibility(false);
            }
        }
    }
}