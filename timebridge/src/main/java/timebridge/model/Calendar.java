package timebridge.model;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

public class Calendar {
    @Id
    private String id;

    private String name;
    private Format format;
    private ArrayList<Event> events;

    public Calendar() {
        this.id = new ObjectId().toHexString();
        this.name = new String();
        this.events = new ArrayList<>();
        this.format = new Format();
    }

    public Calendar(String name, ArrayList<Event> events) {
        this.id = new ObjectId().toHexString();
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

    public void setId(ObjectId id) {
        this.id = id.toHexString();
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

    public void deleteEvent(String eventId){
        for (Event event : this.events) {
            if(event.getId().equals(eventId)){
                events.remove(event);
                return;
            }
        }
    }

    // Replaces existing event with new specification if id matches.
    // If event has no id, set a new id and add event to calendar.
    public void saveEvent(Event event){
        String Id = event.getId();

        if(Id != null && !Id.isEmpty()){
            for (int i = 0; i < events.size(); i++) {
                if(events.get(i).getId().equals(Id)){
                    events.set(i, event);
                    return;
                }
            }
        }
        if(Id == null || Id.isEmpty()){
            event.setId(new ObjectId());
        }
        addEvent(event);
    }

    // Filters events based on course and activity settings
    public void filterEvents(ArrayList<String> codeFilter, ArrayList<String> activityFilter) {
        for (Event event : this.events) {
            boolean courseMatch = codeFilter == null || codeFilter.isEmpty()
                    || codeFilter.contains(event.getCourse().getCode());
            boolean activityMatch = activityFilter == null || activityFilter.isEmpty()
                    || activityFilter.contains(event.getActivity());
            if (courseMatch && activityMatch) {
                event.setVisibility(true);
            } else {
                event.setVisibility(false);
            }
        }
    }
}