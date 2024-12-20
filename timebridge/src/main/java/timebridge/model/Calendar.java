package timebridge.model;

import java.io.IOException;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import timebridge.model.event.Event;
import timebridge.model.event.EventDecoratorType;
import timebridge.model.event.component.Attendee;
import timebridge.model.event.component.Course;
import timebridge.model.event.schema.EventSchema;

/**
 * <p>Represents a calendar that holds a list of events. </p>
 * <p> This class provides methods to manage events, including adding, deleting,
 * filtering, and finding events. It supports assigning attendees to course
 * events and applying schemas to events. </p>
 * <p> Each instance has a unique identifier, a name, and a list of events. </p>
 *
 * @author Group 12
 * @since 2024-12-19
 */
public class Calendar {
    @Id
    private String id;

    private String name;
    private ArrayList<Event> events;

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

    /**
     * <p> Filters events by course code and activity. </p>
     * <p> This method checks each event in the list and sets their visibility accordingly,
     * depending on whether each event matches the filters.</p>
     *
     * @param codeFilter a list of course codes to filter by.
     * @param activityFilter a list of activities to filter by.
     *
     * @author Group 12
     * @since 2024-12-19
     */
    public void filterEvents(ArrayList<String> codeFilter, ArrayList<String> activityFilter) {
        for (Event event : this.events) {
            // Skip default events as we can't filter them
            if (event.getDecorators().isEmpty()) {
                continue;
            }

            Course course = new Course();
            String activity = new String();

            if (event.getDecorators().containsKey(EventDecoratorType.COURSE)) {
                course = (Course) event.getDecorators().get(EventDecoratorType.COURSE);
            }

            if (event.getDecorators().containsKey(EventDecoratorType.ACTIVITY)) {
                activity = (String) event.getDecorators().get(EventDecoratorType.ACTIVITY);
            }

            if ((codeFilter.contains(course.getCode()) || codeFilter.isEmpty())
                    && (activityFilter.contains(activity) || activityFilter.isEmpty())) {
                event.setVisibility(true);
            } else {
                event.setVisibility(false);
            }
        }
    }

    /**
     * <p> Saves or updates and event in the calendar. </p>
     * <p>
     * If the event ID already exists, it updates the corresponding event.
     * Otherwise, it saves the event as a new event.
     * </p>
     *
     * @param event the event to be saved.
     *
     * @author Group 12
     * @since 2024-12-19
     */
    public void saveEvent(Event event) {
        for (Event e : events) {
            if (event.getId().equals(e.getId())) {
                e = event;
                return;
            }
        }
        events.add(event);
    }

    /**
     * <p> Deletes an event from the calendar. </p>
     * <p> Removes the event with the specified ID from the calendar. </p>
     *
     * @param id the ID of the event to be deleted.
     *
     * @author Group 12
     * @since 2024-12-19
     */
    public void deleteEvent(String id) {
        for (Event event : events) {
            if (event.getId().equals(id)) {
                events.remove(event);
                return;
            }
        }
    }

    /**
     * <p> Finds an event in the calendar by its ID. </p>
     *
     * @param id the ID of the event to be found.
     * @return {@link Event} object with the specified ID, or null if not found.
     * @throws IllegalArgumentException if the ID is null or empty.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    public Event findEvent(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Event ID must not be null or empty");
        }

        return events.stream()
                .filter(event -> id.equals(event.getId()))
                .findFirst()
                .orElse(null);
    }

    
    public void SetCourseAttendees(String courseCode, ArrayList<Attendee> attendees) throws IOException {
        boolean CourseCodeDoesNotExist = false;
        for (Event event : events) {
            // If event does not have a course, continue
            if (!event.getDecorators().containsKey(EventDecoratorType.COURSE)) {
                continue;
            }

            // Get course from event
            Course course = (Course) event.getDecorators().get(EventDecoratorType.COURSE);

            // If codes matches, add attendees
            if (course.getCode().equals(courseCode)) {
                event.setAttendees(attendees);
                CourseCodeDoesNotExist = true;
            }
        }
        if (CourseCodeDoesNotExist){
             throw new IllegalArgumentException("CourseCode does not exist");
        }
    }

    /**
     * <p> Applies a schema/format to all events in the calendar. </p>
     *
     * @param schema the schema to apply to the events.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    public void SetEventSchemas(EventSchema schema) {
        for (Event event : events) {
            event.setSchema(schema);
        }
    }
}