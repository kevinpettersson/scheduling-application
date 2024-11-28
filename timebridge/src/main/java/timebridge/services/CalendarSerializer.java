package timebridge.services;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import timebridge.model.*;

public class CalendarSerializer {
    
    private static final String ORG = "Chalmers";
    private static final String PRODUCT = "TimeBridge";
    private static final String BEGIN_CALENDAR = "BEGIN:VCALENDAR\n";
    private static final String END_CALENDAR = "END:VCALENDAR\n";
    private static final String VERSION = "VERSION:2.0\n";
    private static final String PRODID = "PRODID:-//" + ORG + "//" + PRODUCT + "//EN\n";
    private static final String BEGIN_EVENT = "BEGIN:VEVENT\n";
    private static final String END_EVENT = "END:VEVENT\n";
    private static final String DTSTART = "DTSTART:";
    private static final String DTEND = "DTEND:";
    private static final String UID = "UID:";
    private static final String DTSTAMP = "DTSTAMP:";
    private static final String LAST_MODIFIED = "LAST-MODIFIED:";
    private static final String SUMMARY = "SUMMARY:";
    private static final String LOCATION = "LOCATION:";
    private static final String DESCRIPTION = "DESCRIPTION:";

    private final StringBuilder sb;

    public CalendarSerializer() {
        sb = new StringBuilder();
    }

    public String serialize(Calendar calendar) throws IOException {
        // Add starting fields
        sb.append(BEGIN_CALENDAR);
        sb.append(VERSION);
        sb.append(PRODID);

        // Add each event to the calendar
        for (Event event : calendar.getEvents()) {
            serializeEvent(event, calendar.getFormat());
        }

        sb.append(END_CALENDAR);
        
        return sb.toString();
    }

    private void serializeEvent(Event event, Format format) {
        sb.append(BEGIN_EVENT);
        sb.append(DTSTART).append(serializeTimestamp(event.getInterval().getStart())).append("\n");
        sb.append(DTEND).append(serializeTimestamp(event.getInterval().getEnd())).append("\n");
        sb.append(UID).append(getUniqueIdetifier()).append("\n");
        sb.append(DTSTAMP).append(serializeTimestamp(ZonedDateTime.now(ZoneOffset.UTC))).append("\n");
        sb.append(LAST_MODIFIED).append(serializeTimestamp(ZonedDateTime.now(ZoneOffset.UTC))).append("\n");

        formatSummary(event, format);
        formatLocation(event, format);
        formatDescription(event, format);

        sb.append(END_EVENT);
    }

    private void formatSummary(Event event, Format format) {
        sb.append(SUMMARY);
        for (String field : format.getSummary()) { // [code, activity]
            appendField(event, field, format);
        }
        sb.append("\n");
    }

    private void formatLocation(Event event, Format format) {
        sb.append(LOCATION);
        for (Location loc : event.getLocations()) {
            if (format.getLocation().contains("building")) {
                sb.append("Byggnad: ").append(loc.getBuilding()).append(", ");
            }
            if (format.getLocation().contains("room")) {
                sb.append("Rum: ").append(loc.getRoom());
            }
        }
        sb.append("\n");
    }

    private void formatDescription(Event event, Format format) {
        sb.append(DESCRIPTION);
        for (String field : format.getDescription()) { // [code, activity]
            appendField(event, field, format);
        }
        sb.append("\n");
    }

    private void appendField(Event event, String field, Format format) {
        if (field.equals("code")) {
            sb.append(event.getCourse().getCode());
        }
        else if (field.equals("activity")) {
            sb.append(event.getActivity());
        }
        else if (field.equals("name")) {
            sb.append(event.getCourse().getName());
        }

        // Add separator if not last field
        if (format.getSummary().indexOf(field) != format.getSummary().size() - 1) {
            sb.append(" - ");
        }
    }


    private String getUniqueIdetifier() {
        return UUID.randomUUID().toString() + "@timebridge.se";
    }
    
    private String serializeTimestamp(ZonedDateTime dateTime) {
        ZonedDateTime utcDateTime = dateTime.withZoneSameInstant(ZoneOffset.UTC);
        DateTimeFormatter icalFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
        return utcDateTime.format(icalFormatter);
    }
    
}
