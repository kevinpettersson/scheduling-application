package timebridge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import timebridge.model.Calendar;
import timebridge.model.CalendarBuilder;
import timebridge.model.Event;
import timebridge.model.CalendarBuilder;

@SpringBootTest
class ParserTests {

    @Test
    void testSingleEvent() throws IOException {
        // Sample iCalendar data
        String icsData =
            "BEGIN:VCALENDAR\n" +
            "VERSION:2.0\n" +
            "BEGIN:VEVENT\n" +
            "DTSTART:20231010T100000Z\n" +
            "DTEND:20231010T110000Z\n" +
            "SUMMARY:Sample Event\n" +
            "LOCATION:Room 101\n" +
            "END:VEVENT\n" +
            "END:VCALENDAR";

        // Parse the iCalendar data
        Calendar calendar = CalendarBuilder.build(icsData);

        // Verify the parsed calendar object
        assertNotNull(calendar);
        assertEquals(1, calendar.getEvents().size());

        // Verify the parsed event object
        Event event = calendar.getEvents().get(0);
        assertEquals("Sample Event", event.getActivity());
        assertEquals("Room 101", event.getLocations().get(0).getName());
        assertEquals(ZonedDateTime.parse("2023-10-10T10:00:00Z"), event.getInterval().getStart());
        assertEquals(ZonedDateTime.parse("2023-10-10T11:00:00Z"), event.getInterval().getEnd());
    }
}
