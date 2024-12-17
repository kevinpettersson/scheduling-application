package timebridge.model;

import java.util.ArrayList;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import timebridge.model.event.Event;
import timebridge.model.event.component.Course;
import timebridge.model.event.*;

public class Calendar {
    @Id
    private String id;

    private String name;
    private ArrayList<Event> events;
    private Format format;

    public Calendar() {
        this.id = new ObjectId().toHexString();
        this.name = new String();
        this.events = new ArrayList<>();
    }

    public Calendar(String name, ArrayList<Event> events) {
        this.id = new ObjectId().toHexString();
        this.name = name;
        this.events = events;
    }

    public String getId() {
        return id;
    }

    public void SetId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void saveEvent(Event event) {
        // Update event if already present
        for (Event e : events) {
            if (event.getId().equals(e.getId())) {
                e = event;
                return;
            }
        }

        // otherwise save as new event
        events.add(event);
    }

    public Format getFormat() {
        return this.format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public void filterEvents(ArrayList<String> codeFilter, ArrayList<String> activityFilter) {
        for (Event event : this.events) {
            // Skip default events as we can't filter them
            if (event.getDecoratorProps().isEmpty()) {
                continue;
            }

            // Init variables to filter by
            Course course = new Course(); 
            String activity = new String();

            // Check if event has course decorator
            if (event.getDecoratorProps().containsKey(EventDecoratorType.COURSE)) {
                course = (Course) event.getDecoratorProps().get(EventDecoratorType.COURSE);
            }

            // Check if event has activity decorator
            if (event.getDecoratorProps().containsKey(EventDecoratorType.ACTIVITY)) {
                activity = (String) event.getDecoratorProps().get(EventDecoratorType.ACTIVITY);
            }

            // filter by course code and activity
            if ((codeFilter.contains(course.getCode()) || codeFilter.isEmpty()) && (activityFilter.contains(activity) || activityFilter.isEmpty())) {
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
                return;
            }
        }
    }
}