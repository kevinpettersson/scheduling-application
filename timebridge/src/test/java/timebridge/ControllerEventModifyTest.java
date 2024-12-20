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

import timebridge.DTO.EventDTO;
import timebridge.model.Calendar;
import timebridge.model.event.Event;


@SpringBootTest
@AutoConfigureMockMvc

class ControllerEventModifyTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @InjectMocks
    private Controller controller; 

    private final String baseURL = "https://cloud.timeedit.net/chalmers/web/public/";

    private final String calendarID = "675c23c4c70fc136b330dd27";

@Test
void testControllerInitialization() {
    assertThat(controller).isNotNull();
} 

@Test
void testModifyEventShouldReturnStatus200IfValidInput() throws Exception {

    // Step 1: Upload a calendar and retrieve the calendar ID
    MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
        .param("ical", baseURL + "ri657QQQY81Zn6Q5308636Z6y6Z55.ics"))
        .andExpect(status().isOk())
        .andReturn();  

    // Step 2: Prepare the modified event DTO
    EventDTO modifiedEventDTO = new EventDTO();
    String uploadResponse = uploadResult.getResponse().getContentAsString();
    Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
    assertNotNull(calendar);
    String calendarID = calendar.getId();
    ArrayList<Event> calendarEvents = calendar.getEvents();
    String eventID = calendarEvents.get(0).getId();

    // Step 3: Perform the modify event PUT request
    MvcResult modifyResult = mockMvc.perform(put("/event/modify")
        .param("calendarId", calendarID)
        .param("eventId", eventID)  // Ensure we specify the correct event ID
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(modifiedEventDTO)))
        .andExpect(status().isOk())
        .andReturn();  // Store the result in modifyResult

    // Step 4: Parse the response of the modified calendar
    String modifyResponse = modifyResult.getResponse().getContentAsString();
    Calendar modifiedCalendar = objectMapper.readValue(modifyResponse, Calendar.class);

    // Step 5: Assert that the event was modified
    assertThat(modifiedCalendar.getEvents().get(0)).isNotEqualTo(calendar.getEvents().get(0));
    assertThat(modifiedCalendar.getEvents().get(0).getId()).isEqualTo(calendar.getEvents().get(0).getId());
}

@Test
void testModifyEventShouldReturnStatus400IfInvalidCalendarID() throws Exception {

    MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
    .param("ical", baseURL + "ri657QQQY81Zn6Q5308636Z6y6Z55.ics"))
    .andExpect(status().isOk())
    .andReturn();  

    EventDTO modifiedEventDTO = new EventDTO();
    String uploadResponse = uploadResult.getResponse().getContentAsString();
    Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
    assertNotNull(calendar);
    String calendarID = "InvalidCalendarID";
    ArrayList<Event> calendarEvents = calendar.getEvents();
    String eventID = calendarEvents.get(0).getId();

    mockMvc.perform(put("/event/modify")
    .param("calendarId", calendarID)
    .param("eventId", eventID) 
    .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(modifiedEventDTO)))
    .andExpect(status().isNotFound());
}

@Test
void testModifyEventShouldReturnStatusIfInvalidEventID() throws Exception {

    MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
        .param("ical", baseURL + "ri657QQQY81Zn6Q5308636Z6y6Z55.ics"))
        .andExpect(status().isOk())
        .andReturn();  

        EventDTO modifiedEventDTO = new EventDTO();
        String uploadResponse = uploadResult.getResponse().getContentAsString();
        Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
        assertNotNull(calendar);
        String calendarID = calendar.getId(); ;
        String eventID = "InvalidEventID";

        mockMvc.perform(put("/event/modify")
        .param("calendarId", calendarID)
        .param("eventId", eventID) 
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(modifiedEventDTO)))
        .andExpect(status().isInternalServerError());
}

@Test
void testModifyEventShouldReturnStatus400IfEmptyCalendarID() throws Exception {

    MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
        .param("ical", baseURL + "ri657QQQY81Zn6Q5308636Z6y6Z55.ics"))
        .andExpect(status().isOk())
        .andReturn();  

        EventDTO modifiedEventDTO = new EventDTO();
        String uploadResponse = uploadResult.getResponse().getContentAsString();
        Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
        assertNotNull(calendar);
        ArrayList<Event> calendarEvents = calendar.getEvents();
        String calendarID = "";
        String eventID = calendarEvents.get(0).getId();

        mockMvc.perform(put("/event/modify")
        .param("calendarId", calendarID)
        .param("eventId", eventID) 
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(modifiedEventDTO)))
        .andExpect(status().isNotFound());
    }

@Test
void testModifyEventShouldReturnStatus400IfEmptyEventID() throws Exception {

    MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
        .param("ical", baseURL + "ri657QQQY81Zn6Q5308636Z6y6Z55.ics"))
        .andExpect(status().isOk())
        .andReturn();  

        EventDTO modifiedEventDTO = new EventDTO();
        String uploadResponse = uploadResult.getResponse().getContentAsString();
        Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
        assertNotNull(calendar);
        String calendarID = calendar.getId();
        String eventID = "";

        mockMvc.perform(put("/event/modify")
        .param("calendarId", calendarID)
        .param("eventId", eventID) 
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(modifiedEventDTO)))
        .andExpect(status().isBadRequest());
    }
}
