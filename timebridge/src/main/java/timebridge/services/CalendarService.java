package timebridge.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import timebridge.model.event.component.Attendee;
import timebridge.model.event.schema.EventSchema;
import timebridge.repository.CalendarRepository;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import timebridge.DTO.EventDTO;
import timebridge.model.Calendar;
import timebridge.model.event.Event;
import timebridge.model.event.EventFactory;

/**
 * Service class for managing calendar operations.
 * This class provides methods for uploading, modifying, serializing calendars, etc.
 */
@Service
public class CalendarService {

    @Autowired
    private CalendarRepository repository;

    private CalendarParser parser;
    private CalendarSerializer serializer;

    public CalendarService() {
        this.parser = new CalendarParser();
        this.serializer = new CalendarSerializer();
    }


    /**
     * Uploads a calendar from a URL and saves it to the database.
     *
     * @param ical the URL of the iCalendar file to upload.
     * @return the uploaded {@link Calendar} object.
     * @throws MalformedURLException if the provided URL is malformed.
     * @throws IOException if an I/O error occurs during the upload process.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    // Upload a calendar from a URL and save it to the database
    public Calendar uploadCalendar(String ical) throws MalformedURLException, IOException {
        try {
            // Open connection and read the iCalendar file data from the URL
            HttpURLConnection connection = (HttpURLConnection) (new URL(ical)).openConnection();
            InputStream inputStream = connection.getInputStream();
            String icsData = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Parse the iCalendar data
            Calendar calendar = parser.parse(icsData);
            repository.save(calendar);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @param id
     * @param courseFilter
     * @param activityFilter
     * @return
     * @throws IOException
     */
    // Modify the calendar by filtering events, based on course and activity filters
    public Calendar modifyCalendar(String id, ArrayList<String> courseFilter, ArrayList<String> activityFilter)
            throws IOException {
        try {
            Calendar calendar = getCalendar(id);
            calendar.filterEvents(courseFilter, activityFilter);
            repository.save(calendar);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @param calendarId
     * @param eventDTO
     * @return
     */
    // Add a new event to the calendar
    public Calendar addEvent(String calendarId, EventDTO eventDTO) {
        try {
            Calendar calendar = getCalendar(calendarId);
            Event event = EventFactory.createPersonalEvent(
                    eventDTO.getInterval(),
                    eventDTO.getSummary(),
                    eventDTO.getDescription(),
                    eventDTO.getLocation(),
                    eventDTO.getAttendees());

            calendar.saveEvent(event);
            repository.save(calendar);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @param eventDTO
     * @param eventId
     * @param calendarId
     * @return
     */
    // Modify an existing event in the calendar
    public Calendar modifyEvent(EventDTO eventDTO, String eventId, String calendarId) {
        try {
            // Fetch calendar and event
            Calendar calendar = repository.findById(calendarId).orElseThrow();
            Event event = calendar.findEvent(eventId);

            // Update its properties
            event.setSummary(eventDTO.getSummary());
            event.setDescription(eventDTO.getDescription());
            event.setInterval(eventDTO.getInterval());
            event.setAttendees(eventDTO.getAttendees());

            // Save the modified event to the calendar
            calendar.saveEvent(event);
            repository.save(calendar);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @param calendarId
     * @param eventId
     * @return
     */
    // Delete event and update the calendar
    public Calendar deleteEvent(String calendarId, String eventId) {
        try {
            Calendar calendar = repository.findById(calendarId).orElseThrow();
            calendar.deleteEvent(eventId);
            repository.save(calendar);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @param calendarId
     * @return
     * @throws IOException
     */
    // Retrieve the calendar from the database and serialize it to iCalendar format
    public byte[] SerializeCalendar(String calendarId) throws IOException {
        try {
            Calendar calendar = getCalendar(calendarId);
            String ics = serializer.serialize(calendar);
            return ics.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @param calendarId
     * @return
     */
    // Retrieve the calendar from the database by ID
    public Calendar getCalendar(String calendarId) {
        try {
            return repository.findById(calendarId).orElseThrow();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @param calendarId
     * @param courseCode
     * @param attendees
     * @return
     */
    // Add attendees to events of a specific course
    public Calendar setCourseAttendees(String calendarId, String courseCode, ArrayList<Attendee> attendees ) {
        try {
            Calendar calendar = getCalendar(calendarId);
            calendar.SetCourseAttendees(courseCode, attendees);
            repository.save(calendar);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @param calendarId
     * @param schema
     * @return
     */
    // Set event schemas for all events in the calendar
    public Calendar setEventSchemas(String calendarId, EventSchema schema) {
        try {
            Calendar calendar = getCalendar(calendarId);
            calendar.SetEventSchemas(schema);
            repository.save(calendar);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
