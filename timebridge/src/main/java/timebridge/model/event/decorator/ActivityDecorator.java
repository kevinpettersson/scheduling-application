package timebridge.model.event.decorator;

import java.util.HashMap;

import timebridge.model.event.Event;
import timebridge.model.event.EventDecoratorType;

public class ActivityDecorator extends EventDecorator {
    private String activity;

    public ActivityDecorator(Event decoratedEvent, String activity) {
        super(decoratedEvent);
        this.activity = activity;
    }

    @Override
    public String getSummary() {
        String result = super.getSummary();
        return result.isEmpty() ? activity : result + " - " + activity;
    }

    @Override
    public HashMap<EventDecoratorType, Object> getDecoratorProps() {
        HashMap<EventDecoratorType, Object> map = super.getDecoratorProps();
        map.put(EventDecoratorType.ACTIVITY, activity);
        return map;
    }
}
