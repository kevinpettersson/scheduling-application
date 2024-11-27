package timebridge.converters;

import timebridge.model.Calendar;

public abstract class CalendarParser {

    // this is our CalendarBuilder
    
    public static Calendar parse(String iCal) {
        // add parser logic here
        return new Calendar();
    }
}
