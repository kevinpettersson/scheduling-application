package timebridge.model.event;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import timebridge.model.event.component.Attendee;
import timebridge.model.event.component.Interval;
import timebridge.model.event.schema.EventSchema;

/**
 * This interface represents an event, with summary, description, location, interval and attendees.
 */
@JsonDeserialize(as = DefaultEvent.class)
public interface Event {

    public String getId();

    public String getSummary();
    public void setSummary(String summary);

    public String getDescription();
    public void setDescription(String description);

    public String getLocation();
    public void setLocation(String location);

    public Interval getInterval();
    public void setInterval(Interval interval);

    public ArrayList<Attendee> getAttendees();
    public void setAttendees(ArrayList<Attendee> attendee);
    
    public Boolean getVisibility();
    public void setVisibility(Boolean visibility);

    public EventSchema getSchema();
    public void setSchema(EventSchema schema);

    public HashMap<EventDecoratorType, Object> getDecorators();
}
