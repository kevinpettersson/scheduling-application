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


/**
 * This class represents a decorator for an event that adds a locale to the event.
 */
public class LocaleDecorator extends EventDecorator {
    private ArrayList<Locale> locales;

    public LocaleDecorator(Event decoratedEvent, ArrayList<Locale> locales) {
        super(decoratedEvent);
        this.locales = locales;
    }

    /**
     * Returns the location of the event with the locale added to the location.
     * Separates for each unique building and separates buildings and rooms if the schema contains both.
     *
     * @return the location of the event with the locale added.
     *
     * @since 2024-12-19
     * @version 1.0
     */
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
            if (schema.contains(BUILDING) && !location.toString().contains(locale.getBuilding())) {
                location.append(locale.getBuilding());

                if (schema.contains(ROOM)) {
                    location.append(" ➤ ");
                }
            }

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

    /**
     * Returns a map of decorators with an added entry to the HashMap with the decorator type and the value of the locale field.
     *
     * @return a map of event decorators.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    @Override
    public HashMap<EventDecoratorType, Object> getDecorators() {
        HashMap<EventDecoratorType, Object> map = super.getDecorators();
        map.put(EventDecoratorType.LOCALE, locales);
        return map;
    }
}
