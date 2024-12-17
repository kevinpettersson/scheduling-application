package timebridge;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import timebridge.main.Controller;

// TODO?: Add test for GetPublicCalendar, test edge case where calendar ID has been valid, but now should be invalid?
// Or is that test for the database ?

@SpringBootTest
@AutoConfigureMockMvc

class ControllerGetPublicCalendarTest {

    @Autowired
    private MockMvc mockMvc;
    
    @InjectMocks
    private Controller controller;  // The controller to test

    private final String baseURL = "https://cloud.timeedit.net/chalmers/web/public/";

    private final String calendarID = "675c23c4c70fc136b330dd27";


// getPuplicCalendar Tests

@Test
void testGetPublicCalendarShouldReturnStatus200OKIfValidCalendarID() throws Exception {/* 
    ResponseEntity<Calendar> response = controller.uploadCalendar(baseURL + "ri657QQQY81Zn6Q5308636Z6y6Z55.ics");
    assertNotNull(response);
    Calendar calendar = response.getBody();
    assertNotNull(calendar);
    String calendarID = calendar.getId(); */

    mockMvc.perform(get("/public/{id}", calendarID.toString())).andExpect(status().isOk());
    }
// We dont upload the calendar in these test since there is no point when we are testing wrong or empty calendarID's

@Test
void testGetPublicCalendarShouldReturnStatus404NotFoundIfInvalidCalendarID() throws Exception {
    String id = "dfghjklrtyuiop8777";
    mockMvc.perform(get("/public/{id}", id)).andExpect(status().isNotFound());
    }
    
@Test
void testGetPublicCalendarShouldReturnStatus404NotFoundIfEmptyCalendarID() throws Exception {
String id = "";
mockMvc.perform(get("/public/{id}", id)).andExpect(status().isNotFound());
}

}