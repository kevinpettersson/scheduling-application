package timebridge.model.event;

import java.util.ArrayList;

import timebridge.model.event.component.Attendee;
import timebridge.model.event.component.Course;
import timebridge.model.event.component.Interval;
import timebridge.model.event.component.Locale;
import timebridge.model.event.decorator.ActivityDecorator;
import timebridge.model.event.decorator.CourseDecorator;
import timebridge.model.event.decorator.LocaleDecorator;

/**
 * Factory class for creating events.
 * This class provides methods to create empty, personal and time edit events.
 */
public abstract class EventFactory {

    public Event createEmptyEvent(Interval interval) {
        Event event = new DefaultEvent();
        event.setInterval(interval);
        return event;
    }
    
    public static Event createPersonalEvent(Interval interval, String summary, String description, String location, ArrayList<Attendee> attendees) {
        Event event = new DefaultEvent();
        event.setInterval(interval);
        event.setSummary(summary);
        event.setDescription(description);
        event.setLocation(location);
        event.setAttendees(attendees);
        return event;
    }

    public static Event createTimeEditEvent(Interval interval, Course course, String activity, ArrayList<Locale> locale) {
        Event event = new DefaultEvent();
        event.setInterval(interval);
        event = new CourseDecorator(event, course);
        event = new ActivityDecorator(event, activity);
        event = new LocaleDecorator(event, locale);
        return event;
    }
}
