package timebridge;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import timebridge.model.Calendar;
import timebridge.repository.CalendarRepository;
import timebridge.services.CalendarParser;
import timebridge.services.CalendarSerializer;
//import timebridge.main.Controller;


@SpringBootTest
@AutoConfigureMockMvc

class ControllerModifyTests {

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

@Test
void testControllerInitialization() {
    assertThat(controller).isNotNull();
} 

}
