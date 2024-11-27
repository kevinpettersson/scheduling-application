package timebridge.model;

import java.util.ArrayList;

public class Event implements EventInterface {
    private String activity;
    private Interval interval;

    public Event() {
        this.activity = "";
        this.interval = new Interval();
    }

    public Event(String activity, Interval interval) {
        this.activity = activity;
        this.interval = interval;
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
}
