package timebridge;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import timebridge.model.*;

@RestController
class Controller {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }

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
                Calendar calendar = CalendarBuilder.build(icsData);
                return ResponseEntity.ok(calendar);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

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
                Calendar calendar = CalendarBuilder.build(icsData);
                String icsContent = CalendarParser.parse(calendar);

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
}
