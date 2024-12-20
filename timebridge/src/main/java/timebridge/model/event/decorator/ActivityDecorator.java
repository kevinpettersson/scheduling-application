package timebridge.model.event.decorator;

import java.util.HashMap;
import java.util.List;

import timebridge.model.event.Event;
import timebridge.model.event.EventDecoratorType;
import timebridge.model.event.schema.EventSchema;

/**
 * This class represents a decorator for an event that adds an activity to the event.
 */
public class ActivityDecorator extends EventDecorator {
    private String activity;

    public ActivityDecorator(Event decoratedEvent, String activity) {
        super(decoratedEvent);
        this.activity = activity;
    }

    /**
     * Returns the customized summary of an event by incorporating the activity field based on the schema attributes.
     *
     * @return the summary of the event
     *
     * @since 2024-12-19
     * @author Group 12
     */
    @Override
    public String getSummary() {
        StringBuilder summary = new StringBuilder();
        List<EventSchema.SummaryAttribute> schema = decoratedEvent.getSchema().getSummarySchema();

        boolean activityAppended = false;
        boolean baseSummaryAppended = false;

        if (schema.isEmpty()) {
            return decoratedEvent.getSummary();
        }

        for (EventSchema.SummaryAttribute attribute : schema) {
            switch (attribute) {
                case ACTIVITY:
                    if (!activityAppended) {
                        appendAttribute(summary, activity);
                        activityAppended = true;
                    }
                    break;
                default:
                    if (!baseSummaryAppended) {
                        appendAttribute(summary, decoratedEvent.getSummary());
                        baseSummaryAppended = true;
                    }
                    break;
            }
        }
        String result = summary.toString().replaceAll("-\\s*-\\s*", "- ");
        return result;
    }

    private void appendAttribute(StringBuilder input, String value) {
        if (!input.isEmpty()) {
            input.append(" - ");
            input.append(value);
        } else {
            input.append(value);
        }
    }

    /**
     * Retunrs the customized description of an event by incorporating the activity field based on the schema attributes.
     *
     * @return the description of the event
     *
     * @since 2024-12-19
     * @author Group 12
     */
    @Override
    public String getDescription() {
        StringBuilder desc = new StringBuilder();
        List<EventSchema.DescriptionAttribute> schema = decoratedEvent.getSchema().getDescriptionSchema();
        boolean activityAppended = false;
        boolean baseDescriptionAppended = false;

        if (schema.isEmpty()) {
            return decoratedEvent.getDescription();
        }

        for (EventSchema.DescriptionAttribute attribute : schema) {
            switch (attribute) {
                case ACTIVITY:
                    if(!activityAppended) {
                        appendAttribute(desc, activity);
                        activityAppended = true;
                    }
                    break;
                default:
                    if(!baseDescriptionAppended) {
                        appendAttribute(desc, decoratedEvent.getDescription());
                        baseDescriptionAppended = true;
                    }
                    break;
            }

            // Add separator, but only once
            if (schema.indexOf(attribute) < schema.size() - 1) {
                desc.append(" - ");
            }
        }
        return desc.toString();
    }

    /**
     * Returns a map of decorators with an added entry to the HashMap with the decorator type and the value of the activity field.
     *
     * @return a map of event decorators.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    @Override
    public HashMap<EventDecoratorType, Object> getDecorators() {
        HashMap<EventDecoratorType, Object> map = super.getDecorators();
        map.put(EventDecoratorType.ACTIVITY, activity);
        return map;
    }
}