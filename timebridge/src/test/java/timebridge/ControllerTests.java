package timebridge;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import timebridge.services.CalendarParser;
import timebridge.services.CalendarSerializer;

@SpringBootTest
@AutoConfigureMockMvc

class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CalendarParser mockParser; 
     // Mock parsing service
    @Mock
    private CalendarSerializer mockSerializer;  // Mock serializer service
    
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
        String icalUrl = ""; //Expected: 400, Was: 500
    mockMvc.perform(get("/upload").param("ical", icalUrl)).andExpect(status().isBadRequest());
} //NOK


// Controller Download Tests:
@Test
void testDownloadCalendarShouldReturnStatus200OKIfValidIcalString() throws Exception {
    mockMvc.perform(get("/download")).andExpect(status().isOk());
}
}