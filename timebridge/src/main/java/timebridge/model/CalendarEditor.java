package timebridge.model;

import java.util.*;

public class CalendarEditor {
    private Calendar calendar;
    private Settings settings;

    public CalendarEditor(Calendar calendar, Settings settings) {
        this.calendar = calendar;
        this.settings = settings;
    }

    // Builds a new Calendar with filtered and formatted events
    public Calendar build() {
        Calendar resultCalendar = new Calendar(calendar.getName(), new ArrayList<EventInterface>());
        resultCalendar.addEvents(filterEvents());
        formatEvents(resultCalendar);
        return resultCalendar;
    }

    // Filters events based on course and activity settings
    private ArrayList<EventInterface> filterEvents() {
        ArrayList<EventInterface> filteredEvents = new ArrayList<>();
        for (EventInterface event : calendar.getEvents()) {
            if (settings.getCourseFilter().contains(((SchoolEvent) event).getCourse().getCode())
                    && settings.getActivityFilter().contains(event.getActivity())) {
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }

    // Formats events in the result calendar
    private void formatEvents(Calendar resultCalendar) {
        for (EventInterface event : resultCalendar.getEvents()) {
            String summary = formatSummary(event);
            String description = formatDescription(event);
            String location = formatLocation(event);

            ((SchoolEvent)event).getFormat().setSummary(summary);
            ((SchoolEvent)event).getFormat().setDescription(description);
            ((SchoolEvent)event).getFormat().setLocation(location);
        }
    }

    // Formats the summary of an event based on settings
    private String formatSummary(EventInterface event) {
        StringBuilder summary = new StringBuilder();
        for (String field : settings.getSummaryFormat()) {
            appendField(summary, field, event);
            if (!isLastField(settings.getSummaryFormat(), field)) {
                summary.append(" - ");
            }
        }
        return summary.toString();
    }

    // Formats the description of an event based on settings
    private String formatDescription(EventInterface event) {
        StringBuilder description = new StringBuilder();
        for (String field : settings.getDescriptionFormat()) {
            appendField(description, field, event);
            if (!isLastField(settings.getDescriptionFormat(), field)) {
                description.append(" -");
            }
        }
        return description.toString();
    }

    // Formats the location of an event
    private String formatLocation(EventInterface event) {
        StringBuilder location = new StringBuilder();
        for (Location loc : ((SchoolEvent)event).getLocations()) {
            if (settings.getLocationFormat().contains("building")) {
                location.append("Byggnad: ").append(loc.getBuilding()).append(", ");
            }

            if (settings.getLocationFormat().contains("room")) {
                location.append("Rum: ").append(loc.getRoom());
            }

            if (((SchoolEvent)event).getLocations().indexOf(loc) != ((SchoolEvent)event).getLocations().size() - 1) {
                location.append(" \n ");
            }
        }
        return location.toString();
    }

    // Appends the specified field of an event to the builder
    private void appendField(StringBuilder builder, String field, EventInterface event) {
        switch (field) {
            case "code":
                builder.append(((SchoolEvent)event).getCourse().getCode());
                break;
            case "name":
                builder.append(((SchoolEvent)event).getCourse().getName());
                break;
            case "activity":
                builder.append(event.getActivity());
                break;
        }
    }

    // Checks if the field is the last in the format list
    private boolean isLastField(List<String> format, String field) {
        return format.get(format.size() - 1).equals(field);
    }
}
