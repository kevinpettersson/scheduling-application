package timebridge.model.observers;

import timebridge.model.Calendar;

public class ICalObserver implements Observer {
    CalendarSerializer serializer = New Calendar
    String iCal;

    public void update(Calendar calendar){
        
    }

    public String getICal() {
        return this.iCal;
    }
}