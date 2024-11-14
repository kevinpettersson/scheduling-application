package timebridge;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.net.HttpURLConnection;
import java.net.URL;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@RestController
class Controller {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }

    @GetMapping("/downloadIcs")
    public ResponseEntity<InputStreamResource> downloadIcsFile(@RequestParam String icalUrl) {
        try {
            // Create a URL object and open a connection to the iCalendar URL
            URL url = new URL(icalUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Check if the request was successful
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return ResponseEntity.badRequest().build();
            }

            // Read the iCalendar file data from the URL
            try (InputStream inputStream = connection.getInputStream()) {
                byte[] icsData = inputStream.readAllBytes();

                // Wrap the data in an InputStreamResource
                InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(icsData));

                // Set headers to prompt a file download with the .ics extension
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=calendar.ics");

                // Return the file as a downloadable resource
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(MediaType.parseMediaType("text/calendar"))
                        .body(resource);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
