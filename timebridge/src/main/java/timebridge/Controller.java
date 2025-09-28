package timebridge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import timebridge.DTO.EventDTO;
import timebridge.model.Calendar;
import timebridge.model.event.component.Attendee;
import timebridge.model.event.schema.EventSchema;
import timebridge.services.CalendarService;

/**
 * Controller class for handling HTTP requests. Provides endpoints for uploading, downloading, modifying and deleting.
 *
 * @since 2024-12-19
 * @author Group 12
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class Controller {

    @Autowired
    private CalendarService service;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @PostMapping("/calendar/upload")
    public ResponseEntity<Calendar> uploadCalendar(@RequestParam String ical) {
        try {
            return ResponseEntity.ok(service.uploadCalendar(ical));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid iCalendar URL: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Unable to fetch the iCalendar: " + e.getMessage(), e);
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Failed to parse iCalendar data: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error occured: " + e.getMessage(), e);
        }
    }

    @GetMapping("/calendar/download/{id}")
    public ResponseEntity<byte[]> downloadCalendar(@PathVariable String id) {
        try {
            // Retrieve the calendar to use its name
            Calendar calendar = service.getCalendar(id);

            // Serialize the calendar to iCalendar format
            byte[] bytes = service.SerializeCalendar(id);

            // Set HTTP headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + calendar.getName() + ".ics");
            headers.add(HttpHeaders.CONTENT_TYPE, "text/calendar; charset=UTF-8");

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Calendar not found with ID: " + id, e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error occurred: " + e.getMessage(), e);
        }
    }

    @PutMapping("/calendar/modify/{id}")
    public ResponseEntity<Calendar> modifyCalendar(
            @RequestParam ArrayList<String> courseFilter,
            @RequestParam ArrayList<String> activityFilter,
            @PathVariable String id) {
        try {
            return ResponseEntity.ok(service.modifyCalendar(id, courseFilter, activityFilter));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Calendar not found with ID: " + id, e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid filter parameters: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error occurred: " + e.getMessage(), e);
        }
    }

    @PostMapping("/event/add")
    public ResponseEntity<Calendar> addEvent(
            @RequestParam String calendarId,
            @RequestBody EventDTO eventDTO) {
        try {
            return ResponseEntity.ok(service.addEvent(calendarId, eventDTO));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Calendar not found with ID: " + calendarId, e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid event data: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error occurred: " + e.getMessage(), e);
        }
    }

    @PutMapping("/event/modify")
    public ResponseEntity<Calendar> modifyEvent(
            @RequestParam String calendarId,
            @RequestParam String eventId,
            @RequestBody EventDTO eventDTO) {
        try {
            return ResponseEntity.ok(service.modifyEvent(eventDTO, eventId, calendarId));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Event or Calendar not found with provided IDs.", e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid event data: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error occurred: " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/event/delete")
    public ResponseEntity<Calendar> deleteEvent(
            @RequestParam String calendarId,
            @RequestParam String eventId) {
        try {
            return ResponseEntity.ok(service.deleteEvent(calendarId, eventId));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Event or Calendar not found with provided IDs.", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error occurred: " + e.getMessage(), e);
        }
    }

    @PutMapping("calendar/setCourseAttendees/{id}")
    public ResponseEntity<Calendar> setCourseAttendees(
            @PathVariable String id,
            @RequestParam String courseCode,
            @RequestBody ArrayList<Attendee> attendees) {
        try {
            return ResponseEntity.ok(service.setCourseAttendees(id, courseCode, attendees));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Calendar not found with ID: " + id, e);}
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error occurred: " + e.getMessage(), e);
        }
    }

    @PutMapping("calendar/applySchema/{id}")
    public ResponseEntity<Calendar> setEventSchemas(
            @PathVariable String id,
            @RequestBody EventSchema schema) {
        try {
            return ResponseEntity.ok(service.setEventSchemas(id, schema));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Calendar not found with ID: " + id, e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error occurred: " + e.getMessage(), e);
        }
    }
}