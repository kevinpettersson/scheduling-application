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
        Calendar resultCalendar = new Calendar(calendar.getName(), new ArrayList<Event>());
        resultCalendar.addEvents(filterEvents());
        formatEvents(resultCalendar);
        return resultCalendar;
    }

    // Filters events based on course and activity settings
    private ArrayList<Event> filterEvents() {
        ArrayList<Event> filteredEvents = new ArrayList<>();
        for (Event event : calendar.getEvents()) {
            if (settings.getCourseFilter().contains(event.getCourse().getCode())
                    && settings.getActivityFilter().contains(event.getActivity())) {
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }

    // Formats events in the result calendar
    private void formatEvents(Calendar resultCalendar) {
        for (Event event : resultCalendar.getEvents()) {
            String summary = formatSummary(event);
            String description = formatDescription(event);
            String location = formatLocation(event);

            event.getFormat().setSummary(summary);
            event.getFormat().setDescription(description);
            event.getFormat().setLocation(location);
        }
    }

    // Formats the summary of an event based on settings
    private String formatSummary(Event event) {
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
    private String formatDescription(Event event) {
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
    private String formatLocation(Event event) {
        StringBuilder location = new StringBuilder();
        for (Location loc : event.getLocations()) {
            location.append("Byggnad: ").append(loc.getBuilding()).append(", ");
            location.append("Rum: ").append(loc.getRoom());

            if (event.getLocations().indexOf(loc) != event.getLocations().size() - 1) {
                location.append(" \n ");
            }
        }
        return location.toString();
    }

    // Appends the specified field of an event to the builder
    private void appendField(StringBuilder builder, String field, Event event) {
        switch (field) {
            case "code":
                builder.append(event.getCourse().getCode());
                break;
            case "name":
                builder.append(event.getCourse().getName());
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
