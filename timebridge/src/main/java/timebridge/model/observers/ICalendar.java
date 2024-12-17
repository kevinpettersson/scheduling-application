package timebridge.model.observers;

import java.io.IOException;

import timebridge.model.Calendar;
import timebridge.services.CalendarSerializer;

public class ICalendar implements Observer {
    CalendarSerializer serializer;
    String iCal;

    public ICalendar(Calendar calendar) {
        try {
            this.serializer = new CalendarSerializer();
            this.iCal = serializer.serialize(calendar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Calendar calendar){
        try {
            this.iCal = serializer.serialize(calendar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getICal() {
        return this.iCal;
    }
}