package timebridge.model.event.decorator;

import java.util.ArrayList;
import java.util.HashMap;

import timebridge.model.event.Event;
import timebridge.model.event.EventDecoratorType;
import timebridge.model.event.component.Locale;

public class LocaleDecorator extends EventDecorator {
    private ArrayList<Locale> locale;

    public LocaleDecorator(Event decoratedEvent, ArrayList<Locale> locale) {
        super(decoratedEvent);
        this.locale = locale;
    }

    @Override
    public String getLocation() {
        String locales = new String();
        String result = super.getLocation();

        for (Locale l : locale) {
            locales += "(" + l.getBuilding() + ", " + l.getRoom() + ") , ";
        }
        
        return result.isEmpty() ? locales : result + " - " + locales;
    }

    @Override
    public HashMap<EventDecoratorType, Object> getDecoratorProps() {
        HashMap<EventDecoratorType, Object> map = super.getDecoratorProps();
        map.put(EventDecoratorType.LOCALE, locale);
        return map;
    }
}
