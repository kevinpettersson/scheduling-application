package timebridge;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import timebridge.DTO.EventDTO;
import timebridge.model.Calendar;
import timebridge.repository.CalendarRepository;
import timebridge.services.CalendarParser;
import timebridge.services.CalendarSerializer;


@SpringBootTest
@AutoConfigureMockMvc

class ControllerAddEventTest {

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
void testAddEventShouldReturnStatus200IfValidInput() throws Exception {

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
    // Step 3: Perform the modify event PUT request
    MvcResult modifyResult = mockMvc.perform(post("/event/add")
        .param("calendarId", calendarID)
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
void testAddEventShouldReturnStatus400IfInValidCalendarID() throws Exception {
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
    String calendarID = "InvalidCalendarID";
    // Step 3: Perform the modify event PUT request
    MvcResult modifyResult = mockMvc.perform(post("/event/add")
        .param("calendarId", calendarID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(modifiedEventDTO)))
        .andExpect(status().isNotFound())
        .andReturn();  // Store the result in modifyResult
}


}