package timebridge;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import timebridge.model.Calendar;

@SpringBootTest
@AutoConfigureMockMvc

class ControllerDownloadTest {

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
void testDownloadEventValid() throws Exception {
     MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
        .param("ical", "https://cloud.timeedit.net/chalmers/web/public/ri637Q6QY12Z60Q5Z68086X6y5Z353nQ6351.ics"))
        .andExpect(status().isOk())
        .andReturn();
    
    String uploadResponse = uploadResult.getResponse().getContentAsString();
    Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
    assertNotNull(calendar);
    String calendarID = calendar.getId();


    MvcResult result = mockMvc.perform(get("/calendar/download/" + calendarID))
    //.param("id", calendarID))
    .andExpect(status().isOk())
    .andReturn();

}
@Test
void testDownloadEventInvalidIfInvalidId() throws Exception {
     MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
        .param("ical", "https://cloud.timeedit.net/chalmers/web/public/ri637Q6QY12Z60Q5Z68086X6y5Z353nQ6351.ics"))
        .andExpect(status().isOk())
        .andReturn();
    
    String uploadResponse = uploadResult.getResponse().getContentAsString();
    Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
    assertNotNull(calendar);
    String calendarID = "InvalidID";


    MvcResult result = mockMvc.perform(get("/calendar/download/" + calendarID))
    .andExpect(status().isNotFound())
    .andReturn();

}

}