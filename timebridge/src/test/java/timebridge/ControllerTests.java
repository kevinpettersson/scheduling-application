package timebridge;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import timebridge.model.Calendar;
import timebridge.services.CalendarParser;
import timebridge.services.CalendarSerializer;
//import timebridge.main.Controller;

@SpringBootTest
@AutoConfigureMockMvc

class ControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private CalendarParser mockParser; 
     // Mock parsing service
    @Mock
    private CalendarSerializer mockSerializer;  // Mock serializer service

    @Mock
    private Calendar calendar;
    
    @InjectMocks
    private Controller controller;  // The controller to test

    private final String baseURL = "https://cloud.timeedit.net/chalmers/web/public/";


@Test
void testControllerInitialization() {
    assertThat(controller).isNotNull();
} //OK

// Controller Upload Tests:
@Test 
void testUploadCalendarShouldReturnStatus200OKIfValidIcalString() throws Exception {
        String icalUrl = baseURL; //Expected: 200, Was: 200
    mockMvc.perform(get("/upload").param("ical", icalUrl)).andExpect(status().isOk());
} //OK

@Test 
void testUploadCalendarShouldReturnStatus400BadRequestIfInvalidIcal() throws Exception {
        String icalUrl = baseURL + "/ThisFileIsVeryWrongShouldNotWork.ics"; //Expected: 400 Was:200
    mockMvc.perform(get("/upload")
            .param("ical", icalUrl))
            .andExpect(status().isBadRequest());
} //NOK

@Test
void testUploadCalendarShouldReturnStatus500ServerErrorIfNoHTTPRequest() throws Exception {
        String icalUrl = "/chalmers/web/public/.ics"; //Expected: 500, Was: 500
    mockMvc.perform(get("/upload").param("ical", icalUrl)).andExpect(status().isInternalServerError()); 
}//OK

@Test
void testUploadCalendarShouldReturnStatus400BadRequestIfIcalUrlIsMalformed() throws Exception {
        String icalUrl = baseURL + "invalid-url"; //Expected: 400, Was: 200
    mockMvc.perform(get("/upload")
            .param("ical", icalUrl))
            .andExpect(status().isBadRequest());
} //NOK

@Test
void testUploadCalendarShouldReturnStatus400BadRequestIfIcalUrlIsTooLong() throws Exception {
        String icalUrl = baseURL +  new String(new char[1001]).replace('\0', 'a'); //Expected: 400, Was: 200

    mockMvc.perform(get("/upload")
            .param("ical", icalUrl))
            .andExpect(status().isBadRequest());
} //NOK

@Test
void testUploadCalendarShouldReturnStatus400BadRequestIfIcalUrlIsNull() throws Exception {
        String icalUrl = null; //Expected: 400, Was: 400
    mockMvc.perform(get("/upload")
            .param("ical", icalUrl))
            .andExpect(status().isBadRequest());
} //OK

@Test
void testUploadCalendarShouldReturnStatus400BadRequestIfIcalUrlIsEmpty() throws Exception {
        String icalUrl = ""; 
    mockMvc.perform(get("/upload").param("ical", icalUrl)).andExpect(status().isBadRequest());
} //NOK


 //Controller getPublicCalendar Tests:

@Test
void testDownloadCalendarShouldReturnStatus200OKIfValidCalendar() throws Exception {

    ResponseEntity<Calendar> response = controller.uploadCalendar(baseURL);

    Calendar responseCalendar = response.getBody();
    String calendarID = responseCalendar.getId();
    mockMvc.perform(get("/public/{id}").param("id", calendarID)).andExpect(status().isOk());

}

@Test
void testGetPublicCalendarShouldReturnStatus200OKIfValidCalendarID() throws Exception {
    // Arrange
    String validIcalUrl = "https://cloud.timeedit.net/chalmers/web/public/ri657QQQY81Zn6Q5308636Z6y6Z55.ics";
    ResponseEntity<Calendar> response = controller.uploadCalendar(validIcalUrl);
    assertNotNull(response);
    Calendar calendar = response.getBody();
    assertNotNull(calendar);
    String calendarID = calendar.getId();

    // Act & Assert
    mockMvc.perform(get("/public/{id}", calendarID))
            .andExpect(status().isOk())
            .andExpect(header().string("Content-Disposition", "attachment; filename=" + calendar.getName() + ".ics"))
            .andExpect(header().string("Content-Type", "text/calendar; charset=UTF-8"));
}

// @Test
// void testGetPublicCalendarShouldReturnStatus200OKIfValidCalendarID() throws Exception {
//     ResponseEntity<Calendar> response = controller.uploadCalendar("http://time-bridge.live/api/upload?ical=https%3A%2F%2Fcloud.timeedit.net%2Fchalmers%2Fweb%2Fpublic%2Fri657QQQY81Zn6Q5308636Z6y6Z55.ics");
//     assertNotNull(response);
//     Calendar calendar = response.getBody();
//     assertNotNull(calendar);
//     String calendarID = calendar.getId();

//     mockMvc.perform(get("/public/{id}").param("id", calendarID)).andExpect(status().isOk());}


/* @Test
void testGetPublicCalendarShouldReturnStatus200OKIfValidCalendarID2() throws Exception {
    // Arrange
    String validId = "12345"; // Simulated valid calendar ID
    Calendar mockCalendar = new Calendar();
    mockCalendar.setId(validId);
    mockCalendar.setName("Test Calendar");

    // Mock repository behavior
    when(repository.findById(validId)).thenReturn(Optional.of(mockCalendar));

    // Act & Assert
    mockMvc.perform(get("/public/{id}", validId))
            .andExpect(status().isOk())
            .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Test Calendar.ics"))
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "text/calendar; charset=UTF-8"));
} */

}

// Controller Modify Tests:
/* @Test  {
void testModifyCalendarShouldReturnStatus200OKIfValidInput() throws Exception {
    ResponseEntity<Calendar> response = controller.uploadCalendar(baseURL);
    Calendar responseCalendar = response.getBody();
    String calendarID = responseCalendar.getId();
    
    Calendar validCalendar = new Calendar();
    validCalendar.setName("Test Calendar");
    String calendarJson = objectMapper.writeValueAsString(validCalendar);

    mockMvc.perform(get("/modify")
            .param("courseFilter", "Course1", "Course2")
            .param("activityFilter", "Lecture", "Lab")
            .contentType("application/json")
            .content(calendarJson))
            .andExpect(status().isOk());
}
 */




