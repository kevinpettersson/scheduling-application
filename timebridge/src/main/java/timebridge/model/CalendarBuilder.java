package timebridge.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class CalendarBuilder {

    // Method to parse iCal-file to our model
    public static Calendar build(String ics) throws IOException {
        // Normalize the iCal data, so that values are on the same line as the key
        String normalizedIcs = normalizeIcs(ics);

        // Create a BufferedReader from the normalized iCal data
        BufferedReader reader = new BufferedReader(
            new StringReader(normalizedIcs)
        );

        // Parse events from the  BufferedReader
        ArrayList<Event> events = parseEvents(reader);

        // Create and return a new Calendar object with the parsed events
        return new Calendar("My Calendar", events);
    }

    // Method to normalize iCal data so that the SUMMARY, LOCATION,
    // and other values are on the same line as the key
    private static String normalizeIcs(String ics) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(ics));
        StringWriter writer = new StringWriter();
    
        for (String line; (line = reader.readLine()) != null; ) {
            writer.write(line.startsWith(" ") ? line.trim() : "\n" + line.trim());
        }

        return writer.toString().trim();
    }

    // Method to parse events from the input BufferedReader
    private static ArrayList<Event> parseEvents(BufferedReader reader)
        throws IOException {
        ArrayList<Event> events = new ArrayList<>();
        String line;

        // Read each line from the input
        while ((line = reader.readLine()) != null) {
            // Check if the line indicates the start of an event, and parse it
            if (line.startsWith("BEGIN:VEVENT")) {
                events.add(parseEvent(reader));
            }
        }
        return events;
    }

    // Method to parse a single event from the input BufferedReader
    private static Event parseEvent(BufferedReader reader) throws IOException {
        Course course = null;
        String activity = null;
        Interval interval = new Interval();
        ArrayList<Location> locations = new ArrayList<>();
        String line;

        // Read each line until the end of the event
        while (!(line = reader.readLine().trim()).equals("END:VEVENT")) {
            // Skip if line does not have prefix
            if (!line.contains(":")) { continue; }

            // Extract the prefix of the line and remove it from the line
            String prefix = line.substring(0, line.indexOf(':'));
            line = line.substring(prefix.length() + 1);

            // Parse and set the event details based on the prefix
            switch (prefix) {
                // Parse and set the start datetime of the event
                case "DTSTART":
                    interval.setStart(parseDateTime(line));
                    break;
                // Parse and set the end datetime of the event
                case "DTEND":
                    interval.setEnd(parseDateTime(line));
                    break;
                // Parse and set the summary (course & activity) of the event
                case "SUMMARY":
                    activity = parseSummary(line);
                    break;
                // Parse and add the location(s) of the event
                case "LOCATION":
                    locations = parseLocation(line);
                    break;
                // Handle any other prefixes if necessary
                default:
                    break;
            }
        }

        // Create and return a new Event object with the parsed details
        return new Event(course, activity, interval, locations);
    }

    // Method to parse a datetime string and return a ZonedDateTime object
    private static ZonedDateTime parseDateTime(String line) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            "yyyyMMdd'T'HHmmss'Z'"
        );
        return ZonedDateTime.parse(line, formatter.withZone(ZoneOffset.UTC));
    }

    // Method to parse the summary (course & activity) of an event
    private static String parseSummary(String line) {
        return line;
    }

    // Method to parse the location(s) of an event
    private static ArrayList<Location> parseLocation(String line) {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Room(line));
        return locations;
    }
}
