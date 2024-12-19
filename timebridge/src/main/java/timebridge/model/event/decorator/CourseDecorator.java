package timebridge.model.event.decorator;

import java.util.HashMap;
import java.util.List;

import timebridge.model.event.Event;
import timebridge.model.event.component.Course;
import timebridge.model.event.EventDecoratorType;
import timebridge.model.event.schema.EventSchema;

/**
 * This class represents a decorator for an event that adds a course to the event.
 */
public class CourseDecorator extends EventDecorator {
    private final Course course;

    public CourseDecorator(Event decoratedEvent, Course course) {
        super(decoratedEvent);
        this.course = course;
    }

    /**
     * Returns the customized summary of an event by incorporating the course name and code field based on the schema attributes.
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

        for (EventSchema.SummaryAttribute attribute : schema) {
            switch (attribute) {
                case COURSE_NAME:
                    summary.append(course.getName());
                    break;
                case COURSE_CODE:
                    summary.append(course.getCode());
                    break;
                default:
                    summary.append(decoratedEvent.getSummary());
                    break;
            }
        }

        summary.append(" - ");

        return summary.toString();
    }

    /**
     * Returns the customized description of the event by incorporating the course name and code field based on the schema attributes.
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

        if(schema.isEmpty()) {
            return decoratedEvent.getDescription();
        }

        for (EventSchema.DescriptionAttribute attribute : schema) {
            switch (attribute) {
                case COURSE_NAME:
                    desc.append(course.getName());
                    break;
                case COURSE_CODE:
                    desc.append(course.getCode());
                    break;
                default:
                    desc.append(decoratedEvent.getDescription());
                    break;
            }

            desc.append(" - ");
        }

        return desc.toString();
    }

    /**
     * Returns a map of decorators with an added entry to the HashMap with the decorator type and the value of the course field.
     * @return the map of decorators
     *
     * @since 2024-12-19
     * @author Group 12
     */
    @Override
    public HashMap<EventDecoratorType, Object> getDecorators() {
        HashMap<EventDecoratorType, Object> map = super.getDecorators();
        map.put(EventDecoratorType.COURSE, course);
        return map;
    }
}
