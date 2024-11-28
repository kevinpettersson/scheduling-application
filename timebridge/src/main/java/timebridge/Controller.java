package timebridge;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import timebridge.services.*;
import timebridge.model.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@RestController
class Controller {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }

    @CrossOrigin(origins = "http://localhost:5173") // Your frontend's origin
    @GetMapping("/upload")
    public ResponseEntity<Calendar> uploadCalendar(@RequestParam String ical) {
        try {
            // Create a URL object and open a connection to the iCalendar URL
            URL url = new URL(ical);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Check if the request was successful
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return ResponseEntity.badRequest().build();
            }

            // Read the iCalendar file data from the URL
            try (InputStream inputStream = connection.getInputStream()) {
                String icsData = new String(inputStream.readAllBytes());
                CalendarParser parser = new CalendarParser();
                Calendar calendar = parser.parse(icsData);
                return ResponseEntity.ok(calendar);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @CrossOrigin(origins = "http://localhost:5173") // Your frontend's origin
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadCalendar(@RequestParam String ical) {
        try {
            // Create a URL object and open a connection to the iCalendar URL
            URL url = new URL(ical);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Check if the request was successful
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return ResponseEntity.badRequest().build();
            }

            // Read the iCalendar file data from the URL
            try (InputStream inputStream = connection.getInputStream()) {
                String icsData = new String(inputStream.readAllBytes());

                CalendarParser parser = new CalendarParser();
                CalendarSerializer serializer = new CalendarSerializer();

                Calendar calendar = parser.parse(icsData);
                String icsContent = serializer.serialize(calendar);

                // Convert content to bytes
                byte[] contentBytes = icsContent.getBytes(StandardCharsets.UTF_8);

                // Set HTTP headers
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=calendar-event.ics");
                headers.add(HttpHeaders.CONTENT_TYPE, "text/calendar; charset=UTF-8");

                // Response
                return new ResponseEntity<>(contentBytes, headers, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/modify")
    public ResponseEntity<Calendar> modifyCalendar(
            @RequestParam ArrayList<String> courseFilter,
            @RequestParam ArrayList<String> activityFilter,
            @RequestParam ArrayList<String> summaryFormat,
            @RequestParam ArrayList<String> descriptionFormat,
            @RequestParam ArrayList<String> locationFormat,
            @RequestBody Calendar calendar) throws Exception {

        calendar.filterEvents(courseFilter, activityFilter);
        return ResponseEntity.ok(calendar);
    }
}