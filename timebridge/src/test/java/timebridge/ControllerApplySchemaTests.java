package timebridge;
import org.springframework.http.MediaType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

//import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import timebridge.DTO.EventDTO;
import timebridge.model.Calendar;
import timebridge.model.event.Event;
import timebridge.model.event.component.Attendee;
import timebridge.model.event.component.Interval;
import timebridge.repository.CalendarRepository;
import timebridge.services.CalendarParser;
import timebridge.services.CalendarSerializer;


@SpringBootTest
@AutoConfigureMockMvc

class ControllerApplySchemaTests {

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

//     @Test
//     void testApplySchema() throws Exception {
//     MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
//     .param("ical", "https://cloud.timeedit.net/chalmers/web/public/ri637Q6QY12Z60Q5Z68086X6y5Z353nQ6351.ics"))
//     .andExpect(status().isOk())
//     .andReturn();
    

//     String uploadResponse = uploadResult.getResponse().getContentAsString();
//     Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
//     assertNotNull(calendar);

//     String calendarID = calendar.getId();


//     MvcResult result = mockMvc.perform(post("/calendar/applySchema/{id}", id)
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(schema))
//             .andExpect(status().isOk())
//             .andReturn();

//     // Assert
//     verify(mockSerializer, times(1)).applySchema(eq(calendarID), any());
//     assertNotNull(result.getResponse().getContentAsString());
// }
}