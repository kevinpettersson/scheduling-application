package timebridge.services;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import timebridge.model.Calendar;
import timebridge.model.event.Event;
import timebridge.model.event.component.Attendee;

/**
 * Serializes a {@link Calendar} object to the iCalendar format.
 * Uses a {@link StringBuilder} to append necessary fields and serialized events.
 */
@Service
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
    private static final String ATTENDEE = "ATTENDEE;CN=";
    private static final String DATE_PATTERN = "yyyyMMdd'T'HHmmss'Z'";


    private final StringBuilder sb;

    public CalendarSerializer() {
        sb = new StringBuilder();
    }

    /**
     * <p> Serializes a {@link Calendar} object to a iCalendar format. </p>
     *
     * @param calendar The calendar to serialize.
     * @return String - The serialized calendar in iCalendar format.
     * @throws IOException if an I/O error occurs.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    public String serialize(Calendar calendar) throws IOException {
        sb.append(BEGIN_CALENDAR);
        sb.append(VERSION);
        sb.append(PRODID);

        for (Event event : calendar.getEvents()) {
            // Only serialize visible events
            if (event.getVisibility()) {
                serializeEvent(event);
            }
        }

        sb.append(END_CALENDAR);

        return sb.toString();
    }

    /**
     * <p> Serializes an {@link Event} object to a iCalendar format. </p>
     *
     * @param event The event to serialize.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    private void serializeEvent(Event event) {
        sb.append(BEGIN_EVENT);
        sb.append(DTSTART).append(serializeTimestamp(event.getInterval().getStart())).append("\n");
        sb.append(DTEND).append(serializeTimestamp(event.getInterval().getEnd())).append("\n");
        sb.append(UID).append(event.getId()).append("\n");
        sb.append(DTSTAMP).append(serializeTimestamp(ZonedDateTime.now(ZoneOffset.UTC))).append("\n");
        sb.append(LAST_MODIFIED).append(serializeTimestamp(ZonedDateTime.now(ZoneOffset.UTC))).append("\n");
        sb.append(SUMMARY).append(event.getSummary()).append("\n");
        sb.append(LOCATION).append(event.getLocation()).append("\n");
        sb.append(DESCRIPTION).append(event.getDescription()).append("\n");
        formatAttendee(event);
        sb.append(END_EVENT);
    }

    /**
     * <p> Formats the attendees of an {@link Event} object to iCalendar format. </p>
     *
     * @param event The event to format the attendees of.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    private void formatAttendee(Event event) {
        for (Attendee attendee : event.getAttendees()) {
            sb.append(ATTENDEE).append(attendee.getName()).append(":mailto:").append(attendee.getMail())
                    .append("\n");
        }
    }

    /**
     * <p> Serializes a {@link ZonedDateTime} object to a iCalendar format. </p>
     *
     * @param dateTime The ZonedDateTime object to serialize.
     * @return String - The serialized timestamp in correct format.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    private String serializeTimestamp(ZonedDateTime dateTime) {
        ZonedDateTime utcDateTime = dateTime.withZoneSameInstant(ZoneOffset.UTC);
        DateTimeFormatter iCalFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return utcDateTime.format(iCalFormatter);
    }
}
