package timebridge.services;

import org.springframework.stereotype.Service;
import timebridge.model.*;
import timebridge.model.event.component.Course;
import timebridge.model.event.component.Interval;
import timebridge.model.event.component.Locale;
import timebridge.model.event.*;

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

/**
 * <p> Parses iCal data and creates a {@link Calendar} object. </p>
 *
 * @since 2024-12-19
 * @author Group 12
 */
@Service
public class CalendarParser {

    public CalendarParser() {
    }


    /**
     * <p> Parses iCal data and creates a {@link Calendar} object. </p>
     *
     * @param iCal iCal data to parse
     * @return a {@link Calendar} object with the parsed events
     * @throws IOException if an I/O error occurs
     *
     * @since 2024-12-19
     * @author Group 12
     */
    public Calendar parse(String iCal) throws IOException {
        // Check if the input is an actual iCal file
        if (!iCal.startsWith("BEGIN:VCALENDAR")) {
            throw new IllegalArgumentException("Input is not a valid iCal file");
        }

        String normalizedIcs = normalizeIcs(iCal);

        BufferedReader reader = new BufferedReader(
                new StringReader(normalizedIcs));

        ArrayList<Event> events = parseEvents(reader);

        return new Calendar("My Calendar", events);
    }

    /**
     * <p> Normalizes iCal data so that the keys(SUMMARY, LOCATION, etc.) are on the same line as the values. </p>
     *
     * @param ics iCal data to normalize.
     * @return a string, normalized iCal data.
     * @throws IOException if an I/O error occurs.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    private String normalizeIcs(String ics) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(ics));
        StringWriter writer = new StringWriter();

        for (String line; (line = reader.readLine()) != null;) {
            writer.write(line.startsWith(" ") ? line.trim() : "\n" + line.trim());
        }
        return writer.toString().trim();
    }

    /**
     * <p>Reads the input line by line, looking for the start of an event (indicated by
     * the line "BEGIN:VEVENT"). Once an event is detected, it delegates the actual parsing to the
     * {@link #parseEvent(BufferedReader)} method.</p>
     *
     * @param reader BufferedReader to parse events from
     * @return ArrayList<Event> contains all the parsed events from input data.
     * @throws IOException if an I/O error occurs.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    private ArrayList<Event> parseEvents(BufferedReader reader)
            throws IOException {
        ArrayList<Event> events = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("BEGIN:VEVENT")) {
                events.addAll(parseEvent(reader));
            }
        }
        return events;
    }

    /**
     * <p> Parses a single event from the input BufferedReader.
     * Based on the key of the line, it delegates the parsing of the value to the appropriate method.</p>
     * See: {@link #parseDateTime(String)}, {@link #parseCourses(String)}, {@link #parseActivity(String)}.
     *
     * @param reader BufferedReader to parse events from.
     * @return ArrayList<Event> contains the parsed event from input data.
     * @throws IOException if an I/O error occurs.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    private ArrayList<Event> parseEvent(BufferedReader reader) throws IOException {
        ArrayList<Course> courses = new ArrayList<>();
        String activity = new String();
        Interval interval = new Interval();
        ArrayList<Locale> locations = new ArrayList<Locale>();
        String line;

        while (!(line = reader.readLine().trim()).equals("END:VEVENT")) {
            // Skip if line does not have prefix
            if (!line.contains(":")) {
                continue;
            }

            // Extract the prefix of the line and remove it from the line
            String prefix = line.substring(0, line.indexOf(':'));
            line = line.substring(prefix.length() + 1);

            switch (prefix) {
                case "DTSTART":
                    interval.setStart(parseDateTime(line));
                    break;
                case "DTEND":
                    interval.setEnd(parseDateTime(line));
                    break;
                case "SUMMARY":
                    courses = parseCourses(line);
                    activity = parseActivity(line);
                    break;
                case "LOCATION":
                    locations = parseLocales(line);
                    break;
                default:
                    break;
            }
        }

        ArrayList<Event> events = new ArrayList<>();

        for (Course course : courses) {
            events.add(EventFactory.createTimeEditEvent(interval, course, activity, locations));
        }

        return events;
    }

    /**
     * <p> Parses a datetime string and returns a {@link ZonedDateTime} object representing the parsed datetime. </p>
     *
     * @param line String to parse as a datetime.
     * @return {@link ZonedDateTime} object representing the parsed datetime.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    private ZonedDateTime parseDateTime(String line) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "yyyyMMdd'T'HHmmss'Z'");
        return ZonedDateTime.parse(line, formatter.withZone(ZoneOffset.UTC));
    }

    /**
     * <p> Parses the course information from the provided line into an {@link Course} object. </p>
     * <p> Extracts course codes and names removes unwanted characters,
     * and returns a list of {@link Course} objects. </p>
     *
     * @param line String to parse as courses.
     * @return {@link ArrayList<Course>} containing the parsed courses.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    private ArrayList<Course> parseCourses(String line) {
        ArrayList<Course> courses = new ArrayList<>();

        // Remove backslashes
        line = line.replaceAll("\\\\", "");

        // Regex to match each "Kurs kod: ... . Kurs namn: ..."
        String pattern = "Kurs kod:\\s*([^\\.]+)\\.\\s*Kurs namn:\\s*([^,]+)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(line);

        while (matcher.find()) {
            String code = matcher.group(1).trim();
            String name = matcher.group(2).trim();
            courses.add(new Course(code, name));
        }

        return courses;
    }

    /**
     * <p> Parses the activity from the summary, where the activity comes after the last comma. </p>
     *
     * @param line String to parse as activity.
     * @return String containing the parsed activity.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    private String parseActivity(String line) {
        return line.replaceAll(".*Activity:\\s*([^,\\\\]+).*", "$1").trim();
    }

    /**
     * <p> Parses the location(s) from the provided line into an {@link Locale} object. </p>
     *
     * @param line String to parse as locations.
     * @return {@link ArrayList<Locale>} containing the parsed locations.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    // Method to parse the location(s) of an event
    private ArrayList<Locale> parseLocales(String line) {
        ArrayList<Locale> locales = new ArrayList<>();

        line = line.replace("\\n", "\n");
        line = line.trim();
        ArrayList<String> locationList = new ArrayList<String>(Arrays.asList(line.split("\n")));

        for (String location : locationList) {
            if (location.isEmpty()) {
                continue;
            }

            int lokalIndex = location.indexOf("Lokalnamn:");
            if (lokalIndex == -1){
                continue; // skip if not found
            }

            int endIndex = location.indexOf(".", lokalIndex);
            if (endIndex == -1) endIndex = location.length();
            String room = location.substring(lokalIndex + "Lokalnamn:".length(), endIndex).trim();
            locales.add(new Locale("", room));
        }

        return locales;
    }
}
