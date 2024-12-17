package timebridge.model.event.decorator;
import java.util.ArrayList;
import java.util.HashMap;

import timebridge.model.event.Event;
import timebridge.model.event.EventDecoratorType;
import timebridge.model.event.component.Attendee;
import timebridge.model.event.component.Interval;

public abstract class EventDecorator implements Event {
    private final Event decoratedEvent;

    public EventDecorator(Event decoratedEvent) {
        this.decoratedEvent = decoratedEvent;
    }

    @Override
    public String getId() {
        return decoratedEvent.getId();
    }

    @Override
    public String getSummary() {
        return decoratedEvent.getSummary();
    }

    @Override
    public void setSummary(String summary) {
        decoratedEvent.setSummary(summary);
    }

    @Override
    public String getDescription() {
        return decoratedEvent.getDescription();
    }

    @Override
    public void setDescription(String description) {
        decoratedEvent.setDescription(description);
    }

    @Override
    public String getLocation() {
        return decoratedEvent.getLocation();
    }

    @Override
    public void setLocation(String location) {
        decoratedEvent.setLocation(location);
    }

    @Override
    public Interval getInterval() {
        return decoratedEvent.getInterval();
    }

    @Override
    public void setInterval(Interval interval) {
        decoratedEvent.setInterval(interval);
    }

    @Override
    public ArrayList<Attendee> getAttendees() {
        return decoratedEvent.getAttendees();
    }

    @Override
    public void setAttendees(ArrayList<Attendee> attendee) {
        decoratedEvent.setAttendees(attendee);
    }

    @Override
    public Boolean getVisibility() {
        return decoratedEvent.getVisibility();
    }

    @Override
    public void setVisibility(Boolean visibility) {
        decoratedEvent.setVisibility(visibility);
    }

    @Override
    public HashMap<EventDecoratorType, Object> getDecoratorProps() {
        return decoratedEvent.getDecoratorProps();
    }

    @Override
    public void setDecoratorProps(HashMap<EventDecoratorType, Object> props) {
        decoratedEvent.setDecoratorProps(props);
    }
}
