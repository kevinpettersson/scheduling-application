package timebridge;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import timebridge.services.CalendarParser;
import timebridge.services.CalendarSerializer;
import timebridge.model.Calendar;
import timebridge.model.Event;
import timebridge.repository.CalendarRepository;

@RestController
@CrossOrigin(origins = "http://localhost:5173") // Your frontend's origin
public class Controller {

    @Autowired
    private CalendarRepository repository;

    @GetMapping("/upload")
    public ResponseEntity<Calendar> uploadCalendar(@RequestParam String ical)
            throws MalformedURLException, IOException {
        try {
            // Create a URL object and open a connection to the iCalendar URL
            URL url = new URL(ical);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Check if the request was successful
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Read the iCalendar file data from the URL
            InputStream inputStream = connection.getInputStream();
            String icsData = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            CalendarParser parser = new CalendarParser();
            Calendar calendar = parser.parse(icsData);

            // Save the calendar to the database
            repository.save(calendar);

            return ResponseEntity.ok(calendar);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadCalendar(@RequestBody Calendar calendar) throws IOException {
        try {
            // Serialize the calendar to iCalendar format
            CalendarSerializer serializer = new CalendarSerializer();
            String icsData = serializer.serialize(calendar);

            // Convert content to bytes
            byte[] contentBytes = icsData.getBytes(StandardCharsets.UTF_8);

            // Set HTTP headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + calendar.getName() + ".ics");
            headers.add(HttpHeaders.CONTENT_TYPE, "text/calendar; charset=UTF-8");

            return new ResponseEntity<>(contentBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/modify")
    public ResponseEntity<Calendar> modifyCalendar(
            @RequestParam ArrayList<String> courseFilter,
            @RequestParam ArrayList<String> activityFilter,
            @RequestBody Calendar calendar) throws Exception {
        try {
            // Filter events based on course and activity filters
            calendar.filterEvents(courseFilter, activityFilter);

            // Save the modified calendar to the database
            // If the calendar does exist in the database, it will be updated
            repository.save(calendar);

            return ResponseEntity.ok(calendar);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/addEvent")
    public ResponseEntity<Calendar> addEvent(
        @RequestParam String calendarId,
        @RequestBody Event event) throws Exception {
            try {
                // Retrieve the calendar from the database
                Calendar calendar = repository.findById(calendarId).orElse(null);

                // Check if the calendar exists
                if (calendar == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
                calendar.addEvent(event);
                repository.save(calendar);
                return ResponseEntity.ok(calendar);
            }
            catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
    }

    @PostMapping("/deleteEvent")
    public ResponseEntity<Calendar> deleteEvent(
        @RequestParam String calendarId,
        @RequestParam String eventId) throws Exception {
        try {
            // Retrieve the calendar from the database
            Calendar calendar = repository.findById(calendarId).orElse(null);

            // Check if the calendar exists
            if (calendar == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            calendar.deleteEvent(eventId);
            repository.save(calendar);
            return ResponseEntity.ok(calendar);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<byte[]> getPublicCalendar(@PathVariable String id) {
        try {
            // Retrieve the calendar from the database
            Calendar calendar = repository.findById(id).orElse(null);

            // Check if the calendar exists
            if (calendar == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Serialize the calendar to iCalendar format
            CalendarSerializer serializer = new CalendarSerializer();
            String icsData = serializer.serialize(calendar);

            // Convert content to bytes
            byte[] contentBytes = icsData.getBytes(StandardCharsets.UTF_8);

            // Set HTTP headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + calendar.getName() + ".ics");
            headers.add(HttpHeaders.CONTENT_TYPE, "text/calendar; charset=UTF-8");

            return new ResponseEntity<>(contentBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}