package timebridge.services;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import timebridge.model.*;

public class CalendarSerializer {
    
    private static final String ORG = "Group 12";
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

    private StringBuilder sb;

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
            serializeEvent(event, calendar);
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

        sb.append(SUMMARY);
        for (String field : format.getSummary()) {
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

        sb.append(LOCATION);
        
        
        
    }

    public static String parse(Calendar calendar) throws IOException {
        // Start building the iCalendar string
        StringBuilder icalContent = new StringBuilder();


        // Add each event to the calendar
        for (Event event : calendar.getEvents()) {
            icalContent.append(writeEvent(event));
        }

        icalContent.append(END_CALENDAR);

        return icalContent.toString();
    }

    private String writeEvent(Event event){
        StringBuilder sb = new StringBuilder();
        // Build each event
        sb.append(BEGIN_EVENT);
        sb.append(DTSTART).append(event.getInterval().getStart()).append("\n");
        sb.append(DTEND).append(event.getInterval().getEnd()).append("\n");
        sb.append(UID + getUniqueIdetifier()).append("\n");
        sb.append("DTSTAMP:" + serializeTimestamp(ZonedDateTime.now(ZoneOffset.UTC))).append("\n");
        sb.append("LAST-MODIFIED:" + serializeTimestamp(ZonedDateTime.now(ZoneOffset.UTC))).append("\n");
        sb.append("SUMMARY:" + event.getCourse().getCode()).append(" - " + event.getActivity()).append("\n"); 
        sb.append("LOCATION:");  
        boolean flag = true;
        ArrayList buildings = new ArrayList<String>();
        
        for (Location location : event.getLocations()) {
            if(!buildings.contains(location.getBuilding())){
                sb.append("Byggnad: " + location.getBuilding()).append(", ");
                sb.append("Rum: " + location.getRoom()).append(" \\ ");
                buildings.add(location.getBuilding());
            }
            else{
                sb.append("Rum: " + location.getRoom()).append(" \\ ");
            }
        }
        sb.append("\n");
        sb.append("DESCRIPTION:" + (event.getCourse().getName()) + "\n");
        sb.append("END:VEVENT\n");
        
        return sb.toString();
    }
    

    private String getUniqueIdetifier() {
        return UUID.randomUUID().toString() + "@timebridge.se";
    }
    
    private String serializeTimestamp(ZonedDateTime dateTime) {
        // Get the current UTC time and format it to iCal format

        ZonedDateTime utcDateTime = dateTime.withZoneSameInstant(ZoneOffset.UTC);

        // Format to iCalendar format: YYYYMMDDTHHMMSSZ
        DateTimeFormatter icalFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

        return utcDateTime.format(icalFormatter);
    }
    
}
