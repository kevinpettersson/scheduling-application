package timebridge.model.event;
import java.util.ArrayList;

import timebridge.model.event.component.Attendee;
import timebridge.model.event.component.Interval;
import timebridge.model.event.component.Location;

public interface Event {

    public String getId();

    public Interval getInterval();
    public void setInterval(Interval interval);

    public ArrayList<Location> getLocations();
    public void setLocations(ArrayList<Location> location);

    public ArrayList<Attendee> getAttendees();
    public void setAttendees(ArrayList<Attendee> attendee);
    
    public Boolean getVisibility();
    public void setVisibility(Boolean visibility);

}

