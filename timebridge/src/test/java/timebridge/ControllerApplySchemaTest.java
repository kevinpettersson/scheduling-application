package timebridge;
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

import timebridge.model.Calendar;
import timebridge.model.event.schema.EventSchema;


@SpringBootTest
@AutoConfigureMockMvc

class ControllerApplySchemaTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @InjectMocks
    private Controller controller; 
    
    @Test
    void testApplySchema() throws Exception {
        MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
        .param("ical", "https://cloud.timeedit.net/chalmers/web/public/ri637Q6QY12Z60Q5Z68086X6y5Z353nQ6351.ics"))
        .andExpect(status().isOk())
        .andReturn();
        String uploadResponse = uploadResult.getResponse().getContentAsString();
        Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
        assertNotNull(calendar);

        EventSchema schema = new EventSchema();

        String calendarID = calendar.getId();

        MvcResult applySchemaResult = mockMvc.perform(put("/calendar/applySchema/" + calendarID)
        .content(objectMapper.writeValueAsString(schema)) // Use .content() to send the request body
        .contentType(MediaType.APPLICATION_JSON)) // Set the content type to JSON
        .andExpect(status().isOk()) // Expect a 200 OK response
        .andReturn();
    }

    @Test
    void testApplySchemaInvalidId() throws Exception {
        MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
        .param("ical", "https://cloud.timeedit.net/chalmers/web/public/ri637Q6QY12Z60Q5Z68086X6y5Z353nQ6351.ics"))
        .andExpect(status().isOk())
        .andReturn();
        String uploadResponse = uploadResult.getResponse().getContentAsString();
        Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
        assertNotNull(calendar);

        EventSchema schema = new EventSchema();

        MvcResult applySchemaResult = mockMvc.perform(put("/calendar/applySchema/" + "InvalidId")
        .content(objectMapper.writeValueAsString(schema)) // Use .content() to send the request body
        .contentType(MediaType.APPLICATION_JSON)) // Set the content type to JSON
        .andExpect(status().isNotFound())
        .andReturn();
    }

    @Test
    void testApplySchemaInternalServerError() throws Exception {
        MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
        .param("ical", "https://cloud.timeedit.net/chalmers/web/public/ri637Q6QY12Z60Q5Z68086X6y5Z353nQ6351.ics"))
        .andExpect(status().isOk())
        .andReturn();
        String uploadResponse = uploadResult.getResponse().getContentAsString();
        Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
        assertNotNull(calendar);

        EventSchema schema = new EventSchema();

        // Intentionally set an invalid schema to cause an internal server error
        schema.setSummarySchema(null);
        schema.setDescriptionSchema(null);
        schema.setLocationSchema(null);

        MvcResult applySchemaResult = mockMvc.perform(put("/calendar/applySchema/" + calendar.getId())
        .content(objectMapper.writeValueAsString(schema))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
        .andReturn();
    }
}