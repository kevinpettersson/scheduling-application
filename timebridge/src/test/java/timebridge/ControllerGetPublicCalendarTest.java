package timebridge;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import timebridge.model.Calendar;

@SpringBootTest
@AutoConfigureMockMvc

class ControllerGetPublicCalendarTest {

    @Autowired
    private MockMvc mockMvc;
    
    @InjectMocks
    private Controller controller;  // The controller to test

    private final String baseURL = "https://cloud.timeedit.net/chalmers/web/public/";

/*     private final String calendarID = "675c23c4c70fc136b330dd27";
 */

// getPuplicCalendar Tests

@Test
void testGetPublicCalendarShouldReturnStatus200OKIfValidCalendarID() throws Exception {
    ResponseEntity<Calendar> response = controller.uploadCalendar(baseURL + "ri637Q6QY12Z60Q5Z68086X6y5Z353nQ6351.ics");
    assertNotNull(response);
    Calendar calendar = response.getBody();
    assertNotNull(calendar);
    String calendarID = calendar.getId(); 

    mockMvc.perform(get("/public/{id}", calendarID)).andExpect(status().isOk());
    }
// We dont upload the calendar in these test since there is no point when we are testing wrong or empty calendarID's

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