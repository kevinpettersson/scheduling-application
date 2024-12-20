package timebridge;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc

class ControllerUploadTest {

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
    void testUploadCalendarShouldReturnStatus200OKIfValidIcalString() throws Exception {
            String icalUrl = baseURL;
        mockMvc.perform(post("/calendar/upload").param("ical", icalUrl + "ri657QQQY81Zn6Q5308636Z6y6Z55.ics")).andExpect(status().isOk());
    }

    @Test
    void testUploadCalendarShouldReturnStatus400BadRequestIfInvalidIcal() throws Exception {
            String icalUrl = baseURL + "ThisFileIsVeryWrongShouldNotWork.ics";
        mockMvc.perform(post("/calendar/upload")
                .param("ical", icalUrl))
                .andExpect(status().isBadRequest());
    } 

    @Test
    void testUploadCalendarShouldReturnStatus500ServerErrorIfNoHTTPRequest() throws Exception {
            String icalUrl = "/chalmers/web/public/.ics"; 
        mockMvc.perform(post("/calendar/upload").param("ical", icalUrl)).andExpect(status().isServiceUnavailable());
    }

    @Test
    void testUploadCalendarShouldReturnStatus400BadRequestIfIcalUrlIsMalformed() throws Exception {
            String icalUrl = baseURL + "invalid-url"; 
        mockMvc.perform(post("/calendar/upload")
                .param("ical", icalUrl))
                .andExpect(status().isBadRequest());
    } 

    @Test
    void testUploadCalendarShouldReturnStatus400BadRequestIfIcalUrlIsTooLong() throws Exception {
            String icalUrl = baseURL +  new String(new char[1001]).replace('\0', 'a');

            mockMvc.perform(post("/calendar/upload").param("ical", icalUrl))
                .andExpect(status().isBadRequest());
    } 

    @Test
    void testUploadCalendarShouldReturnStatus400BadRequestIfIcalUrlIsNull() throws Exception {
            String icalUrl = null; 
            mockMvc.perform(post("/calendar/upload")
                .param("ical", icalUrl))
                .andExpect(status().isBadRequest());
    } 

}


