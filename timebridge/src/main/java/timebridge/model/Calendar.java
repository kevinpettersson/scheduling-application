package timebridge.model;

import java.util.ArrayList;
import org.springframework.data.annotation.Id;

import timebridge.converters.CalendarSerializer;

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
    
    public Calendar(String iCal){
       // add parser logic here 
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

    public void addEvent(Event event) {
        events.add(event);
    }

    public void addEvents(ArrayList<Event> events) {
        this.events.addAll(events);
    }

    public String toIcal() {
        // add ical parser logic here
        return CalendarSerializer.serialize(this);
    }

}