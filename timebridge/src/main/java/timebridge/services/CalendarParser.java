package timebridge.services;

import timebridge.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalendarParser {

    public CalendarParser() {
    }

    public Calendar parse(String iCal) throws IOException {
        // Normalize the iCal data, so that values are on the same line as the key
        String normalizedIcs = normalizeIcs(iCal);

        // Create a BufferedReader from the normalized iCal data
        BufferedReader reader = new BufferedReader(
                new StringReader(normalizedIcs));

        // Parse events from the BufferedReader
        ArrayList<Event> events = parseEvents(reader);

        // Create and return a new Calendar object with the parsed events
        return new Calendar("My Calendar", events);
    }

    // Method to normalize iCal data so that the SUMMARY, LOCATION,
    // and other values are on the same line as the key
    private String normalizeIcs(String ics) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(ics));
        StringWriter writer = new StringWriter();

        for (String line; (line = reader.readLine()) != null;) {
            writer.write(line.startsWith(" ") ? line.trim() : "\n" + line.trim());
        }

        return writer.toString().trim();
    }

    // Method to parse events from the input BufferedReader
    private ArrayList<Event> parseEvents(BufferedReader reader)
            throws IOException {
        ArrayList<Event> events = new ArrayList<>();
        String line;

        // Read each line from the input
        while ((line = reader.readLine()) != null) {
            // Check if the line indicates the start of an event, and parse it
            if (line.startsWith("BEGIN:VEVENT")) {
                events.addAll(parseEvent(reader));
            }
        }
        return events;
    }

    // Method to parse a single event from the input BufferedReader
    private ArrayList<Event> parseEvent(BufferedReader reader) throws IOException {
        ArrayList<Course> courses = new ArrayList<>();
        String activity = null;
        Interval interval = new Interval();
        ArrayList<Location> locations = new ArrayList<Location>();
        String line;

        // Read each line until the end of the event
        while (!(line = reader.readLine().trim()).equals("END:VEVENT")) {
            // Skip if line does not have prefix
            if (!line.contains(":")) {
                continue;
            }

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
                    courses = parseCourses(line);
                    activity = parseActivity(line);
                    break;
                // Parse and add the location(s) of the event
                case "LOCATION":
                    locations = parseLocations(line);
                    break;
                // Handle any other prefixes if necessary
                default:
                    break;
            }
        }

        ArrayList<Event> events = new ArrayList<>();

        // TEMP create a list of attendees, this needs to be implemented properly
        // by checking if the ical file contains attendees for each event.
        ArrayList<Attendee> attendees = new ArrayList<>();

        for (Course course : courses) {
            events.add(new Event(course, activity, interval, locations, attendees));
        }

        return events;
    }

    // Method to parse a datetime string and return codea ZonedDateTime object
    private ZonedDateTime parseDateTime(String line) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "yyyyMMdd'T'HHmmss'Z'");
        return ZonedDateTime.parse(line, formatter.withZone(ZoneOffset.UTC));
    }

    // Method to parse the course from the summary
    private ArrayList<Course> parseCourses(String line) {
        ArrayList<Course> courses = new ArrayList<>();
        String tempLine = line;

        // Remove activity from line
        tempLine = tempLine.replaceAll(",[^,]*$", "");

        // Remove all backslashes
        tempLine = tempLine.replaceAll("\\\\", "");

        // Find course code
        String pattern = "Kurskod:\\s(\\S+)\\.\\sKursnamn:\\s(.*?)(?=Kurskod:|$)";

        // Create a Pattern object
        Pattern regex = Pattern.compile(pattern);

        // Create a Matcher object
        Matcher matcher = regex.matcher(tempLine);

        // Create Course objects from the matches
        while (matcher.find()) {
            Course temp = new Course(matcher.group(1), matcher.group(2));
            courses.add(temp);
        }

        // Remove unwanted chars and substrings from course names
        for (Course course : courses) {
            // Remove trailing commas from course names
            String str = course.getName().trim();
            if (str.charAt(str.length() - 1) == ',') {
                course.setName(str.substring(0, str.length() - 1));
            }

            // Remove "Rubrik:" from course names
            str.replace("Rubrik:", "");
        }

        return courses;
    }

    // Method to parse the activity from the summary
    private String parseActivity(String line) {
        return line.replaceAll(".*,(\\s*[^,]+)$", "$1").trim();
    }

    // Method to parse the location(s) of an event
    private ArrayList<Location> parseLocations(String line) {
        ArrayList<Location> locations = new ArrayList<>();

        line = line.replace("\\n", "\n");
        line = line.trim();
        ArrayList<String> locationList = new ArrayList<String>(Arrays.asList(line.split("\n")));

        for (String location : locationList) {
            if (location.isEmpty()) {
                continue;
            }

            int firstDotIndex = location.indexOf(".");
            int secondDotIndex = location.indexOf(".", firstDotIndex + 1);
            if (secondDotIndex == -1) {
                secondDotIndex = location.length();
            }

            String room = location.substring(0, firstDotIndex);
            String building = location.substring(location.indexOf("Byggnad:"), secondDotIndex).replace("Byggnad:", "")
                    .trim();

            locations.add(new Location(building, room));
        }

        return locations;
    }
}
