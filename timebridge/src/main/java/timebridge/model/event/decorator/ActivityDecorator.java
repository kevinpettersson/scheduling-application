package timebridge.model.event.decorator;

import java.util.HashMap;
import java.util.List;

import timebridge.model.event.Event;
import timebridge.model.event.EventDecoratorType;
import timebridge.model.event.schema.EventSchema;

public class ActivityDecorator extends EventDecorator {
    private String activity;

    public ActivityDecorator(Event decoratedEvent, String activity) {
        super(decoratedEvent);
        this.activity = activity;
    }

    @Override
    public String getSummary() {
        StringBuilder summary = new StringBuilder();
        List<EventSchema.SummaryAttribute> schema = decoratedEvent.getSchema().getSummarySchema();

        if (schema.isEmpty()) {
            return decoratedEvent.getSummary();
        }

        for (EventSchema.SummaryAttribute attribute : schema) {
            switch (attribute) {
                case ACTIVITY:
                    summary.append(activity);
                    break;
                default:
                    summary.append(decoratedEvent.getSummary());
                    break;
            }
        }

        return summary.toString();
    }

    @Override
    public String getDescription() {
        StringBuilder desc = new StringBuilder();
        List<EventSchema.DescriptionAttribute> schema = decoratedEvent.getSchema().getDescriptionSchema();

        if (schema.isEmpty()) {
            return decoratedEvent.getDescription();
        }

        for (EventSchema.DescriptionAttribute attribute : schema) {
            switch (attribute) {
                case ACTIVITY:
                    desc.append(activity);
                    break;
                default:
                    desc.append(decoratedEvent.getDescription());
                    break;
            }

            // Add separator, but only once
            if (schema.indexOf(attribute) < schema.size() - 1) {
                desc.append(" - ");
            }

        }

        return desc.toString();
    }

    @Override
    public HashMap<EventDecoratorType, Object> getDecorators() {
        HashMap<EventDecoratorType, Object> map = super.getDecorators();
        map.put(EventDecoratorType.ACTIVITY, activity);
        return map;
    }
}
