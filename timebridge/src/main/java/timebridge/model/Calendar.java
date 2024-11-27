package timebridge.model;

import java.util.ArrayList;
import org.springframework.data.annotation.Id;

public class Calendar {
    @Id
    private String id;

    private String name;
    private ArrayList<EventInterface> events;

    public Calendar() {
        this.name = "";
        this.events = new ArrayList<>();
    }

    public Calendar(String name, ArrayList<EventInterface> events) {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.events = events;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<EventInterface> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<EventInterface> events) {
        this.events = events;
    }

    public void addEvent(EventInterface event) {
        events.add(event);
    }

    public void addEvents(ArrayList<EventInterface> events) {
        this.events.addAll(events);
    }
}