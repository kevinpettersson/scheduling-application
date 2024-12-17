package timebridge.model.event.decorator;

import java.util.HashMap;

import timebridge.model.event.Event;
import timebridge.model.event.component.Course;
import timebridge.model.event.EventDecoratorType;

public class CourseDecorator extends EventDecorator {
    private final Course course;

    public CourseDecorator(Event decoratedEvent, Course course) {
        super(decoratedEvent);
        this.course = course;
    }

    @Override
    public String getSummary() {
        String result = super.getSummary();
        return result.isEmpty() ? course.getCode() : result + " - " + course.getCode();
    }

    @Override
    public String getDescription() {
        String result = super.getDescription();
        return result.isEmpty() ? course.getName() : result + " - " + course.getName();
    }
    
    @Override
    public HashMap<EventDecoratorType, Object> getDecoratorProps() {
        HashMap<EventDecoratorType, Object> map = super.getDecoratorProps();
        map.put(EventDecoratorType.COURSE, course);
        return map;
    }
}
