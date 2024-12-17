package timebridge.model.observers;

import timebridge.model.Calendar;

public interface Observer {
    public void update(Calendar calendar);
}