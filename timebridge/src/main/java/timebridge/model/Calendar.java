package timebridge.model;

import org.springframework.data.annotation.Id;
import java.util.ArrayList;

public class Calendar {

    @Id
    private String id;
    private String name;
    private Interval interval;
    private ArrayList<Event> events;

    public Calendar(String name, Interval interval, ArrayList<Event> events) {
        this.name = name;
        this.interval = interval;
        this.events = new ArrayList<Event>();
    }

    public Calendar() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
