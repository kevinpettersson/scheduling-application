package timebridge.model;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import timebridge.model.event.Event;
import timebridge.model.observers.Observer;
import timebridge.model.event.*;

public class Calendar {
    @Id
    private final String id;

    private String name;
    private ArrayList<Event> events;
    private ArrayList<Observer> observers;
    private Format format;

    public Calendar() {
        this.id = new ObjectId().toHexString();
        this.name = new String();
        this.events = new ArrayList<>();
        this.observers = new ArrayList<Observer>();
    }

    public Calendar(String name, ArrayList<Event> events) {
        this.id = new ObjectId().toHexString();
        this.name = name;
        this.events = events;
        this.observers = new ArrayList<Observer>();
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

    public void setName(String name) {
        this.name = name;
        notifyObservers();
    }

    public void saveEvent(Event event) {
        // Update event if already present
        for (Event e : events) {
            if (event.getId().equals(e.getId())) {
                e = event;
                notifyObservers();
                return;
            }
        }

        // otherwise save as new event
        events.add(event);
        notifyObservers();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public Format getFormat() {
        return this.format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public void filterEvents(ArrayList<String> codeFilter, ArrayList<String> activityFilter) {
        for (Event event : this.events) {
            // Skip personal events
            if (event instanceof PersonalEvent) {
                continue;
            }

            boolean courseMatch = codeFilter == null || codeFilter.isEmpty()
                    || codeFilter.contains(((TimeEditEvent) event).getCourse().getCode());
            boolean activityMatch = activityFilter == null || activityFilter.isEmpty()
                    || activityFilter.contains(((TimeEditEvent) event).getActivity());

            if (courseMatch && activityMatch) {
                event.setVisibility(true);
            } else {
                event.setVisibility(false);
            }
        }
    }

    public void deleteEvent(String id) {
        for (Event event : events) {
            if (event.getId().equals(id)) {
                events.remove(event);
                notifyObservers();
                return;
            }
        }
    }
}