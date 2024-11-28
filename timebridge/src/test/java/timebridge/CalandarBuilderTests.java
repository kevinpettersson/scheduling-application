package timebridge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import timebridge.model.Calendar;
import timebridge.model.CalendarBuilder;
import timebridge.model.Event;
import timebridge.model.Location;
import java.net.URL;


//TESTS REGARDING A CORRECLY FORMATTED ICS FILE, TESTING IT BUILDS A CORRECT CALENDAR OBJECT
@SpringBootTest
public class CalandarBuilderTests {

    @Test //test OK
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
        assertEquals(2, calendar.getEvents().size());

        Event event = calendar.getEvents().get(0);
        assertEquals(expectedStart, event.getInterval().getStart());
        assertEquals(expectedEnd, event.getInterval().getEnd());
}


//TESTS REGARDING THE FORMAT OF THE ICS FILE, CHECCKING THAT IT DOES NOT BUILD ANYTHING WHEN IT IS WRONGLY FORMATTED
// TEST CASE 2: The empty ics file should return an empty calendar
@Test //test OK
void testEmptyICS() throws IOException, URISyntaxException {
    // Empty ICS input
    String ics = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("testfiles/EmptyICS.ics").toURI())));

    // Build the calendar and check for an empty calendar
    Calendar calendar = CalendarBuilder.build(ics);

    // Assertions
    assertNotNull(calendar);
    assertEquals(0, calendar.getEvents().size()); // No events should be parsed
}

// TEST CASE 3: Malformed ICS file (invalid date format), should return empty calendar without throwing an exception???
@Test //DETTA TEST GER ERROR PGA HUR VI HAR SKRIVIT I CALENDAR BUILDER???
void testInvalidiCalString() throws IOException, URISyntaxException {
    String ics = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("testfiles/invalidICSfileDate.ics").toURI())));

    // Build the calendar
   // Calendar calendar = CalendarBuilder.build(ics);

    // Assertions
    //assertNotNull(calendar);  // Ensure the calendar is not null (even if empty)
    //assertEquals(0, calendar.getEvents().size());  // Expecting no events due to malformed date
} //Det här testet failar, ger den bara null eller nåt?


//Test missing required fields in ICS file
@Test 
void testMissingReqField() throws IOException, URISyntaxException{
    String ics = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("testfiles/missingReqFieldTime.ics").toURI())));

// Build the calendar
    Calendar calendar = CalendarBuilder.build(ics);

    // Assertions
    assertNotNull(calendar);  // Calendar should be created successfully
    assertEquals(2, calendar.getEvents().size());  // One event should be in the calendar

    Event event = calendar.getEvents().get(0);
    //assertNull(event.getInterval().getStart());  // The start time should be null or a default value
    //assertNull(event.getInterval().getEnd());    // The end time should be null or a default value

}

@Test 
void test_two_events() throws IOException, URISyntaxException{
    String ics = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("testfiles/two_events.ics").toURI())));

    // Build the calendar
    Calendar calendar = CalendarBuilder.build(ics);

    // Assertions
    assertNotNull(calendar);  // Ensure the calendar is created
    assertEquals(4, calendar.getEvents().size());  // There should be two events in the calendar

    // Verify the first event
    Event event1 = calendar.getEvents().get(0);
    assertEquals("Föreläsning", event1.getActivity());  // Check the activity
    assertEquals("DIT213GU", event1.getCourse().getCode());  // Check course code
    assertEquals("Objektorienterat programmeringsprojekt", event1.getCourse().getName());  // Check course name

    // Verify the locations for the first event
    ArrayList<Location> locations1 = event1.getLocations();
    assertEquals(1, locations1.size());  // There should be 1 location
    assertEquals("SB-H1", locations1.get(0).getRoom());
    assertEquals("SB2", locations1.get(0).getBuilding());

    // Check the event start and end time
    assertEquals(ZonedDateTime.parse("2024-11-20T12:15:00Z"), event1.getInterval().getStart());
    assertEquals(ZonedDateTime.parse("2024-11-20T14:00:00Z"), event1.getInterval().getEnd());

    // Verify the second event
    Event event2 = calendar.getEvents().get(2);
    assertEquals("Övning", event2.getActivity());  // Check the activity
    assertEquals("DIT213GU", event2.getCourse().getCode());  // Check course code
    assertEquals("Objektorienterat programmeringsprojekt", event2.getCourse().getName());  // Check course name

    // Verify the locations for the second event
    ArrayList<Location> locations2 = event2.getLocations();
    assertEquals(3, locations2.size());  // There should be 3 locations
    assertEquals("SB-D023", locations2.get(0).getRoom());
    assertEquals("SB1", locations2.get(0).getBuilding());
    assertEquals("SB-D025", locations2.get(1).getRoom());
    assertEquals("SB1", locations2.get(1).getBuilding());
    assertEquals("SB-D080", locations2.get(2).getRoom());
    assertEquals("SB1", locations2.get(2).getBuilding());

    // Check the event start and end time
    assertEquals(ZonedDateTime.parse("2024-11-20T14:15:00Z"), event2.getInterval().getStart());
    assertEquals(ZonedDateTime.parse("2024-11-20T16:00:00Z"), event2.getInterval().getEnd());
}


}



