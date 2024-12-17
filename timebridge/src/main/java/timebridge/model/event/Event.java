package timebridge.model.event;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import timebridge.model.event.component.Attendee;
import timebridge.model.event.component.Interval;

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

    public HashMap<EventDecoratorType, Object> getDecoratorProps();
    public void setDecoratorProps(HashMap<EventDecoratorType, Object> decoratorProps);
}
