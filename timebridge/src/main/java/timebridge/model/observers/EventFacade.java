package timebridge.model.facade;

import timebridge.model.event.TimeEditEvent;
import timebridge.model.event.PersonalEvent;

public class EventFacade implements Facade {
    private final String id;
    private String summary;
    private String description;
    private String start;
    private String end;
    private String location;
    private String attendees;


    public EventFacade(TimeEditEvent event) {
        this.id = event.getId();
        this.summary = 
    }

    public EventFacade(PersonalEvent event) {

    }


    
    public String serialize() {
        return new String();
    }
}