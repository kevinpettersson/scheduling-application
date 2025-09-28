package timebridge.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import timebridge.DTO.EventDTO;
import timebridge.model.Calendar;
import timebridge.model.event.Event;
import timebridge.model.event.EventFactory;
import timebridge.model.event.component.Attendee;
import timebridge.model.event.schema.EventSchema;
import timebridge.repository.CalendarRepository;

/**
 * Service class for managing calendar operations.
 * <p>
 * This class provides methods for uploading, modifying, serializing calendars,
 * etc.
 * </p>
 * <p>
 * Purpose of this class is to provide a layer of abstraction between the
 * controller and the model/repository.
 * </p>
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
     * Uploads a calendar from a URL, parses it and saves it to the database.
     *
     * @param ical the URL of the iCalendar file to upload.
     * @return the uploaded {@link Calendar} object.
     * @throws MalformedURLException if the provided URL is malformed.
     * @throws IOException           if an I/O error occurs during the upload
     *                               process.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    public Calendar uploadCalendar(String ical) throws MalformedURLException, IOException {
        try {
            // Open connection and read the iCalendar file data from the URL
            System.out.println("Fetching iCal URL: " + ical);
            HttpURLConnection connection = (HttpURLConnection) URI.create(ical).toURL().openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0"); // some servers reject Java default UA
            InputStream inputStream = connection.getInputStream();
            String icsData = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Fetched iCal data length: " + icsData.length());

            Calendar calendar = parser.parse(icsData);
            repository.save(calendar);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Modify the calendar by filtering events, based on course and activity
     * filters.
     * <p>
     * Retrieves the calendar from the database and saves the modified calendar back
     * to the database.
     * </p>
     *
     * @param id             the ID of the calendar to modify.
     * @param courseFilter   the list of course codes to filter by.
     * @param activityFilter the list of activities to filter by.
     * @return the modified {@link Calendar} object.
     * @throws IOException if an I/O error occurs.
     *
     * @since 2024-12-19
     * @author Group 12
     */
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
     * Adds a new personal event to the calendar and saves it to the database.
     * <p>
     * Retrieves the calendar from the database and saves the modified calendar back
     * to the database.
     * </p>
     *
     * @param calendarId the ID of the calendar to add the event to.
     * @param eventDTO   the event data to add.
     * @return the modified {@link Calendar} object.
     *
     * @since 2024-12-19
     * @author Group 12
     */
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
     * Modify an existing event in the calendar and save it to the database.
     *
     * @param eventDTO   the event data to modify.
     * @param eventId    the ID of the event to modify.
     * @param calendarId the ID of the calendar to modify the event in.
     * @return the modified {@link Calendar} object.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    public Calendar modifyEvent(EventDTO eventDTO, String eventId, String calendarId) {
        try {
            Calendar calendar = getCalendar(calendarId);
            Event event = calendar.findEvent(eventId);

            event.setSummary(eventDTO.getSummary());
            event.setDescription(eventDTO.getDescription());
            event.setInterval(eventDTO.getInterval());
            event.setAttendees(eventDTO.getAttendees());

            calendar.saveEvent(event);
            repository.save(calendar);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Delete an existing event in the calendar and save the updated calendar to the
     * database.
     *
     * @param calendarId the ID of the calendar to delete the event in.
     * @param eventId    the ID of the event to modify.
     * @return the modified {@link Calendar} object.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    public Calendar deleteEvent(String calendarId, String eventId) {
        try {
            Calendar calendar = getCalendar(calendarId);
            calendar.deleteEvent(eventId);
            repository.save(calendar);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Retrieve the calendar from the database and serialize it to iCalendar format.
     *
     * @param calendarId the ID of the calendar to serialize.
     * @return the serialized iCalendar data as a byte array.
     * @throws IOException if an I/O error occurs.
     *
     * @since 2024-12-19
     * @author Group 12
     */
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
     * Retrieve the calendar from the database.
     *
     * @param calendarId the ID of the calendar to retrieve.
     * @return the {@link Calendar} object.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    public Calendar getCalendar(String calendarId) {
        try {
            return repository.findById(calendarId).orElseThrow();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Add attendees all to events of a specific course.
     *
     * @return the {@link Calendar} object with modified events.
     *
     * @since 2024-12-19
     * @author Group 12
     */
    public Calendar setCourseAttendees(String calendarId, String courseCode, ArrayList<Attendee> attendees)
            throws Exception {
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
     * Set event schemas for all events in the calendar.
     *
     * @return the {@link Calendar} object with modified events.
     *
     * @since 2024-12-19
     * @author Group 12
     */
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
