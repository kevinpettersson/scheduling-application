package timebridge.model.event;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import timebridge.model.event.component.Interval;
import timebridge.model.event.component.Location;

public interface Event {

    public String getId();

    public Interval getInterval();
    public void setInterval(Interval interval);

    public ArrayList<Location> getLocation();
    public void setLocation(ArrayList<Location> location);

    public Boolean getVisibility();
    public void setVisibility(Boolean visibility);

    public String getAttendee();
    public void setAttendee(String attendee);
}
