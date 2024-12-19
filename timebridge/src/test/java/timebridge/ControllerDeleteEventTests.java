package timebridge;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import timebridge.model.Calendar;
import timebridge.model.event.Event;
import timebridge.repository.CalendarRepository;
import timebridge.services.CalendarParser;
import timebridge.services.CalendarSerializer;
import timebridge.services.CalendarService;

@SpringBootTest
@AutoConfigureMockMvc

class ControllerDeleteEventTests {

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
    private CalendarService service;

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
void testDeleteEventShouldReturnStatus200IfValidInputID() throws Exception {
    // Upload a calendar and save the response
    MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
        .param("ical", "https://cloud.timeedit.net/chalmers/web/public/ri637Q6QY12Z60Q5Z68086X6y5Z353nQ6351.ics"))
        .andExpect(status().isOk())
        .andReturn();
    
    String uploadResponse = uploadResult.getResponse().getContentAsString();
    Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
    assertNotNull(calendar);
    String calendarID = calendar.getId();
    ArrayList<Event> calendarEvents = calendar.getEvents();
    String eventID = calendarEvents.get(0).getId();

    // Delete event
    mockMvc.perform(delete("/event/delete")
        .param("calendarId", calendarID)
        .param("eventId", eventID))
        .andExpect(status().isOk());
}


@Test
void testDeleteEventShouldReturnStatus404IfInvalidEventID() throws Exception {
    // Upload a calendar and save the response
    MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
        .param("ical", baseURL + "ri657QQQY81Zn6Q5308636Z6y6Z55.ics"))
        .andExpect(status().isOk())
        .andReturn();

    String uploadResponse = uploadResult.getResponse().getContentAsString();
    Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
    assertNotNull(calendar);
    String calendarID = calendar.getId();
    String invalidEventID = "invalidEventID";     //Use an invalid eventID

    mockMvc.perform(delete("/deleteEvent")
        .param("calendarId", calendarID)
        .param("eventId", invalidEventID))
        .andExpect(status().isNotFound());
}

@Test
void testDeleteEventShouldReturnStatus404IfEmptyEventID() throws Exception{
    MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
        .param("ical", baseURL + "ri657QQQY81Zn6Q5308636Z6y6Z55.ics"))
        .andExpect(status().isOk())
        .andReturn();

        String uploadResponse = uploadResult.getResponse().getContentAsString();
        Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
        assertNotNull(calendar);
        String calendarID = calendar.getId();
        String emptyEventID = "";  //Empty eventID
    
        mockMvc.perform(delete("/deleteEvent")
            .param("calendarId", calendarID)
            .param("eventId", emptyEventID))
            .andExpect(status().isNotFound());

}

@Test
void testDeleteEventShouldReturnStatus404IfInvalidCalendarID() throws Exception{

    MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
    .param("ical", "https://cloud.timeedit.net/chalmers/web/public/ri637Q6QY12Z60Q5Z68086X6y5Z353nQ6351.ics"))
    .andExpect(status().isOk())
    .andReturn();

    String uploadResponse = uploadResult.getResponse().getContentAsString();
    Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
    assertNotNull(calendar);
    String calendarID = "InvalidCalendarID"; //Invalid CalendarID
    ArrayList<Event> calendarEvents = calendar.getEvents();
    String eventID = calendarEvents.get(0).getId();

    mockMvc.perform(delete("/event/delete")
        .param("calendarId", calendarID)
        .param("eventId", eventID))
        .andExpect(status().isNotFound());
    
}

@Test
void testDeleteEventShouldReturnStatus404IfEmptyCalendarID() throws Exception{
    MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
    .param("ical", "https://cloud.timeedit.net/chalmers/web/public/ri637Q6QY12Z60Q5Z68086X6y5Z353nQ6351.ics"))
    .andExpect(status().isOk())
    .andReturn();

    String uploadResponse = uploadResult.getResponse().getContentAsString();
    Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
    assertNotNull(calendar);
    String calendarID = ""; //Empty CalendarID
    ArrayList<Event> calendarEvents = calendar.getEvents();
    String eventID = calendarEvents.get(0).getId();

    mockMvc.perform(delete("/event/delete")
        .param("calendarId", calendarID)
        .param("eventId", eventID))
        .andExpect(status().isNotFound());
}


}