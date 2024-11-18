package timebridge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import timebridge.model.Calendar;
import timebridge.model.CalendarBuilder;
import timebridge.model.CalendarParser;

@SpringBootTest
public class CalendarParserTests {
    
    @Test 
    public void emptyCalendarTest() throws IOException, URISyntaxException{
        // Retrive the ics-file from the given path and turn it into a String.
        String ics = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("testfiles/no_events.ics").toURI())));

        // Build the Calendar object from the String using CalendarBuilder. 
        Calendar calendar = CalendarBuilder.build(ics);

        // Convert the Calendar object back to iCalendar-format.
        String iCalContent = CalendarParser.parse(calendar);

        // Validate that the iCalendar-string contains the expected format.
        assertTrue(iCalContent.contains("BEGIN:VCALENDAR"));
        assertTrue(iCalContent.contains("END:VCALENDAR"));

        // Should not contain any events.
        assertFalse(iCalContent.contains("BEGIN:VEVENT"));
        assertFalse(iCalContent.contains("END:VEVENT"));
    }



    @Test
    public void testParseTwoCourses() throws IOException, URISyntaxException{
        
       String ics = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("testfiles/two_courses.ics").toURI())));

       Calendar calendar = CalendarBuilder.build(ics);
       
       String iCalContent = CalendarParser.parse(calendar);
        
       assertNotNull(iCalContent);
       assertTrue(iCalContent.contains("BEGIN:VCALENDAR"));
    }
}
