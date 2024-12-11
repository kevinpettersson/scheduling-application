package timebridge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import timebridge.model.Calendar;
import timebridge.model.event.TimeEditEvent;
import timebridge.services.CalendarParser;

@SpringBootTest
public class CalendarEditorTest {

    private ArrayList<String> courseFilter = new ArrayList<>();
    private ArrayList<String> activityFilter = new ArrayList<>();
    private ArrayList<String> summaryFormat = new ArrayList<>();
    private ArrayList<String> descriptionFormat = new ArrayList<>();
    private ArrayList<String> locationFormat = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        // Instance variable setup before each test.
        ArrayList<String> courseFilter = new ArrayList<>();
        ArrayList<String> activityFilter = new ArrayList<>();
        ArrayList<String> summaryFormat = new ArrayList<>();
        ArrayList<String> descriptionFormat = new ArrayList<>();
        ArrayList<String> locationFormat = new ArrayList<>();
    }


    public String getTextFile(String file) throws IOException, URISyntaxException{
        // Retrive the ics-file from the given path and turn it into a String.
        return new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("testfiles/"+ file +".ics").toURI())));
    }

    @Test
    public void testFilteringTwoEvents() throws IOException, URISyntaxException{

        String ics = getTextFile("two_events");

        CalendarParser parser = new CalendarParser();
        Calendar calendar = parser.parse(ics);

        courseFilter.add("DIT213GU");
        activityFilter.add("Föreläsning");
        summaryFormat.add("code");
        summaryFormat.add("activity");
        descriptionFormat.add("name");
        locationFormat.add("room");

        // Filter the calendar
        calendar.filterEvents(courseFilter, activityFilter);

        for (TimeEditEvent event : calendar.getEvents()) {
            if (event.getVisibility().equals(false)) {
                continue;
            }
            assertEquals("DIT213GU", event.getCourse().getCode());
            assertEquals("Objektorienterat programmeringsprojekt", event.getCourse().getName());
            assertEquals("Föreläsning", event.getActivity());
        }
    }
    
}
