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

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import timebridge.model.Calendar;
import timebridge.model.CalendarBuilder;
import timebridge.model.CalendarEditor;
import timebridge.model.Event;
import timebridge.model.Settings;

@SpringBootTest
public class CalendarEditorTest {

    private ArrayList<String> courseFilter = new ArrayList<>();
    private ArrayList<String> activityFilter = new ArrayList<>();
    private ArrayList<String> summaryFormat = new ArrayList<>();
    private ArrayList<String> descriptionFormat = new ArrayList<>();
    private ArrayList<String> locationFormat = new ArrayList<>();

    public String getTextFile(String file) throws IOException, URISyntaxException{
        // Retrive the ics-file from the given path and turn it into a String.
        return new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("testfiles/"+ file +".ics").toURI())));
    }

    @Test
    public void testFilteringTwoEvents() throws IOException, URISyntaxException{
        
       String ics = getTextFile("two_events");

       Calendar calendar = CalendarBuilder.build(ics);

       courseFilter.add("DIT213GU");
       activityFilter.add("Föreläsning");
       summaryFormat.add("code");
       summaryFormat.add("activity");
       descriptionFormat.add("name");
       locationFormat.add("room");

       Settings settings = new Settings(courseFilter,activityFilter,summaryFormat,descriptionFormat,locationFormat);
       
        CalendarEditor editor = new CalendarEditor(calendar, settings);   
        Calendar filteredCalendar = editor.build();

        for (Event event : filteredCalendar.getEvents()) {
            assertEquals("DIT213GU - Föreläsning", event.getFormat().getSummary());
            assertEquals("Objektorienterat programmeringsprojekt", event.getFormat().getDescription());
            assertFalse(event.getFormat().getLocation().contains("Byggnad:"));
        }

       
    }
    
}
