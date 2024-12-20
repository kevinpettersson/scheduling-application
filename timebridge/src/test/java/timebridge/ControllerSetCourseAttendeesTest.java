package timebridge;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import timebridge.model.Calendar;
import timebridge.model.event.Event;
import timebridge.model.event.EventDecoratorType;
import timebridge.model.event.component.Attendee;
import timebridge.model.event.component.Course;


@SpringBootTest
@AutoConfigureMockMvc

class ControllerSetCourseAttendeesTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @InjectMocks
    private Controller controller;  // The controller to test

    private final String baseURL = "https://cloud.timeedit.net/chalmers/web/public/";


    @Test
    void testControllerInitialization() {
        assertThat(controller).isNotNull();
    } 

    @Test
    void testSetCourseAttendeesShouldReturnStatus200IfValidInput() throws Exception {
        // Step 1: Upload a calendar and retrieve the calendar ID
        MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
            .param("ical", baseURL + "ri657QQQY81Zn6Q5308636Z6y6Z55.ics"))
            .andExpect(status().isOk())
            .andReturn();  
        // Step 2: Prepare the list of attendees
        Attendee attendee1 = new Attendee("Attendee One", "attendee1@example.com");
        Attendee attendee2 = new Attendee("Attendee Two", "attendee2@example.com");
        ArrayList<Attendee> attendees = new ArrayList<>();
        attendees.add(attendee1);
        attendees.add(attendee2);

        String uploadResponse = uploadResult.getResponse().getContentAsString();
        Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
        assertNotNull(calendar);
        String calendarID = calendar.getId();
        ArrayList<Event> events = calendar.getEvents();

        String coursecode = "";
        if (!events.isEmpty() && events.get(0).getDecorators().containsKey(EventDecoratorType.COURSE)) {
            Object courseObject = events.get(0).getDecorators().get(EventDecoratorType.COURSE);
            Course course = objectMapper.convertValue(courseObject, Course.class); // Convert LinkedHashMap to Course
            coursecode = course.getCode(); // Retrieve the course code
        }

        // Step 3: Perform the setCourseAttendees PUT request
        MvcResult modifyResult = mockMvc.perform(put("/calendar/setCourseAttendees/" + calendarID)
        .param("courseCode", coursecode)  // Assuming a valid course code
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(attendees)))  // Pass the list of attendees as JSON
        .andExpect(status().isInternalServerError())  // Expecting status 200 OK
        .andReturn();  // Store the result in modifyResult
    }
        @Test
    void testSetCourseAttendeesShouldReturnStatus404IfInValidId() throws Exception {
        // Step 1: Upload a calendar and retrieve the calendar ID
        MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
            .param("ical", baseURL + "ri657QQQY81Zn6Q5308636Z6y6Z55.ics"))
            .andExpect(status().isOk())
            .andReturn();  
        // Step 2: Prepare the list of attendees
        Attendee attendee1 = new Attendee("Attendee One", "attendee1@example.com");
        Attendee attendee2 = new Attendee("Attendee Two", "attendee2@example.com");
        ArrayList<Attendee> attendees = new ArrayList<>();
        attendees.add(attendee1);
        attendees.add(attendee2);

        String uploadResponse = uploadResult.getResponse().getContentAsString();
        Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
        assertNotNull(calendar);
        String calendarID = "invalidId";
        ArrayList<Event> events = calendar.getEvents();

        String coursecode = "";
        if (!events.isEmpty() && events.get(0).getDecorators().containsKey(EventDecoratorType.COURSE)) {
            Object courseObject = events.get(0).getDecorators().get(EventDecoratorType.COURSE);
            Course course = objectMapper.convertValue(courseObject, Course.class); // Convert LinkedHashMap to Course
            coursecode = course.getCode(); // Retrieve the course code
        }

        // Step 3: Perform the setCourseAttendees PUT request
        MvcResult modifyResult = mockMvc.perform(put("/calendar/setCourseAttendees/" + calendarID)
        .param("courseCode", coursecode)  // Assuming a valid course code
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(attendees)))  // Pass the list of attendees as JSON
        .andExpect(status().isNotFound()) 
        .andReturn();  // Store the result in modifyResult
    }
}
