package timebridge;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MvcResult;
import timebridge.model.Calendar;

@SpringBootTest
@AutoConfigureMockMvc

class ControllerGetPublicCalendarTest {

    @Autowired
    private MockMvc mockMvc;
    
    @InjectMocks
    private Controller controller;  // The controller to test

    @Autowired
    private ObjectMapper objectMapper;

    private final String baseURL = "https://cloud.timeedit.net/chalmers/web/public/";

    @Test
    void testGetPublicCalendarShouldReturnStatus200OKIfValidCalendarID() throws Exception {
        // Upload a calendar and get the calendar object
        MvcResult result = mockMvc.perform(post("/calendar/upload").param("ical", baseURL + "ri657QQQY81Zn6Q5308636Z6y6Z55.ics")).andExpect(status().isOk()).andReturn();
        String response = result.getResponse().getContentAsString();
        Calendar calendar = objectMapper.readValue(response, Calendar.class);

        // Check that the calendar object is not null
        assertNotNull(calendar);

        // Download the calendar and check that the status is 200 OK
        mockMvc.perform(get("/calendar/download/{id}", calendar.getId())).andExpect(status().isOk());
    }

    @Test
    void testGetPublicCalendarShouldReturnStatus404NotFoundIfInvalidCalendarID() throws Exception {
        String id = "dfghjklrtyuiop8777";
        mockMvc.perform(get("/calendar/download/{id}", id)).andExpect(status().isNotFound());
    }

    @Test
    void testGetPublicCalendarShouldReturnStatus404NotFoundIfEmptyCalendarID() throws Exception {
    String id = "";
    mockMvc.perform(get("/calendar/download/{id}", id)).andExpect(status().isNotFound());
    }
}