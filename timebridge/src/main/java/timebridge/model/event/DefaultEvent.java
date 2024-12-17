package timebridge.model.event;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import timebridge.model.event.component.Attendee;
import timebridge.model.event.component.Interval;

public class DefaultEvent implements Event {

    @Id
    private String id;

    private String summary;
    private String description;
    private String location;
    private Interval interval;
    private ArrayList<Attendee> attendees;
    private Boolean visibility;
    private HashMap<EventDecoratorType, Object> decoratorProps;

    public DefaultEvent() {
        this.id = new ObjectId().toHexString();
        this.summary = new String();
        this.description = new String();
        this.location = new String();
        this.interval = new Interval();
        this.attendees = new ArrayList<>();
        this.visibility = true;
        this.decoratorProps = new HashMap<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getSummary() {
        return summary;
    }

    @Override
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public Interval getInterval() {
        return interval;
    }

    @Override
    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    @Override
    public ArrayList<Attendee> getAttendees() {
        return attendees;
    }

    @Override
    public void setAttendees(ArrayList<Attendee> attendee) {
        this.attendees = attendee;
    }

    @Override
    public Boolean getVisibility() {
        return visibility;
    }

    @Override
    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public HashMap<EventDecoratorType, Object> getDecoratorProps() {
        return decoratorProps;
    }

    @Override
    public void setDecoratorProps(HashMap<EventDecoratorType, Object> decoratorProps) {
        this.decoratorProps = decoratorProps;
    }
}
