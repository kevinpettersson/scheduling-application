package timebridge;
import org.springframework.http.MediaType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

//import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import timebridge.DTO.EventDTO;
import timebridge.model.Calendar;
import timebridge.model.event.Event;
import timebridge.model.event.component.Attendee;
import timebridge.model.event.component.Interval;
import timebridge.repository.CalendarRepository;
import timebridge.services.CalendarParser;
import timebridge.services.CalendarSerializer;


@SpringBootTest
@AutoConfigureMockMvc

class ControllerSetCourseAttendeesTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private CalendarParser mockParser; 

    @Mock
    private CalendarRepository repository;

    @Mock
    private CalendarSerializer mockSerializer;  // Mock serializer service

    @Mock
    private Calendar calendar;
    
    @InjectMocks
    private Controller controller;  // The controller to test

    private final String baseURL = "https://cloud.timeedit.net/chalmers/web/public/";

    private final String calendarID = "675c23c4c70fc136b330dd27";

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
    
    // Step 3: Perform the setCourseAttendees PUT request
    MvcResult modifyResult = mockMvc.perform(put("/calendar/setCourseAttendees/{id}", calendarID)
        .param("courseCode", "CS101")  // Assuming a valid course code
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(attendees)))  // Pass the list of attendees as JSON
        .andExpect(status().isOk())  // Expecting status 200 OK
        .andReturn();  // Store the result in modifyResult

    // Step 4: Parse the response of the modified calendar
    String modifyResponse = modifyResult.getResponse().getContentAsString();
    Calendar modifiedCalendar = objectMapper.readValue(modifyResponse, Calendar.class);

    // Step 5: Assert that the attendees have been added
    assertNotNull(modifiedCalendar.getEvents());
    Event event = modifiedCalendar.getEvents().get(0);  // Assuming we modify the first event
    assertNotNull(event.getAttendees());
    assertThat(event.getAttendees()).hasSize(2);  // Verify the number of attendees
    assertThat(event.getAttendees().get(0).getMail()).isEqualTo("attendee1@example.com"); // Verify the first attendee's email
    assertThat(event.getAttendees().get(1).getMail()).isEqualTo("attendee2@example.com"); // Verify the second attendee's email
}

}
