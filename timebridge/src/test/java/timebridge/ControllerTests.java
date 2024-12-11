package timebridge;
import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestParam;

import timebridge.model.Calendar;
import timebridge.services.CalendarParser;
import timebridge.services.CalendarSerializer;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTests {
    @Mock
    private CalendarParser mockParser; 
     // Mock parsing service
    @Mock
    private CalendarSerializer mockSerializer;  // Mock serializer service
    
    @InjectMocks
    private Controller controller;  // The controller to test

    @Test
    void testControllerInitialization() {
        assertThat(controller).isNotNull();
    }

//     @Test
//     void testUploadCalendarSuccess() throws Exception {
    
//     String icalData = "BEGIN:VCALENDAR\nVERSION:2.0\n...";
//     Calendar calendar = new Calendar();  // Mock calendar object 
    
//     when(mockParser.parse(icalData)).thenReturn(calendar);
    

//     URL mockUrl = mock(URL.class);
//     HttpURLConnection mockConnection = mock(HttpURLConnection.class);
//     when(mockUrl.openConnection()).thenReturn(mockConnection);
//     when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
//     when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream(icalData.getBytes(StandardCharsets.UTF_8)));

    
//     ResponseEntity<Calendar> response = controller.uploadCalendar("http://time-bridge.live/api/upload?ical=");

//     assertEquals(HttpStatus.OK, response.getStatusCode());
//     assertNotNull(response.getBody());
//     assertSame(calendar, response.getBody());
// }


    @Autowired
    private MockMvc mockMvc;
    // @Test
//     void uploadCalendarTest() throws Exception {

//     String validIcal = "http://time-bridge.live/api/upload?ical=";

//     mockMvc.perform(uploadCalendar.get("/upload")
//             .param("ical", validIcal)
//             .contentType(MediaType.APPLICATION_JSON))
//             .andExpect(status().isOk());
//     }

//         when(controller.uploadCalendar(validIcal)).thenThrow(new GithubTokenNotFoundException());



//         /*
//          * EXECUTION & POST-CONDITIONS
//          */

//         assertThrows(
//                 GithubTokenNotFoundException.class, () -> hamsterController.requestProject(mockedProject, mockedToken));
//     }    
@Test
void testUploadCalendarShouldReturnStatus200OKIfValidIcalString() throws Exception {
    // Arrange
    String icalUrl = "https://cloud.timeedit.net/chalmers/web/public/ri657QQQY81Zn6Z6y6Z55.ics";

    // Act
    mockMvc.perform(get("/upload")
            .param("ical", icalUrl))
            .andExpect(status().isOk());
            //.andExpect(jsonPath("$.someField").value("expectedValue")); 
}

@Test
void testUploadCalendarShouldReturnStatus400BadRequestIfInvalidIcal() throws Exception {
    // Arrange
    String icalUrl = "https://cloud.timeedit.net/chalmers/web/public/ThisFileIsVeryWrongShouldNotWork.ics";
    // Act
    mockMvc.perform(get("/upload")
            .param("ical", icalUrl))
            .andExpect(status().isBadRequest());
            //.andExpect(jsonPath("$.someField").value("expectedValue")); 
}

@Test
void testUploadCalendarShouldReturnStatus400BadRequestIfEmptyIcalFile() throws Exception {
    // Arrange
    String icalUrl = "https://cloud.timeedit.net/chalmers/web/public/.ics";
    // Act
    mockMvc.perform(get("/upload")
            .param("ical", icalUrl))
            .andExpect(status().isBadRequest());
            //.andExpect(jsonPath("$.someField").value("expectedValue")); 
}

@Test
void testUploadCalendarShouldReturnStatus500ServerErrorIfNoHTTPRequest() throws Exception {
    // Arrange
    String icalUrl = "/chalmers/web/public/.ics";
    // Act
    mockMvc.perform(get("/upload")
            .param("ical", icalUrl))
            .andExpect(status().isInternalServerError());
            //.andExpect(jsonPath("$.someField").value("expectedValue")); 
}

}