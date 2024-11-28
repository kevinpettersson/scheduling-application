package timebridge;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import timebridge.model.Calendar;
import timebridge.model.Event;
import timebridge.services.CalendarParser;
import timebridge.services.CalendarSerializer;

@SpringBootTest
public class CalendarParserTests {

    public String getTextFile(String file) throws IOException, URISyntaxException{
        // Retrive the ics-file from the given path and turn it into a String.
        return new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("testfiles/"+ file +".ics").toURI())));
    }
    
    @Test
    public void testParseEmptyFile() throws IOException, URISyntaxException{
        String ics = getTextFile("empty_file");

        // Build the Calendar object from the String using CalendarBuilder.
        CalendarParser parser = new CalendarParser();
        Calendar calendar = parser.parse(ics);

        // Convert the Calendar object back to iCalendar-format.
        CalendarSerializer serializer = new CalendarSerializer();
        String iCalContent = serializer.serialize(calendar);

        // Verify that it's the right format.
        assertTrue(iCalContent.contains("BEGIN:VCALENDAR"));
        assertTrue(iCalContent.contains("END:VCALENDAR"));
        // verify that there is no events.
        assertEquals(0, StringUtils.countOccurrencesOf(iCalContent, "BEGIN:VEVENT"));
        assertEquals(0, StringUtils.countOccurrencesOf(iCalContent,"END:VEVENT"));
    }

    @Test
    public void testParseEmptyCalendar() throws IOException, URISyntaxException{
        var calendar = new Calendar();
        // Convert the Calendar object back to iCalendar-format.
        CalendarSerializer serializer = new CalendarSerializer();
        String iCalContent = serializer.serialize(calendar);

        // Verify that it's the right format.
        assertTrue(iCalContent.contains("BEGIN:VCALENDAR"));
        assertTrue(iCalContent.contains("END:VCALENDAR"));
        // verify that there is no events.
        assertEquals(0, StringUtils.countOccurrencesOf(iCalContent, "BEGIN:VEVENT"));
        assertEquals(0, StringUtils.countOccurrencesOf(iCalContent,"END:VEVENT"));
    }

    @Test 
    public void testParseCalendarWithNoEvents() throws IOException, URISyntaxException{
        String ics = getTextFile("no_events");

        CalendarParser parser = new CalendarParser();
        Calendar calendar = parser.parse(ics);

        CalendarSerializer serializer = new CalendarSerializer();
        String iCalContent = serializer.serialize(calendar);

        // Validate that the iCalendar-string contains the expected format.
        assertTrue(iCalContent.contains("BEGIN:VCALENDAR"));
        assertTrue(iCalContent.contains("END:VCALENDAR"));

        // Should not contain any events.
        assertFalse(iCalContent.contains("BEGIN:VEVENT"));
        assertFalse(iCalContent.contains("END:VEVENT"));
    }

    @Test
    public void testParseOneEvent() throws IOException, URISyntaxException{
        String ics = getTextFile("one_event");

        CalendarParser parser = new CalendarParser();
        Calendar calendar = parser.parse(ics);

        CalendarSerializer serializer = new CalendarSerializer();
        String iCalContent = serializer.serialize(calendar);

        assertNotNull(iCalContent);
        assertTrue(iCalContent.contains("BEGIN:VCALENDAR"));
        assertTrue(iCalContent.contains("END:VCALENDAR"));
            
        // Verify that the file only contains 2 events if there only are 2 occurences of Kurskod:
        assertEquals(2, StringUtils.countOccurrencesOf(iCalContent, "BEGIN:VEVENT"));
        assertEquals(2, StringUtils.countOccurrencesOf(iCalContent,"END:VEVENT"));
    }

    @Test
    public void testParseTwoEvents() throws IOException, URISyntaxException{
        
       String ics = getTextFile("two_events");

       CalendarParser parser = new CalendarParser();
       Calendar calendar = parser.parse(ics);

       CalendarSerializer serializer = new CalendarSerializer();
       String iCalContent = serializer.serialize(calendar);
        
       assertNotNull(iCalContent);
       assertTrue(iCalContent.contains("BEGIN:VCALENDAR"));
       assertTrue(iCalContent.contains("END:VCALENDAR"));

       // Verify the file only contains 4 events if there is four occurences of Kurskod.
       assertEquals(4, StringUtils.countOccurrencesOf(iCalContent, "BEGIN:VEVENT"));
       assertEquals(4, StringUtils.countOccurrencesOf(iCalContent,"END:VEVENT"));
    }

    @Test
    public void testTimeCorrectTimeFormat() throws IOException, URISyntaxException{
        
        String ics = getTextFile("two_courses");

        CalendarParser parser = new CalendarParser();
        Calendar calendar = parser.parse(ics);

        CalendarSerializer serializer = new CalendarSerializer();
        String iCalContent = serializer.serialize(calendar);

        assertNotNull(iCalContent);
        assertTrue(iCalContent.contains("BEGIN:VCALENDAR"));
        assertTrue(iCalContent.contains("END:VCALENDAR"));

        // defines the expected date-format for verification. 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'").withZone(ZoneOffset.UTC);

        for (Event event : calendar.getEvents()) {
            
            ZonedDateTime start = event.getInterval().getStart();
            ZonedDateTime end = event.getInterval().getEnd();

            // verify start comes before the end.
            assertTrue(start.isBefore(end));

            // Format the ZonedDateTime objects into strings
            String formattedStart = formatter.format(start);
            String formattedEnd = formatter.format(end);

            // verify the string matches the expected pattern: "yyyyMMdd'T'HHmmss'Z'"
            assertTrue(formattedStart.matches("\\d{8}T\\d{6}Z"));
        }
    }
}
