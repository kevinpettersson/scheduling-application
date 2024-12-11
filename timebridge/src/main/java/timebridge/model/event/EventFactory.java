package timebridge.model.event;

import timebridge.model.event.component.Attendee;
import timebridge.model.event.component.Course;
import timebridge.model.event.component.Interval;
import timebridge.model.event.component.Location;
import java.util.ArrayList;


public abstract class EventFactory {
    
    static public TimeEditEvent createTimeEditEvent(Course course, String activity, Interval interval, ArrayList<Location> locations,
        ArrayList<Attendee> attendees){
        return new TimeEditEvent(course, activity, interval, locations, attendees);
    }

    static public PersonalEvent createPersonalEvent(String summary, String description, Interval interval, ArrayList<Location> location, String attendees, Boolean visibility){
        return new PersonalEvent(summary, description, interval, location, attendees, visibility);
    }

}

