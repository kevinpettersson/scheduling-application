package timebridge.model.event.decorator;

import java.util.HashMap;
import java.util.List;

import timebridge.model.event.Event;
import timebridge.model.event.component.Course;
import timebridge.model.event.EventDecoratorType;
import timebridge.model.event.schema.EventSchema;

public class CourseDecorator extends EventDecorator {
    private final Course course;

    public CourseDecorator(Event decoratedEvent, Course course) {
        super(decoratedEvent);
        this.course = course;
    }

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
    
    @Override
    public HashMap<EventDecoratorType, Object> getDecorators() {
        HashMap<EventDecoratorType, Object> map = super.getDecorators();
        map.put(EventDecoratorType.COURSE, course);
        return map;
    }
}
