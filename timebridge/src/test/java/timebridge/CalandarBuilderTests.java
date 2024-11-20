package timebridge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import timebridge.model.Calendar;
import timebridge.model.CalendarBuilder;
import timebridge.model.Event;


@SpringBootTest
public class CalandarBuilderTests {

    @Test
void testBuildWithDateTime() throws IOException, URISyntaxException {
    // iCal input with date and time
    String ics = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("testfiles/DateTimeTest.ics").toURI())));

        // Expected start and end times
        ZonedDateTime expectedStart = ZonedDateTime.parse("2023-11-20T15:00:00Z");
        ZonedDateTime expectedEnd = ZonedDateTime.parse("2023-11-20T16:00:00Z");

        // Build the calendar
        Calendar calendar = CalendarBuilder.build(ics);

        // Assertions
        assertNotNull(calendar);
        assertEquals(1, calendar.getEvents().size());

        Event event = calendar.getEvents().get(0);
        assertEquals(expectedStart, event.getInterval().getStart());
        assertEquals(expectedEnd, event.getInterval().getEnd());
}


@Test
void testBuildWithEmptyICS() throws IOException, URISyntaxException {
    // Empty ICS input
    String ics = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("testfiles/EmptyICS.ics").toURI())));

    // Build the calendar and check for an empty calendar
    Calendar calendar = CalendarBuilder.build(ics);

    // Assertions
    assertNotNull(calendar);
    assertEquals(0, calendar.getEvents().size()); // No events should be parsed
}




}
