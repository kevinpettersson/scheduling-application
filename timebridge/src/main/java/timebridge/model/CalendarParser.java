package timebridge.model;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public abstract class CalendarParser {

    public static String parse(Calendar calendar) throws IOException {
        // Start building the iCalendar string
        StringBuilder icalContent = new StringBuilder();
        icalContent.append("BEGIN:VCALENDAR\n");
        icalContent.append("VERSION:2.0\n");
        icalContent.append("PRODID:-//Your Organization//Your Product//EN\n");

        // Add each event to the calendar
        for (EventInterface event : calendar.getEvents()) {
            icalContent.append(writeEvent(event));
        }

        icalContent.append("END:VCALENDAR\n");

        return icalContent.toString();
    }

    private static String writeEvent(EventInterface event){
        StringBuilder sb = new StringBuilder();
        // Build each event
        sb.append("BEGIN:VEVENT\n");
        sb.append("DTSTART:").append(getCurrentTimestamp(event.getInterval().getStart())).append("\n");
        sb.append("DTEND:").append(getCurrentTimestamp(event.getInterval().getEnd())).append("\n");
        sb.append("UID:" + getUniqueIdetifier()).append("\n");
        sb.append("DTSTAMP:" + getCurrentTimestamp(ZonedDateTime.now(ZoneOffset.UTC))).append("\n");
        sb.append("LAST-MODIFIED:" + getCurrentTimestamp(ZonedDateTime.now(ZoneOffset.UTC))).append("\n");
        sb.append("SUMMARY:" + ((SchoolEvent) event).getCourse().getCode()).append(" - " + event.getActivity()).append("\n"); 
        sb.append("LOCATION:");  
        boolean flag = true;
        ArrayList buildings = new ArrayList<String>();
        for (Location location : ((SchoolEvent) event).getLocations()) {
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
        sb.append("DESCRIPTION:" + (((SchoolEvent) event).getCourse().getName()) + "\n");
        sb.append("END:VEVENT\n");
        
        return sb.toString();
    }
    

    private static String getUniqueIdetifier() {
        return UUID.randomUUID().toString() + "@timebridge.se";
    }
    
    private static String getCurrentTimestamp(ZonedDateTime dateTime) {
        // Get the current UTC time and format it to iCal format

        ZonedDateTime utcDateTime = dateTime.withZoneSameInstant(ZoneOffset.UTC);

        // Format to iCalendar format: YYYYMMDDTHHMMSSZ
        DateTimeFormatter icalFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

        return utcDateTime.format(icalFormatter);
    }
    
}

