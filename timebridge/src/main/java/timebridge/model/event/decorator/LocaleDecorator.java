package timebridge.model.event.decorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import timebridge.model.event.Event;
import timebridge.model.event.EventDecoratorType;
import timebridge.model.event.component.Locale;
import timebridge.model.event.schema.EventSchema;

import static timebridge.model.event.schema.EventSchema.LocationAttribute.BUILDING;
import static timebridge.model.event.schema.EventSchema.LocationAttribute.ROOM;


public class LocaleDecorator extends EventDecorator {
    private ArrayList<Locale> locales;

    public LocaleDecorator(Event decoratedEvent, ArrayList<Locale> locales) {
        super(decoratedEvent);
        this.locales = locales;
    }

    @Override
    public String getLocation() {
        StringBuilder location = new StringBuilder();
        List<EventSchema.LocationAttribute> schema = decoratedEvent.getSchema().getLocationSchema();

        if (schema.isEmpty()) {
            return location.toString();
        }

        location.append(decoratedEvent.getLocation());

        if (!location.isEmpty()) {
            location.append(" ┆ ");
        }

        for (Locale locale : locales) {
            // Schema contains the building attribute and the location does not already contain the building
            if (schema.contains(BUILDING) && !location.toString().contains(locale.getBuilding())) {
                location.append(locale.getBuilding());

                // Add separator if rooms are to be included after building
                if (schema.contains(ROOM)) {
                    location.append(" ➤ ");
                }
            }

            // Schema contains the room attribute and the location does not already contain the room
            if (schema.contains(ROOM) && !location.toString().contains(locale.getRoom())) {
                location.append(locale.getRoom());
            }

            // Add separator if there are more locales to be included but if the next locale has a
            // different building, add a different separator
            if (locales.indexOf(locale) < locales.size() - 1) {
                if (!locale.getBuilding().equals(locales.get(locales.indexOf(locale) + 1).getBuilding())) {
                    location.append(" ┆ ");
                } else {
                    location.append(" • ");
                }
            }
        }

        return location.toString();
    }

    @Override
    public HashMap<EventDecoratorType, Object> getDecorators() {
        HashMap<EventDecoratorType, Object> map = super.getDecorators();
        map.put(EventDecoratorType.LOCALE, locales);
        return map;
    }
}
