package timebridge.services;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import timebridge.model.Calendar;
import timebridge.model.event.Event;
import timebridge.model.event.TimeEditEvent;
import timebridge.model.event.PersonalEvent;
import timebridge.model.event.component.Attendee;
import timebridge.model.Format;
import timebridge.model.event.component.Location;

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
            // Only serialize visible events
            if (event.getVisibility()) {

                if (event instanceof TimeEditEvent) {
                    serializeEventTE((TimeEditEvent) event, calendar.getFormat());
                } else if (event instanceof PersonalEvent) {
                    serializeEventPE((PersonalEvent) event, calendar.getFormat());
                }
            }
        }

        sb.append(END_CALENDAR);

        return sb.toString();
    }

    private void serializeEventTE(TimeEditEvent event, Format format) {
        sb.append(BEGIN_EVENT);
        sb.append(DTSTART).append(serializeTimestamp(event.getInterval().getStart())).append("\n");
        sb.append(DTEND).append(serializeTimestamp(event.getInterval().getEnd())).append("\n");
        sb.append(UID).append(getUniqueIdetifier()).append("\n");
        sb.append(DTSTAMP).append(serializeTimestamp(ZonedDateTime.now(ZoneOffset.UTC))).append("\n");
        sb.append(LAST_MODIFIED).append(serializeTimestamp(ZonedDateTime.now(ZoneOffset.UTC))).append("\n");

        formatSummary(event, format);
        formatLocation(event, format);
        formatDescription(event, format);
        formatAttendee(event);

        sb.append(END_EVENT);
    }

    private void formatSummary(TimeEditEvent event, Format format) {
        sb.append(SUMMARY);
        for (String field : format.getSummary()) {
            appendField(event, field, format.getSummary());
        }
        sb.append("\n");
    }

    private void formatLocation(TimeEditEvent event, Format format) {
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

    private void formatDescription(TimeEditEvent event, Format format) {
        sb.append(DESCRIPTION);
        for (String field : format.getDescription()) {
            appendField(event, field, format.getDescription());
        }
        sb.append("\n");
    }

    private void formatAttendee(TimeEditEvent event) {
        for (Attendee attendee : event.getAttendees()) {
            sb.append("ATTENDEE;CN=").append(attendee.getName()).append(":mailto:").append(attendee.getMail())
                    .append("\n");
        }
    }

    private void appendField(TimeEditEvent event, String field, ArrayList<String> formatInstance) {
        if (field.equals("code")) {
            sb.append(event.getCourse().getCode());
        } else if (field.equals("activity")) {
            sb.append(event.getActivity());
        } else if (field.equals("name")) {
            sb.append(event.getCourse().getName());
        }

        // Add separator if not last field
        if (formatInstance.indexOf(field) != formatInstance.size() - 1) {
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

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // - - - - - - - - - - - - - - //

    private void serializeEventPE(PersonalEvent event, Format format) {
        sb.append(BEGIN_EVENT);
        sb.append(DTSTART).append(serializeTimestamp(event.getInterval().getStart())).append("\n");
        sb.append(DTEND).append(serializeTimestamp(event.getInterval().getEnd())).append("\n");
        sb.append(UID).append(getUniqueIdetifier()).append("\n");
        sb.append(DTSTAMP).append(serializeTimestamp(ZonedDateTime.now(ZoneOffset.UTC))).append("\n");
        sb.append(LAST_MODIFIED).append(serializeTimestamp(ZonedDateTime.now(ZoneOffset.UTC))).append("\n");

        // formatSummaryPE(event, format);
        sb.append(SUMMARY);
        sb.append(event.getSummary());
        sb.append("\n");

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
        formatDescriptionPE(event);
        formatAttendeePE(event);

        sb.append(END_EVENT);
    }

    private void formatDescriptionPE(PersonalEvent event) {
        sb.append(DESCRIPTION);
        sb.append(event.getDescription());
        sb.append("\n");
    }

    private void formatAttendeePE(PersonalEvent event) {
        for (Attendee attendee : event.getAttendees()) {
            sb.append("ATTENDEE;CN=").append(attendee.getName()).append(":mailto:").append(attendee.getMail())
                    .append("\n");
        }
    }

}
