package timebridge.model;

import java.util.ArrayList;

public class SchoolEvent extends EventDecorator {
    private Course course;
    private ArrayList<Location> location;
    private EventFormat format;

    public SchoolEvent(EventInterface decoratedEvent) {
        super(decoratedEvent);
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    public ArrayList<Location>  getLocations(){
        return this.location;
    }
    public void setLocation(ArrayList<Location> location){
        this.location = location;
    }
    public void setFormat(EventFormat format){
        this.format = format;
    }
    public EventFormat getFormat(){
        return this.format;
    }

}
